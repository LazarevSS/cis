package ru.sibintek.cis.dao.impl;

import org.apache.solr.common.SolrDocument;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sibintek.cis.dao.PmIsDAO;
import ru.sibintek.cis.dao.converters.SolrDocumentConverter;
import ru.sibintek.cis.model.PmIrEntity;
import ru.sibintek.cis.model.PmIsEntity;
import ru.sibintek.cis.model.dto.InformResIsAndJoin;
import ru.sibintek.cis.util.SparkConnector;

import java.util.List;
import java.util.Map;

@Component
public class SolrPmIsDAO implements PmIsDAO {
    @Autowired
    private SolrDocumentConverter converter;

    @Override
    public void delete(PmIsEntity psIs) {

    }

    @Override
    public PmIsEntity getById(int id) {
        return null;
    }

    @Override
    public void save(int id) {

    }

    @Override
    public List<PmIsEntity> getAll() {
        Function<SolrDocument, Boolean> filter = doc -> (doc.getFieldValue("content_type").equals("is"));
        JavaRDD<SolrDocument> resultsRDD = SparkConnector.getInstance().getResultRDD();
        SolrDocument document = resultsRDD.collect().get(0);
        JavaRDD<SolrDocument> isEntities = resultsRDD.filter(filter);
        return converter.toIsEntity(isEntities.collect());
    }

    @Override
    public List<InformResIsAndJoin> getInformResIsAndJoins(Integer isId) {
        return null;
    }

    @Override
    public Map<PmIsEntity, List<PmIrEntity>> getSystemsAndInformRes() {
        return null;
    }
}
