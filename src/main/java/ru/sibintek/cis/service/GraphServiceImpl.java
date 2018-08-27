package ru.sibintek.cis.service;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sibintek.cis.dao.CommonDao;
import ru.sibintek.cis.model.CommonModel;
import ru.sibintek.cis.model.dto.DrawBubbleChartModel;
import ru.sibintek.cis.model.dto.IsVisualizingData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
}
