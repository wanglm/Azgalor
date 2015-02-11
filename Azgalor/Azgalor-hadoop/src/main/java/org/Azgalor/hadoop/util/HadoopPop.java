package org.Azgalor.hadoop.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public enum HadoopPop {
	INSTANCE;
	private Properties prop;

	private HadoopPop() {
		Properties _prop = new Properties();
		try {
			_prop.load(HadoopPop.class.getResourceAsStream("hadoop.properties"));
			this.prop = _prop;
		} catch (IOException e) {
			Logger log = LogManager.getLogger(HadoopPop.class);
			log.error("HadoopPop的配置文件读取io错误：", e);
		}
	}

	public Properties get() {
		return this.prop;
	}
}
