package org.Azgalor.mongodb.dao;

import java.util.ArrayList;
import java.util.List;

import org.Azgalor.mongodb.MongoEntity;
import org.bson.Document;

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
	protected MongoClient mc;
	protected String dbName;
	protected String collection;

	@SuppressWarnings("unused")
	private void setMc(MongoClient mc) {
		this.mc = mc;
	}

	public String getDbName() {
		return dbName;
	}

	@SuppressWarnings("unused")
	private void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getCollection() {
		return collection;
	}

	@SuppressWarnings("unused")
	private void setCollection(String collection) {
		this.collection = collection;
	}

	public MongoCollection<Document> getCollection(String dbName,
			String collectionName) {
		return mc.getDatabase(dbName).getCollection(collectionName);
	}

	public void insert(T t) {
		MongoCollection<Document> dc = this.getCollection(dbName, collection);
		t.setCreateId("ming");// 需要修改
		t.setCreateTime(System.currentTimeMillis());
		dc.insertOne(t);
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
		UpdateResult ur = dc.updateOne(eq("_id", t.getId()), new Document(
				"$set", t));// 更新属性，如果直接用t则是替换更新
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
		DeleteResult dr = dc.deleteOne(eq("_id", t.getId()));
		return dr.getDeletedCount() == 1L;
	}

	public boolean deleteMany(Document doc) {
		MongoCollection<Document> dc = this.getCollection(dbName, collection);
		DeleteResult dr = dc.deleteMany(doc);
		return dr.getDeletedCount() > 0L;
	}

	public Document getById(String dbName, String collection, Object id) {
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
	public List<Document> find(String dbName, String collection, Document doc) {
		List<Document> list = new ArrayList<Document>();
		MongoCursor<Document> cursor = null;
		try {
			MongoCollection<Document> dc = this.getCollection(dbName,
					collection);
			FindIterable<Document> fi = dc.find(doc);
			cursor = fi.iterator();
			while (cursor.hasNext()) {
				list.add(cursor.next());
			}
		} finally {
			cursor.close();
		}
		return list;
	}

	public long count(String dbName, String collection, Document obj) {
		MongoCollection<Document> dc = this.getCollection(dbName, collection);
		return dc.count(obj);
	}

}
