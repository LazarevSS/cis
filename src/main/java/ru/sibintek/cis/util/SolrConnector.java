package ru.sibintek.cis.util;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.io.IOException;

@Component
public class SolrConnector {
    private final String urlString = "http://192.168.1.8:8983/solr/mycoll";
    private final HttpSolrClient client = new HttpSolrClient.Builder(urlString).build();

    public HttpSolrClient getClient() {
        return client;
    }

    @PreDestroy
    public void destroy() throws IOException {
        client.close();
    }
}
