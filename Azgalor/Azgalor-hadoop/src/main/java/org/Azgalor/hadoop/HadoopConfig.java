package org.Azgalor.hadoop;

import org.apache.hadoop.conf.Configuration;

public enum HadoopConfig {
	INSRANCE;
	private Configuration conf;

	private HadoopConfig() {
		Configuration _conf=new Configuration();
		_conf.addResource("core-site.xml");
		_conf.addResource("hdfs-site.xml");
		_conf.addResource("mapred-site.xml");
		this.conf=_conf;
	}

	public Configuration getConfiguration() {
		return this.conf;
	}
}
