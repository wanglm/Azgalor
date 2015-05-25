package org.Azgalor.solr;

import java.util.Properties;

import org.Azgalor.solr.utils.SolrPop;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

/**
 * solr操作源单例
 * 
 * @author ming
 * @Email ysuwlm@Gmail.com
 *
 */
public enum SearchClient {
	SIMPLE(true), CLOUD(false);
	private SolrClient solr;

	private SearchClient(boolean isSimple) {
		try {
			Properties prop = SolrPop.INSTANCE.get();
			if (isSimple) {
				HttpSolrClient hss = new HttpSolrClient(prop.getProperty("solrHost"));
				hss.setSoTimeout(60 * 1000);// 超时时间
				hss.setConnectionTimeout(60 * 1000);// 连接超时
				hss.setDefaultMaxConnectionsPerHost(1000);// 最大连接数
				hss.setMaxTotalConnections(1000);
				hss.setFollowRedirects(true);
				hss.setAllowCompression(true);// 压缩
				this.solr = hss;
			} else {
				CloudSolrClient css = new CloudSolrClient(prop.getProperty("zkHost"));
				css.setZkClientTimeout(60 * 1000);// zookeeper超时时间
				css.setZkConnectTimeout(60 * 1000);// zookeeper连接超时
				this.solr = css;
			}
		} catch (Exception e) {
			Logger log = LogManager.getLogger(SearchClient.class);
			log.error("Solr的服务器连接单例初始化错误：", e);
		}
	}

	public final SolrClient get() {
		return this.solr;
	}
}
