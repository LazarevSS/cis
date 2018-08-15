package ru.sibintek.cis.util;

import org.apache.solr.common.SolrDocument;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sibintek.cis.dao.CommonDao;
import ru.sibintek.cis.dao.converters.SolrDocumentConverter;
import ru.sibintek.cis.model.CommonModel;
import ru.sibintek.cis.model.dto.*;

import java.util.*;

@Service
public class VisualService {
    private JavaRDD<SolrDocument> resultsRDD = SparkConnector.getInstance().getResultRDD();

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private SolrDocumentConverter converter;

    public List<IsVisualizingData> getVisualizingDataForRoot() {
        List<CommonModel> commonModels = commonDao.getAllIr();
        Set<String> systemsName = new HashSet<>();
        commonModels.forEach(irModel -> systemsName.add(irModel.getIs_name()));
        List<IsVisualizingData> visualizingDataList = new ArrayList<>();
        for (String systemName : systemsName) {
            Function<SolrDocument, Boolean> filter = doc -> (doc.getFieldValue("is_name").equals(systemName));
            JavaRDD<SolrDocument> systemChildrenElement = resultsRDD.filter(filter);
            IsVisualizingData visualizingData = new IsVisualizingData();
            visualizingData.setLabel(systemName);
            visualizingData.setValue(systemChildrenElement.count());
            visualizingData.setUrl("\\is/?ISNAME=" + systemName);
            visualizingData.setCaption(systemName);
            visualizingDataList.add(visualizingData);
        }
        return visualizingDataList;
    }

    public List<DrawBubbleChartModel> getVisualizingDataForIs(String isName) {
        List<DrawBubbleChartModel> drawBubbleChartModels = new ArrayList<>();
        Function<SolrDocument, Boolean> filter = doc -> (doc.getFieldValue("object_type").equals("ir") && doc.getFieldValue("is_name").equals(isName));
        JavaRDD<SolrDocument> systemChildrenIr = resultsRDD.filter(filter);
        List<CommonModel> irs = converter.toCommonModel(systemChildrenIr.collect());
        for (CommonModel ir : irs) {
            Function<SolrDocument, Boolean> irFilter = doc -> (doc.getFieldValue("object_type").equals("fu") && doc.getFieldValue("ir_name").equals(ir.getIr_name()));
            JavaRDD<SolrDocument> functions = resultsRDD.filter(irFilter);
            DrawBubbleChartModel model = new DrawBubbleChartModel();
            model.setLabel(ir.getIr_name());
            model.setUrl("\\ir/?IRNAME=" + ir.getIr_name());
            model.setValue(functions.count());
            drawBubbleChartModels.add(model);
        }
        return drawBubbleChartModels;
    }

    public List<DrawMytreed3> getVisualizingDataForIr(String irName) {
        List<DrawMytreed3> drawMytreed3Models = new ArrayList<>();
        Map<CommonModel, List<CommonModel>> docAndJoinDoc = commonDao.getIrRelations(irName);
        for (Map.Entry<CommonModel, List<CommonModel>> entry : docAndJoinDoc.entrySet()) {
            for (CommonModel function : entry.getValue()) {
                DrawMytreed3 drawMytreed3 = new DrawMytreed3();
                drawMytreed3.setName(function.getName());
                drawMytreed3.setSize(function.getId());
                drawMytreed3.setUrl("\\fu/?FUNAME=" + function.getName());
                drawMytreed3Models.add(drawMytreed3);
            }
        }
        return drawMytreed3Models;
    }

    public Map<List<Link>, List<Node>> getGraph(String fuName) {
        List<Link> links = new ArrayList<>();
        List<CommonModel> parentIrs = commonDao.getParentIrs(fuName);
        List<Link> linkIrs = new ArrayList<>();
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < parentIrs.size(); i++) {
            Link linkIr = new Link();
            linkIr.setSource(i * 2);
            linkIr.setTarget(i * 2 + 1);
            linkIrs.add(linkIr);
            Node nodeIs = new Node();
            nodeIs.setName(parentIrs.get(i).getIs_name());
            nodeIs.setTitle(parentIrs.get(i).getIs_name());
            nodeIs.setIcon_url("\\resources/img/s_pckstd.gif");
            nodeIs.setUrl("\\is/?ISNAME=" + parentIrs.get(0).getIs_name());
            Node nodeIr = new Node();
            nodeIr.setName(parentIrs.get(i).getIr_name());
            nodeIr.setTitle(parentIrs.get(i).getIr_name());
            nodeIr.setIcon_url("\\resources/img/s_b_renm.gif");
            nodeIr.setUrl("\\ir/?IRNAME=" + parentIrs.get(0).getIr_name());
            nodes.add(nodeIs);
            nodes.add(nodeIr);
        }
        CommonModel function = commonDao.getByFuName(fuName);
        Node nodeFunction = new Node();
        nodeFunction.setName(function.getName());
        nodeFunction.setTitle(function.getName());
        nodeFunction.setIcon_url("\\resources/img/s_b_tree.gif");
        nodeFunction.setUrl("\\fu/?FUNAME=" + function.getName());
        nodes.add(nodeFunction);
        List<Link> linkFunctions = new ArrayList<>();
        for (Link linkIr : linkIrs) {
            Link linkFunction = new Link();
            linkFunction.setSource(linkIr.getTarget());
            linkFunction.setTarget(nodes.size() - 1);
            linkFunctions.add(linkFunction);
        }
        links.addAll(linkIrs);
        links.addAll(linkFunctions);
        Map<List<Link>, List<Node>> result = new HashMap<>();
        result.put(links, nodes);
        return result;
    }

}
