package org.Azgalor.solr;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.Azgalor.solr.annotations.SolrType;
import org.Azgalor.solr.dao.SolrDao;
import org.apache.solr.client.solrj.SolrServer;

public class SolrFactoryImpl implements SolrFactory {

	@Override
	public <T extends SolrDao<?>> T get(Class<T> clz)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, InstantiationException {
		SolrType solrType = clz.getAnnotation(SolrType.class);
		Method setSolr = clz.getMethod("setSolr", SolrServer.class);
		T t = clz.newInstance();
		SolrServer solr = null;
		switch (solrType.value()) {
		case CLOUD: {
			solr = SolrClient.CLOUD.get();
			break;
		}
		default: {
			solr = SolrClient.SIMPLE.get();
			break;
		}
		}
		setSolr.invoke(t, solr);
		return t;
	}

}
