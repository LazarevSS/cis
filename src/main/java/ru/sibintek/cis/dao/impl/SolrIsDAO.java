package ru.sibintek.cis.dao.impl;

import org.apache.solr.common.SolrDocument;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sibintek.cis.dao.IsDAO;
import ru.sibintek.cis.dao.converters.SolrDocumentConverter;
import ru.sibintek.cis.model.IsModel;
import ru.sibintek.cis.util.SparkConnector;

import java.util.List;

@Component
public class SolrIsDAO implements IsDAO {
    @Autowired
    private SolrDocumentConverter converter;

    @Override
    public void delete(IsModel psIs) {

    }

    @Override
    public IsModel getById(int id) {
        return null;
    }

    @Override
    public void save(int id) {

    }

    @Override
    public List<IsModel> getAll() {
        Function<SolrDocument, Boolean> filter = doc -> (doc.getFieldValue("content_type").equals("is"));
        JavaRDD<SolrDocument> resultsRDD = SparkConnector.getInstance().getResultRDD();
        SolrDocument document = resultsRDD.collect().get(0);
        JavaRDD<SolrDocument> isEntities = resultsRDD.filter(filter);
        return converter.toIsEntity(isEntities.collect());
    }
}
