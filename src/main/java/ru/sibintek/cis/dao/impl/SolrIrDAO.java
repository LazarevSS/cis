package ru.sibintek.cis.dao.impl;

import org.apache.solr.common.SolrDocument;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sibintek.cis.dao.IrDAO;
import ru.sibintek.cis.dao.converters.SolrDocumentConverter;
import ru.sibintek.cis.model.FunctionModel;
import ru.sibintek.cis.model.IrModel;
import ru.sibintek.cis.model.IsModel;
import ru.sibintek.cis.model.dto.DrawBubbleChartModel;
import ru.sibintek.cis.util.SparkConnector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SolrIrDAO implements IrDAO {
    private JavaRDD<SolrDocument> resultsRDD = SparkConnector.getInstance().getResultRDD();

    @Autowired
    private SolrDocumentConverter converter;

    @Autowired
    private SolrIsDAO isDAO;

    @Override
    public void delete(IrModel psIr) {

    }

    @Override
    public IrModel getById(int id) {
        return null;
    }

    @Override
    public IrModel getByIdWithIs(int id) {
        return null;
    }


    @Override
    public void save(IrModel psIr, int id) {

    }

    @Override
    public List<IrModel> getAll() {
        Function<SolrDocument, Boolean> filter = doc -> (doc.getFieldValue("object_type").equals("ir"));
        JavaRDD<SolrDocument> irEntities = resultsRDD.filter(filter);
        return converter.toIrModel(irEntities.collect());
    }

    @Override
    public List<IrModel> getByIsId(int id) {
        return null;
    }

    @Override
    public List<FunctionModel> getJoinFunctions(int irId) {
        Function<SolrDocument, Boolean> filterType = doc -> (doc.getFieldValue("content_type").equals("fu"));
        Function<SolrDocument, Boolean> filterId = doc -> {
            List<String> irIds = (List<String>) doc.getFieldValue("ir_id");
            return irIds.contains(String.valueOf(irId));
        };
        JavaRDD<SolrDocument> joinFunctions = resultsRDD.filter(filterType).filter(filterId);
        return converter.toFunctionModel(joinFunctions.collect());
    }

    @Override
    public List<IrModel> getRelationsIr(int irId) {
        Function<SolrDocument, Boolean> filterType = doc -> doc.getFieldValue("content_type").equals("fu");
        Function<SolrDocument, Boolean> filterId = doc -> {
            List<String> isIds = (List<String>) doc.getFieldValue("ir_id");
            return isIds.contains(String.valueOf(irId));
        };
        JavaRDD<SolrDocument> functions = resultsRDD.filter(filterType).filter(filterId);
        List<String> isIds = new ArrayList<>();
        for (SolrDocument document : functions.collect()) {
            List<String> ids = (List<String>) document.get("ir_id");
            ids.remove(String.valueOf(irId));
            isIds.addAll(ids);
        }
        List<IrModel> relatedIr = new ArrayList<>();
        for (String id : isIds) {
            IrModel joinIrModel = getByIdWithIs(Integer.valueOf(id));
            //joinIrModel.setJoinFunctions(getJoinFunctions(Integer.valueOf(id)));
            relatedIr.add(joinIrModel);
        }
        return relatedIr;
    }

    @Override
    public List<DrawBubbleChartModel> getVisualizingData(int isId) {
        List<DrawBubbleChartModel> visualizingDataList = new ArrayList<>();
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
            DrawBubbleChartModel visualizingData = new DrawBubbleChartModel();
            //visualizingData.setLabel(irModel.getIrName());
            visualizingData.setValue(irChildrenElement.count());
            visualizingData.setUrl("\\ir?IRID=" + irModel.getId());
            visualizingDataList.add(visualizingData);
        }
        return visualizingDataList;
    }

}
