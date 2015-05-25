package org.Azgalor.solr;

import java.util.Properties;

import org.Azgalor.solr.utils.SolrPop;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.LBHttpSolrServer;

/**
 * solr操作源单例
 * 
 * @author ming
 * @Email ysuwlm@Gmail.com
 *
 */
@Deprecated
public enum SolrOldClient {
	SIMPLE(true), CLOUD(false);
	private SolrServer solr;

	private SolrOldClient(boolean isSimple) {
		try {
			Properties prop = SolrPop.INSTANCE.get();
			String solrHost = prop.getProperty("solrHost");
			if (isSimple) {
				HttpSolrServer hss = new HttpSolrServer(solrHost);
				hss.setSoTimeout(60 * 1000);// 超时时间
				hss.setConnectionTimeout(60 * 1000);// 连接超时
				hss.setDefaultMaxConnectionsPerHost(1000);// 最大连接数
				hss.setMaxTotalConnections(1000);
				hss.setFollowRedirects(true);
				hss.setAllowCompression(true);// 压缩
				//this.solr = hss;
			} else {
				String zkHost = solrHost;
				LBHttpSolrServer lbServer = new LBHttpSolrServer(solrHost);
				CloudSolrServer css = new CloudSolrServer(zkHost, lbServer);
				css.setZkClientTimeout(60 * 1000);// zookeeper超时时间
				css.setZkConnectTimeout(60 * 1000);// zookeeper连接超时
				//this.solr = css;
			}
		} catch (Exception e) {
			Logger log = LogManager.getLogger(SolrOldClient.class);
			log.error("Solr的服务器连接单例初始化错误：", e);
		}
	}

	public SolrServer get() {
		return this.solr;
	}
}
