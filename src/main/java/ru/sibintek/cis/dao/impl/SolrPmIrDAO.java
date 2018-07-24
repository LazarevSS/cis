package ru.sibintek.cis.dao.impl;

import org.apache.solr.common.SolrDocument;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.springframework.stereotype.Component;
import ru.sibintek.cis.dao.PmIrDAO;
import ru.sibintek.cis.dao.converters.SolrDocumentConverter;
import ru.sibintek.cis.model.PmIrEntity;
import ru.sibintek.cis.model.dto.FuncAreaIrAndJoin;
import ru.sibintek.cis.util.SparkConnector;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SolrPmIrDAO implements PmIrDAO {
    private SolrDocumentConverter converter = new SolrDocumentConverter();

    @Override
    public void delete(PmIrEntity psIr) {

    }

    @Override
    public PmIrEntity getById(int id) {
        return null;
    }

    @Override
    public void save(PmIrEntity psIr, int id) {

    }

    @Override
    public List<PmIrEntity> getAll() {
        Function<SolrDocument, Boolean> filter = doc -> (doc.getFieldValue("content_type").equals("ir"));
        JavaRDD<SolrDocument> irEntities = SparkConnector.getInstance().getResultRDD().filter(filter);
        return converter.toIrEntity(irEntities.collect());
    }

    @Override
    public List<PmIrEntity> getByIsId(int id) {
        return getAll().stream().filter(ir -> ir.getIsId() == id).collect(Collectors.toList());
    }

    @Override
    public List<FuncAreaIrAndJoin> getFuncAreaIrAndJoins(Integer irId) {
        return null;
    }
}
