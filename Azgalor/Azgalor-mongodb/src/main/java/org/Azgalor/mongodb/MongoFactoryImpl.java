package org.Azgalor.mongodb;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
			Method setMc = clz.getMethod("setMc", MongoClient.class);
			Method setDbName = clz.getMethod("setDbName", String.class);
			Method setCollection = clz.getMethod("setCollection", String.class);
			MongoClient mc = null;
			switch (mz.value()) {
			case CLOUD: {
				mc = MongoDB.CLOUD.get();
			}
			default: {
				mc = MongoDB.SIMPLE.get();
			}
			}
			setMc.invoke(t, mc);
			setDbName.invoke(t, dbName);
			setCollection.invoke(t, collection);
		} catch (InstantiationException | IllegalAccessException
				| NoSuchMethodException | SecurityException
				| IllegalArgumentException | InvocationTargetException e) {
			log.error("QuestionDao获取错误:", e);
		}
		return t;
	}

}
