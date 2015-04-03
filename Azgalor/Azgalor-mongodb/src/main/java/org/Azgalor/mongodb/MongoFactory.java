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
	 * 目前属于讲究用的情况，理想情况应该是子类无法操作特地属性。。。应该在父类MongoDao的无参构造中实现成员变量注入
	 * 
	 * @param clz
	 *            继承MongoDaoImpl的自定义dao的class,声明@MongoType
	 * @return 自定义dao
	 */
	public <T extends MongoDao<?>> T get(Class<T> clz);

}
