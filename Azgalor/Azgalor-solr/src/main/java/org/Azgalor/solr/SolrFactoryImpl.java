package org.Azgalor.solr;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.Azgalor.solr.annotations.SolrType;
import org.Azgalor.solr.dao.SolrDao;
import org.apache.solr.client.solrj.SolrClient;

public class SolrFactoryImpl implements SolrFactory {

	@Override
	public <T extends SolrDao<?>> T get(Class<T> clz)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, InstantiationException {
		SolrType solrType = clz.getAnnotation(SolrType.class);
		Method setSolr = clz.getMethod("setSolr", SearchClient.class);
		T t = clz.newInstance();
		SolrClient solr = null;
		switch (solrType.value()) {
		case CLOUD: {
			solr = SearchClient.CLOUD.get();
			break;
		}
		default: {
			solr = SearchClient.SIMPLE.get();
			break;
		}
		}
		setSolr.invoke(t, solr);
		return t;
	}

}
