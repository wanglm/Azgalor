package org.Azgalor.mongodb;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.Azgalor.mongodb.utils.MongoPop;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.ServerAddress;

public enum MongoDB {
	SIMPLE(true), CLOUD(false);
	private MongoClient mc;

	private MongoDB(boolean isSimple) {
		Properties prop = MongoPop.INSTANCE.get();
		try {
			int port = Integer.valueOf(prop.getProperty("port"));
			Builder builder = MongoClientOptions.builder();
			//builder.connectTimeout(10 * 1000);// 连接超时
			builder.socketTimeout(0);//怎么又是10秒了？
			builder.socketKeepAlive(true);// 保持socket连接0
			builder.maxConnectionIdleTime(0);// 最大闲置时间
			builder.maxConnectionLifeTime(0);// 最大生存时间
			MongoClientOptions mo = builder.build();// 连接池设置
			if (isSimple) {
				mc = new MongoClient(new ServerAddress(
						prop.getProperty("mongod"), port), mo);
			} else {
				String mongos[] = prop.getProperty("mongos").split(",");
				List<ServerAddress> list = new ArrayList<ServerAddress>(
						mongos.length);
				for (String host : mongos) {
					ServerAddress sa = new ServerAddress(host, port);
					list.add(sa);
				}
				mc = new MongoClient(list, mo);
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
