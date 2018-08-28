package ru.sibintek.cis.dao.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sibintek.cis.dao.CommonDao;
import ru.sibintek.cis.dao.converters.SolrDocumentConverter;
import ru.sibintek.cis.model.CommonModel;
import ru.sibintek.cis.util.SolrConnector;

import java.io.IOException;
import java.util.*;


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
        if (docs.isEmpty()) return new CommonModel();
        return converter.toCommonModel(docs.get(0));
    }

    @Override
    public SolrDocument getByElementName(String elementName) throws IOException, SolrServerException {
        HttpSolrClient client = solrConnector.getClient();
        SolrQuery q = new SolrQuery();
        q.add("q", "name:" + "\"" + elementName + "\"");
        q.add("rows", String.valueOf(DEFAULT_QUERY_ROWS));
        QueryResponse rsp = client.query(q);
        SolrDocumentList docs = rsp.getResults();
        if (docs.isEmpty()) return new SolrDocument();
        return docs.get(0);
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
        if (docs.isEmpty()) return new CommonModel();
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
        if (docs.isEmpty()) return new CommonModel();
        return converter.toCommonModel(docs.get(0));
    }

    @Override
    public CommonModel getByFuName(String fuName) throws IOException, SolrServerException {
        HttpSolrClient client = solrConnector.getClient();
        SolrQuery q = new SolrQuery();
        q.add("q", "object_type:fu");
        q.add("fq", "name:" + "\"" + fuName + "\"");
        q.add("rows", String.valueOf(DEFAULT_QUERY_ROWS));
        QueryResponse rsp = client.query(q);
        SolrDocumentList docs = rsp.getResults();
        if (docs.isEmpty()) return new CommonModel();
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
            q.add("q", "name:" + "\"" + elementName + "\"");
            QueryResponse rsp = client.query(q);
            SolrDocumentList docs = rsp.getResults();
            List<Long> relationIds = (List<Long>) docs.get(0).getFieldValue("reference_id");
            Set<Long> relationIdsSet = new HashSet<>(relationIds);
            List<SolrDocument> childFunctions = new ArrayList<>();
            for(Long childId : relationIdsSet){
                SolrQuery query = new SolrQuery();
                query.add("q", "id:" + childId.toString());
                query.add("fq", "object_type:fu");
                SolrDocumentList solrDocumentList = client.query(query).getResults();
                if (solrDocumentList.isEmpty()) {
                    continue;
                }
                childFunctions.add(solrDocumentList.get(0));
            }
            return converter.toCommonModel(childFunctions);
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
    public List<CommonModel> getParentIrs(String fuId) throws IOException, SolrServerException {
        HttpSolrClient client = solrConnector.getClient();
        SolrQuery q = new SolrQuery();
        q.add("q", "{!term f=id}" + fuId);
        q.add("fq", "object_type:ir");
        q.add("rows", String.valueOf(DEFAULT_QUERY_ROWS));
        QueryResponse rsp = client.query(q);
        SolrDocumentList docs = rsp.getResults();
        List<CommonModel> parentIrs = new ArrayList<>(converter.toCommonModel(docs));
        SolrQuery q2 = new SolrQuery();
        q2.add("q", "id:" + fuId);
        parentIrs.addAll(converter.toCommonModel(client.query(q2).getResults()));
        return parentIrs;
    }

}
