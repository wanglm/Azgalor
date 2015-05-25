package org.Azgalor.solr.dao;

import java.io.IOException;
import java.util.List;

import org.Azgalor.solr.SolrEntity;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public abstract class SolrDao<T extends SolrEntity> {
	private SolrClient solr;

	protected void setSolr(SolrClient solr) {
		this.solr = solr;
	}

	public void commit() throws SolrServerException, IOException {
		solr.optimize();
		solr.commit();
	};

	public boolean save(T t) throws SolrServerException, IOException {
		UpdateResponse ur = solr.add(t);
		commit();
		return ur.getStatus() == 0 ? false : true;
	}

	public boolean delete(List<String> list) throws SolrServerException,
			IOException {
		UpdateResponse ur = solr.deleteById(list);
		commit();
		return ur.getStatus() == 0 ? false : true;
	}

	public SolrDocument getById(String id) throws SolrServerException, IOException {
		SolrQuery sq = new SolrQuery();
		sq.setQuery("id:" + id);
		QueryResponse qr = solr.query(sq);
		SolrDocumentList solrList = qr.getResults();
		return solrList.get(0);
	}

	public SolrDocumentList list(String str) throws SolrServerException, IOException {
		SolrQuery sq = new SolrQuery();
		sq.setQuery(str);
		QueryResponse qr = solr.query(sq);
		return qr.getResults();
	}

}
