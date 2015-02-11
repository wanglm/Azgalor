package org.Azgalor.mongodb.dao;

import java.util.List;

import org.Azgalor.mongodb.MongoEntity;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

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

	public DBCollection getCollection(String dbName, String collectionName) {
		return mc.getDB(dbName).getCollection(collectionName);
	}

	public boolean insert(T t) {
		DBCollection dc = this.getCollection(dbName, collection);
		WriteResult wr = dc.insert(t);
		return wr.getLastError().ok();
	}

	public boolean update(T t) {
		DBCollection dc = this.getCollection(dbName, collection);
		DBObject obj = new BasicDBObject();
		obj.put("_id", t.getId());// 设置查询的主键
		obj = dc.findOne(obj);// 查询要更新的数据对象
		WriteResult wr = dc.update(obj, new BasicDBObject("$set", t));// 更新属性，如果直接用t则是替换更新
		return wr.isUpdateOfExisting();
	}

	public boolean delete(T t) {
		DBCollection dc = this.getCollection(dbName, collection);
		WriteResult wr = dc.remove(t);
		return wr.getLastError().ok();
	}

	public DBObject getById(String dbName, String collection, Object id) {
		DBCollection dc = this.getCollection(dbName, collection);
		DBObject obj = new BasicDBObject();
		obj.put("_id", id);
		return dc.findOne(obj);
	}

	public List<DBObject> find(String dbName, String collection, DBObject obj) {
		List<DBObject> list=null;
		DBCursor cursor=null;
		try{
			DBCollection dc = this.getCollection(dbName, collection);
			cursor=dc.find(obj);
			list=dc.find(obj).toArray();
		}finally{
			cursor.close();
		}
		return list;
	}
	
	public long count(String dbName, String collection, DBObject obj){
		DBCollection dc = this.getCollection(dbName, collection);
		return dc.count(obj);
	}

}
