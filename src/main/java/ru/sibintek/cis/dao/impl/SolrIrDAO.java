package ru.sibintek.cis.dao.impl;

import org.apache.solr.common.SolrDocument;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sibintek.cis.dao.IrDAO;
import ru.sibintek.cis.dao.converters.SolrDocumentConverter;
import ru.sibintek.cis.model.IrModel;
import ru.sibintek.cis.model.dto.IrVisualizingData;
import ru.sibintek.cis.util.SparkConnector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SolrIrDAO implements IrDAO {
    private JavaRDD<SolrDocument> resultsRDD = SparkConnector.getInstance().getResultRDD();

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
        JavaRDD<SolrDocument> irEntities = resultsRDD.filter(filter);
        return converter.toIrModel(irEntities.collect());
    }

    @Override
    public List<IrModel> getByIsId(int id) {
        return getAll().stream().filter(ir -> ir.getIsId() == id).collect(Collectors.toList());
    }

    @Override
    public List<IrVisualizingData> getVisualizingData(int isId) {
        List<IrVisualizingData> visualizingDataList = new ArrayList<>();
        List<IrModel> irModels = getByIsId(isId);
        for (IrModel irModel : irModels) {
            Function<SolrDocument, Boolean> filterId = doc -> {
                List<String> irIds = (List<String>) doc.getFieldValue("ir_id");
                if (irIds == null) {
                    return false;
                }
                return irIds.contains(String.valueOf(irModel.getId()));
            };
            JavaRDD<SolrDocument> irChildrenElement = resultsRDD.filter(filterId);
            IrVisualizingData visualizingData = new IrVisualizingData();
            visualizingData.setLabel(irModel.getIrName());
            visualizingData.setValue(irChildrenElement.count());
            visualizingData.setUrl("\\ir?IRID=" + irModel.getId());
            visualizingDataList.add(visualizingData);
        }
        return visualizingDataList;
    }

}
