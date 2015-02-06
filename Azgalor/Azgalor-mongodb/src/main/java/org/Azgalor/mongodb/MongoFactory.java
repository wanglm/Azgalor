package org.Azgalor.mongodb;

import org.Azgalor.mongodb.dao.MongoDao;

/**
 * mongodao的工厂类
 * 
 * @author ming
 *
 */
public interface MongoFactory {
	/**
	 * mongodao的生产方法
	 * 
	 * @param clz
	 *            继承MongoDaoImpl的自定义dao的class,声明@MongoType
	 * @return 自定义dao
	 */
	public <T extends MongoDao<?>> T get(Class<T> clz);

}
