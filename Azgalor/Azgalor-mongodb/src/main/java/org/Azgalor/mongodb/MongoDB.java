package org.Azgalor.mongodb;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.Azgalor.mongodb.utils.MongoPop;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public enum MongoDB {
	SIMPLE(true), CLOUD(false);
	private MongoClient mc;

	private MongoDB(boolean isSimple) {
		Properties prop = MongoPop.INSTANCE.get();
		try {
			int port = Integer.valueOf(prop.getProperty("port"));
			if (isSimple) {
				mc = new MongoClient(prop.getProperty("mongod"), port);
			} else {
				String mongos[] = prop.getProperty("mongos").split(",");
				List<ServerAddress> list = new ArrayList<ServerAddress>(
						mongos.length);
				for (String host : mongos) {
					ServerAddress sa = new ServerAddress(host, port);
					list.add(sa);
				}
				mc = new MongoClient(list);
			}
		} catch (Exception e) {
			Logger log = LogManager.getLogger(MongoDB.class);
			log.error("Mongodb的服务单例初始化错误：", e);
		}
	}

	public MongoClient get() {
		return this.mc;
	}

}
