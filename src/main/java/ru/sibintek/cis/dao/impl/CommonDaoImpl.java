package ru.sibintek.cis.dao.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sibintek.cis.dao.CommonDao;
import ru.sibintek.cis.dao.converters.SolrDocumentConverter;
import ru.sibintek.cis.model.CommonModel;
import ru.sibintek.cis.util.SolrConnector;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CommonDaoImpl implements CommonDao {
    private static final int DEFAULT_QUERY_ROWS = 2000;

    @Autowired
    private SolrConnector solrConnector;

    @Autowired
    private SolrDocumentConverter converter;

    @Override
    public void delete(String id) throws IOException, SolrServerException {
        solrConnector.getClient().deleteById(id);
        solrConnector.getClient().commit();
    }

    @Override
    public SolrInputDocument saveFromExcel(SolrInputDocument document){
        try {
            solrConnector.getClient().add(document);
            solrConnector.getClient().commit();
            return document;
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveFromExcel(List<SolrInputDocument> documents) {
        documents.forEach(this::saveFromExcel);
    }

    @Override
    public CommonModel getByIsId(String isId) throws IOException, SolrServerException {
        HttpSolrClient client = solrConnector.getClient();
        SolrQuery q = new SolrQuery();
        q.add("q", "object_type:is");
        q.add("fq", "id:" + isId);
        q.add("rows", String.valueOf(DEFAULT_QUERY_ROWS));
        QueryResponse rsp = client.query(q);
        SolrDocumentList docs = rsp.getResults();
        return converter.toCommonModel(docs.get(0));
    }


    @Override
    public CommonModel getByIsName(String isName) throws IOException, SolrServerException {
        HttpSolrClient client = solrConnector.getClient();
        SolrQuery q = new SolrQuery();
        q.add("fq", "name:" + isName);
        q.add("q", "object_type:is");
        q.add("rows", String.valueOf(DEFAULT_QUERY_ROWS));
        QueryResponse rsp = client.query(q);
        SolrDocumentList docs = rsp.getResults();
        return converter.toCommonModel(docs.get(0));
    }

    @Override
    public CommonModel getByIrName(String irName) throws IOException, SolrServerException {
        HttpSolrClient client = solrConnector.getClient();
        SolrQuery q = new SolrQuery();
        q.add("fq", "name:" + irName);
        q.add("q", "object_type:ir");
        q.add("rows", String.valueOf(DEFAULT_QUERY_ROWS));
        QueryResponse rsp = client.query(q);
        SolrDocumentList docs = rsp.getResults();
        return converter.toCommonModel(docs.get(0));
    }

    @Override
    public CommonModel getByFuName(String fuName) throws IOException, SolrServerException {
        HttpSolrClient client = solrConnector.getClient();
        SolrQuery q = new SolrQuery();
        q.add("q", "object_type:fu");
        q.add("fq", "name:" + fuName);
        q.add("rows", String.valueOf(DEFAULT_QUERY_ROWS));
        QueryResponse rsp = client.query(q);
        SolrDocumentList docs = rsp.getResults();
        return converter.toCommonModel(docs.get(0));
    }

    @Override
    public List<CommonModel> getAllIs() throws IOException, SolrServerException {
        HttpSolrClient client = solrConnector.getClient();
        SolrQuery q = new SolrQuery();
        q.add("q", "object_type:is");
        q.add("rows", String.valueOf(DEFAULT_QUERY_ROWS));
        QueryResponse rsp = client.query(q);
        SolrDocumentList docs = rsp.getResults();
        return converter.toCommonModel(docs);
    }

    @Override
    public List<CommonModel> getSystemChildrenElement(String systemName) throws IOException, SolrServerException {
        HttpSolrClient client = solrConnector.getClient();
        SolrQuery q = new SolrQuery();
        q.add("q", "is_name:" + systemName);
        q.add("rows", String.valueOf(DEFAULT_QUERY_ROWS));
        QueryResponse rsp = client.query(q);
        SolrDocumentList docs = rsp.getResults();
        return converter.toCommonModel(docs);
    }

    @Override
    public List<CommonModel> getChildrenInformResources(String systemName) throws IOException, SolrServerException {
        HttpSolrClient client = solrConnector.getClient();
        SolrQuery q = new SolrQuery();
        q.add("fq", "is_name:" + systemName);
        q.add("q", "object_type:ir");
        q.add("rows", String.valueOf(DEFAULT_QUERY_ROWS));
        QueryResponse rsp = client.query(q);
        SolrDocumentList docs = rsp.getResults();
        return converter.toCommonModel(docs);
    }

    @Override
    public List<CommonModel> getAllIr() throws IOException, SolrServerException {
        HttpSolrClient client = solrConnector.getClient();
        SolrQuery q = new SolrQuery();
        q.add("q", "object_type:ir");
        q.add("rows", String.valueOf(DEFAULT_QUERY_ROWS));
        QueryResponse rsp = client.query(q);
        SolrDocumentList docs = rsp.getResults();
        return converter.toCommonModel(docs);
    }

    @Override
    public List<CommonModel> getAllFunctions() throws IOException, SolrServerException {
        HttpSolrClient client = solrConnector.getClient();
        SolrQuery q = new SolrQuery();
        q.add("q", "object_type:fu");
        q.add("rows", String.valueOf(DEFAULT_QUERY_ROWS));
        QueryResponse rsp = client.query(q);
        SolrDocumentList docs = rsp.getResults();
        return converter.toCommonModel(docs);
    }

    @Override
    public List<CommonModel> getChildrenFunctions(String elementName, String elementType) throws IOException, SolrServerException {
        HttpSolrClient client = solrConnector.getClient();
        SolrQuery q = new SolrQuery();
        if (elementType.equals("is")) {
            q.add("q", "is_name:" + elementName);
        } else if (elementType.equals("ir")) {
            q.add("q", "ir_name:" + elementName);
        } else if (elementType.equals("fu")) {
            q.add("q", "name:" + elementName);
        }
        q.add("fq", "object_type:fu");
        q.add("rows", String.valueOf(DEFAULT_QUERY_ROWS));
        QueryResponse rsp = client.query(q);
        SolrDocumentList docs = rsp.getResults();
        return converter.toCommonModel(docs);
    }

    @Override
    public List<CommonModel> getParentFunctions(String fuName) throws IOException, SolrServerException {
        return new ArrayList<>();
        /*CommonModel function = getByFuName(fuName);
        List<CommonModel> parentFunctions = new ArrayList<>();
        String irPath = function.getIrName();
        String functionPath = function.getObjNumPath().get(0);
        while (functionPath.contains(".")) {
            functionPath = functionPath.replaceFirst("\\.[0-9]+$", "");
            String finalFunctionPath = functionPath;
            Function<SolrDocument, Boolean> filter = doc -> {
                List<String> fieldValues = (List<String>) doc.getFieldValue("obj_num_path");
                if (fieldValues == null) return false;
                String path = fieldValues.get(0);
                String functionParentPath = irPath + "." + finalFunctionPath;
                return path.equals(functionParentPath);
            };
            JavaRDD<SolrDocument> filterDocuments = SparkConnector.getInstance().getResultRDD().filter(filter);
            parentFunctions.addAll(converter.toCommonModel(filterDocuments.collect()));
        }
        return parentFunctions;*/
    }

    @Override
    public List<CommonModel> getParentIrs(String fuName) throws IOException, SolrServerException {
        return new ArrayList<>();
        /*CommonModel function = getByFuName(fuName);
        Function<SolrDocument, Boolean> filter = doc -> {
            List<String> fieldValues = (List<String>) doc.getFieldValue("obj_num_path");
            if (fieldValues == null) return false;
            String functionPath = function.getObjNumPath().get(0).replaceFirst("^[0-9]+\\.[0-9]+\\.", "");
            Pattern p = Pattern.compile("^[0-9]+\\.[0-9]+\\." + functionPath);
            Matcher m = p.matcher(fieldValues.get(0));
            return m.matches();
        };
        JavaRDD<SolrDocument> filterDocuments = SparkConnector.getInstance().getResultRDD().filter(filter);
        return converter.toCommonModel(filterDocuments.collect());*/
    }

}
