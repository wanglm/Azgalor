package org.Azgalor.mongodb;

import org.Azgalor.mongodb.annotations.MongoCollection;
import org.Azgalor.mongodb.annotations.MongoDBName;
import org.Azgalor.mongodb.annotations.Mongoz;
import org.Azgalor.mongodb.dao.MongoDao;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.mongodb.MongoClient;

public class MongoFactoryImpl implements MongoFactory {
	private Logger log = LogManager.getLogger(MongoFactoryImpl.class);

	@Override
	public <T extends MongoDao<?>> T get(Class<T> clz) {
		T t = null;
		try {
			Mongoz mz = clz.getAnnotation(Mongoz.class);
			String dbName = clz.getAnnotation(MongoDBName.class).value();
			String collection = clz.getAnnotation(MongoCollection.class)
					.value();
			t = clz.newInstance();
			MongoClient mc = null;
			switch (mz.value()) {
			case CLOUD: {
				mc = MongoDB.CLOUD.get();
				break;
			}
			default: {
				mc = MongoDB.SIMPLE.get();
			}
			}
			t.setMc(mc);
			t.setCollection(collection);
			t.setDbName(dbName);
		} catch (InstantiationException | IllegalAccessException
				| SecurityException | IllegalArgumentException e) {
			log.error("QuestionDao获取错误:", e);
		}
		return t;
	}

}
