package org.Azgalor.mongodb;

import org.Azgalor.mongodb.dao.MongoDao;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class MongoFactoryImpl implements MongoFactory {
	private Logger log = LogManager.getLogger(MongoFactoryImpl.class);

	@Override
	public <T extends MongoDao<?>> T get(Class<T> clz) {
		T t = null;
		try {
			t = clz.newInstance();
		} catch (InstantiationException | IllegalAccessException
				| SecurityException | IllegalArgumentException e) {
			log.error("QuestionDao获取错误:", e);
		}
		return t;
	}

}
