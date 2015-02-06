package org.Azgalor.solr;

import java.lang.reflect.InvocationTargetException;

import org.Azgalor.solr.dao.SolrDao;

/**
 * solrdao的工厂接口，采用工厂模式生成操作对象
 * 
 * @author ming
 *
 */
public interface SolrFactory {
	/**
	 * 生产方法
	 * 
	 * @param clz
	 *            继承SolrDaoImpl的自定义dao操作类，并且声明@SolrType
	 * @return 自定义dao操作类
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public <T extends SolrDao<?>> T get(Class<T> clz) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException;

}
