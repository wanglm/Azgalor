package org.Azgalor.solr;

import org.apache.solr.common.SolrInputDocument;

public class SolrEntity extends SolrInputDocument {
	private static final long serialVersionUID = 6582495720642066277L;

	protected String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.addField("id", id);
		this.id = id;
	}

}
