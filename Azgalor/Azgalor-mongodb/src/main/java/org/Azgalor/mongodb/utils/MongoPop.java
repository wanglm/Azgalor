package org.Azgalor.mongodb.utils;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public enum MongoPop {
	INSTANCE;
	private Properties prop;

	private MongoPop() {
		Properties _prop = new Properties();
		try {
			_prop.load(MongoPop.class.getResourceAsStream("solr.properties"));
			this.prop = _prop;
		} catch (IOException e) {
			Logger log = LogManager.getLogger(MongoPop.class);
			log.error("Mongodb的配置文件读取io错误：", e);
		}
	}

	public Properties get() {
		return this.prop;
	}
}
