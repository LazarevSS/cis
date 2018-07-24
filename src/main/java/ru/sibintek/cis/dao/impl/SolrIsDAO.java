package ru.sibintek.cis.dao.impl;

import org.apache.solr.common.SolrDocument;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sibintek.cis.dao.IsDAO;
import ru.sibintek.cis.dao.converters.SolrDocumentConverter;
import ru.sibintek.cis.model.FunctionModel;
import ru.sibintek.cis.model.IrModel;
import ru.sibintek.cis.model.IsModel;
import ru.sibintek.cis.util.SparkConnector;

import java.util.ArrayList;
import java.util.List;

@Component
public class SolrIsDAO implements IsDAO {
    private JavaRDD<SolrDocument> resultsRDD = SparkConnector.getInstance().getResultRDD();

    @Autowired
    private SolrDocumentConverter converter;

    @Autowired
    private SolrIrDAO irDAO;

    @Override
    public void delete(IsModel is) {

    }

    @Override
    public IsModel getById(int id) {
        Function<SolrDocument, Boolean> filter = doc -> (doc.getFieldValue("id").equals(String.valueOf(id)));
        JavaRDD<SolrDocument> isModels = resultsRDD.filter(filter);
        return converter.toIsModel(isModels.collect().get(0));
    }

    @Override
    public IsModel getByIdWithIr(int id) {
        IsModel isModel = getById(id);
        List<IrModel> irModels = irDAO.getByIsId(id);
        isModel.setIrs(irModels);
        return isModel;
    }

    @Override
    public void save(int id) {

    }

    @Override
    public List<FunctionModel> getJoinFunctions(int isId) {
        Function<SolrDocument, Boolean> filterType = doc -> (doc.getFieldValue("content_type").equals("fu"));
        Function<SolrDocument, Boolean> filterId = doc -> {
            List<String> isIds = (List<String>) doc.getFieldValue("is_id");
            return isIds.contains(String.valueOf(isId));
        };
        JavaRDD<SolrDocument> irModels = resultsRDD.filter(filterType).filter(filterId);
        return converter.toFunctionModel(irModels.collect());
    }

    @Override
    public List<IsModel> getAll() {
        Function<SolrDocument, Boolean> filter = doc -> (doc.getFieldValue("content_type").equals("is"));
        JavaRDD<SolrDocument> isModels = resultsRDD.filter(filter);
        return converter.toIsModel(isModels.collect());
    }

    @Override
    public List<IsModel> getRelationsIs(int isId) {
        Function<SolrDocument, Boolean> filterType = doc -> doc.getFieldValue("content_type").equals("fu");
        Function<SolrDocument, Boolean> filterId = doc -> {
            List<String> isIds = (List<String>) doc.getFieldValue("is_id");
            return isIds.contains(String.valueOf(isId));
        };
        JavaRDD<SolrDocument> functions = resultsRDD.filter(filterType).filter(filterId);
        List<String> isIds = new ArrayList<>();
        for (SolrDocument document : functions.collect()) {
            List<String> ids = (List<String>) document.get("is_id");
            ids.remove(String.valueOf(isId));
            isIds.addAll(ids);
        }
        List<IsModel> relatedIs = new ArrayList<>();
        for (String ids : isIds) {
            IsModel isValue = getById(Integer.valueOf(ids));
            relatedIs.add(isValue);
        }
        List<FunctionModel> test = getJoinFunctions(isId);
        return relatedIs;
    }
}