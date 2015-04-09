package org.Azgalor.mongodb.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.Azgalor.mongodb.MongoDB;
import org.Azgalor.mongodb.MongoEntity;
import org.Azgalor.mongodb.annotations.MongoCollections;
import org.Azgalor.mongodb.annotations.MongoDBName;
import org.Azgalor.mongodb.annotations.Mongoz;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import static com.mongodb.client.model.Filters.*;

/**
 * mongodb的dao基类
 * 
 * @author ming
 *
 * @param <T>
 */
public abstract class MongoDao<T extends MongoEntity<?>> {
	private Logger log = LogManager.getLogger(MongoDao.class);
	private MongoClient mc;
	private String dbName;
	private String collection;

	public MongoDao() {
		try {
			Class<?> clz = this.getClass();
			Mongoz mz = clz.getAnnotation(Mongoz.class);
			this.dbName = clz.getAnnotation(MongoDBName.class).value();
			this.collection = clz.getAnnotation(MongoCollections.class).value();
			switch (mz.value()) {
			case CLOUD: {
				this.mc = MongoDB.CLOUD.get();
				break;
			}
			default: {
				this.mc = MongoDB.SIMPLE.get();
			}
			}

		} catch (SecurityException e) {
			log.error("MongoDao 初始化错误：", e);
		}
	}

	public MongoClient getMc() {
		return mc;
	}

	public String getDbName() {
		return dbName;
	}

	public String getCollection() {
		return collection;
	}

	public MongoCollection<Document> getCollection(String dbName,
			String collectionName) {
		return mc.getDatabase(dbName).getCollection(collectionName);
	}

	public void insert(T t) {
		MongoCollection<Document> dc = this.getCollection(dbName, collection);
		t.setCreateId("ming");// 需要修改
		t.setCreateTime(System.currentTimeMillis());
		dc.insertOne(t.toDocument());
	}

	public void insertMany(List<T> list) {
		MongoCollection<Document> dc = this.getCollection(dbName, collection);
		list.forEach(e -> {
			e.setCreateId("ming");// 需要修改
			e.setCreateTime(System.currentTimeMillis());
		});
		dc.insertMany(list);
	}

	public boolean updateById(T t) {
		MongoCollection<Document> dc = this.getCollection(dbName, collection);
		t.setUpdateId("ming");// 需要改
		t.setUpdateTime(System.currentTimeMillis());
		UpdateResult ur = dc.updateOne(eq("_id", t.getObjectId("_id")),
				new Document("$set", t.toDocument()));// 更新属性，如果直接用t则是替换更新
		return ur.getModifiedCount() == 1L;
	}

	/**
	 * 更新数据集
	 * 
	 * @param findDoc
	 *            需要更新的数据集的查询条件
	 * @param t
	 *            需要更新的文档属性
	 * @return
	 */
	public boolean updateMany(Document findDoc, T t) {
		MongoCollection<Document> dc = this.getCollection(dbName, collection);
		t.setUpdateId("ming");// 需要改
		t.setUpdateTime(System.currentTimeMillis());
		UpdateResult ur = dc.updateMany(findDoc, new Document("$set", t));// 更新属性
		return ur.getModifiedCount() > 0L;
	}

	public boolean deleteById(T t) {
		MongoCollection<Document> dc = this.getCollection(dbName, collection);
		DeleteResult dr = dc.deleteOne(eq("_id", t.getObjectId("_id")));
		return dr.getDeletedCount() == 1L;
	}

	public boolean deleteMany(Document doc) {
		MongoCollection<Document> dc = this.getCollection(dbName, collection);
		DeleteResult dr = dc.deleteMany(doc);
		return dr.getDeletedCount() > 0L;
	}

	public Document getById(Object id) {
		MongoCollection<Document> dc = this.getCollection(dbName, collection);
		FindIterable<Document> fi = dc.find(eq("_id", id));
		return fi.first();
	}

	/**
	 * 该方法可以优化，循环有点多余
	 * 
	 * @param dbName
	 * @param collection
	 * @param doc
	 * @return
	 */
	public List<Document> find(Document doc) {
		List<Document> list = new ArrayList<Document>();
		MongoCursor<Document> cursor = null;
		FindIterable<Document> fi = null;
		try {
			MongoCollection<Document> dc = this.getCollection(dbName,
					collection);
			if (doc == null) {
				fi = dc.find();
			} else {
				fi = dc.find(MongoDao.MongoAnd(doc));
			}
			cursor = fi.iterator();
			while (cursor.hasNext()) {
				list.add(cursor.next());
			}
		} finally {
			cursor.close();
		}
		return list;
	}

	public long count(Document doc) {
		MongoCollection<Document> dc = this.getCollection(dbName, collection);
		return dc.count(MongoDao.MongoAnd(doc));
	}

	/**
	 * 多条件查询过滤器
	 * 
	 * @param doc
	 * @return
	 */
	private static Bson MongoAnd(Document doc) {
		List<Bson> list = doc.entrySet().stream().map(e -> {
			return eq(e.getKey(), e.getValue());
		}).collect(Collectors.toList());
		return and(list);
	}

}
