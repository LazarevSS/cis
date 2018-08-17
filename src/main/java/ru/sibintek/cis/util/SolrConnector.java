package ru.sibintek.cis.util;

import org.apache.solr.client.solrj.impl.HttpSolrClient;

public class SolrConnector {
    private final String urlString = "http://192.168.1.6:8983/solr/mycoll";
    private final HttpSolrClient client = new HttpSolrClient.Builder(urlString).build();

    public HttpSolrClient getClient() {
        return client;
    }
}
