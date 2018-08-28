package ru.sibintek.cis.dao;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import ru.sibintek.cis.model.CommonModel;


import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CommonDao {
    void delete(String id) throws IOException, SolrServerException;

    SolrInputDocument saveFromExcel(SolrInputDocument document);

    void saveFromExcel(List<SolrInputDocument> documents);

    CommonModel getByIsId(String isId) throws IOException, SolrServerException;

    SolrDocument getByElementName(String elementName) throws IOException, SolrServerException;

    CommonModel getByIsName(String isName) throws IOException, SolrServerException;

    CommonModel getByIrName(String irName) throws IOException, SolrServerException;

    CommonModel getByFuName(String fuName) throws IOException, SolrServerException;

    List<CommonModel> getAllIs() throws IOException, SolrServerException;

    List<CommonModel> getSystemChildrenElement(String systemName) throws IOException, SolrServerException;

    List<CommonModel> getChildrenInformResources(String systemName) throws IOException, SolrServerException;

    List<CommonModel> getAllIr() throws IOException, SolrServerException;

    List<CommonModel> getAllFunctions() throws IOException, SolrServerException;

    List<CommonModel> getChildrenFunctions(String elementName, String elementType) throws IOException, SolrServerException;

    List<CommonModel> getParentFunctions(String fuName) throws IOException, SolrServerException;

    List<CommonModel> getParentIrs(String fuId) throws IOException, SolrServerException;

}
