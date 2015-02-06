package org.Azgalor.solr.utils;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;



public enum SolrPop {
	INSTANCE;
	private Properties prop;

	private SolrPop() {
		Properties _prop = new Properties();
		try {
			_prop.load(SolrPop.class.getResourceAsStream("solr.properties"));
			this.prop = _prop;
		} catch (IOException e) {
			Logger log = LogManager.getLogger(SolrPop.class);
			log.error("Solr的配置文件读取io错误：", e);
		}
	}

	public Properties get() {
		return this.prop;
	}

}
