package ru.sibintek.cis.dao.impl;

import org.apache.solr.common.SolrDocument;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sibintek.cis.dao.IrDAO;
import ru.sibintek.cis.dao.converters.SolrDocumentConverter;
import ru.sibintek.cis.model.IrModel;
import ru.sibintek.cis.util.SparkConnector;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SolrIrDAO implements IrDAO {
    @Autowired
    private SolrDocumentConverter converter;

    @Override
    public void delete(IrModel psIr) {

    }

    @Override
    public IrModel getById(int id) {
        return null;
    }

    @Override
    public void save(IrModel psIr, int id) {

    }

    @Override
    public List<IrModel> getAll() {
        Function<SolrDocument, Boolean> filter = doc -> (doc.getFieldValue("content_type").equals("ir"));
        JavaRDD<SolrDocument> irEntities = SparkConnector.getInstance().getResultRDD().filter(filter);
        return converter.toIrEntity(irEntities.collect());
    }

    @Override
    public List<IrModel> getByIsId(int id) {
        return getAll().stream().filter(ir -> ir.getIsId() == id).collect(Collectors.toList());
    }

}
