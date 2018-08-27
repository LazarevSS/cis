package ru.sibintek.cis.service;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sibintek.cis.dao.CommonDao;
import ru.sibintek.cis.model.CommonModel;
import ru.sibintek.cis.model.dto.DrawBubbleChartModel;
import ru.sibintek.cis.model.dto.IsVisualizingData;
import ru.sibintek.cis.model.dto.Link;
import ru.sibintek.cis.model.dto.Node;

import java.io.IOException;
import java.util.*;

@Service
public class GraphServiceImpl implements GraphService {
    @Autowired
    private CommonDao commonDao;

    @Override
    public List<IsVisualizingData> getGraphSystems() throws IOException, SolrServerException {
        List<CommonModel> commonModels = commonDao.getAllIs();
        Set<String> systemsName = new HashSet<>();
        commonModels.forEach(isModel -> systemsName.add(isModel.getName()));
        List<IsVisualizingData> visualizingDataList = new ArrayList<>();
        for (String systemName : systemsName) {
            IsVisualizingData visualizingData = new IsVisualizingData();
            visualizingData.setLabel(systemName);
            visualizingData.setValue(commonDao.getSystemChildrenElement(systemName).size());
            visualizingData.setUrl("\\is/?ISNAME=" + systemName);
            visualizingData.setCaption(systemName);
            visualizingDataList.add(visualizingData);
        }
        return visualizingDataList;
    }

    @Override
    public List<DrawBubbleChartModel> getGraphSystem(String systemName) throws IOException, SolrServerException {
        List<DrawBubbleChartModel> drawBubbleChartModels = new ArrayList<>();
        List<CommonModel> irs = commonDao.getChildrenInformResources(systemName);
        for (CommonModel ir : irs) {
            DrawBubbleChartModel model = new DrawBubbleChartModel();
            model.setLabel(ir.getIrName());
            model.setUrl("\\ir/?IRNAME=" + ir.getIrName());
            model.setValue(commonDao.getChildrenFunctions(ir.getName(), "ir").size());
            drawBubbleChartModels.add(model);
        }
        return drawBubbleChartModels;
    }

    @Override
    public List<DrawBubbleChartModel> getGraphInformResource(String informResourceName) throws IOException, SolrServerException {
        List<DrawBubbleChartModel> drawBubbleChartModels = new ArrayList<>();
        List<CommonModel> functions = commonDao.getChildrenFunctions(informResourceName, "ir");
        for (CommonModel function : functions) {
            DrawBubbleChartModel model = new DrawBubbleChartModel();
            model.setLabel(function.getName());
            model.setUrl("\\fu/?FUNAME=" + function.getName());
            model.setValue(function.getRelation_id().size());
            drawBubbleChartModels.add(model);
        }
        return drawBubbleChartModels;
    }

    @Override
    public Map<List<Link>, List<Node>> getGraphFunction(String functionId, String functionName) throws IOException, SolrServerException {
        List<Link> links = new ArrayList<>();
        List<CommonModel> parentIrs = commonDao.getParentIrs(functionId);
        List<Link> linkIrs = new ArrayList<>();
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < parentIrs.size(); i++) {
            Link linkIr = new Link();
            linkIr.setSource(i * 2);
            linkIr.setTarget(i * 2 + 1);
            linkIrs.add(linkIr);
            Node nodeIs = new Node();
            nodeIs.setName(parentIrs.get(i).getIsName());
            nodeIs.setTitle(parentIrs.get(i).getIsName());
            nodeIs.setIcon_url("\\resources/img/s_pckstd.gif");
            nodeIs.setUrl("\\is/?ISNAME=" + parentIrs.get(0).getIsName());
            Node nodeIr = new Node();
            nodeIr.setName(parentIrs.get(i).getIrName());
            nodeIr.setTitle(parentIrs.get(i).getIrName());
            nodeIr.setIcon_url("\\resources/img/s_b_renm.gif");
            nodeIr.setUrl("\\ir/?IRNAME=" + parentIrs.get(0).getIrName());
            nodes.add(nodeIs);
            nodes.add(nodeIr);
        }
        CommonModel function = commonDao.getByFuName(functionName);
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
