package ru.sibintek.cis.service;

import org.apache.solr.client.solrj.SolrServerException;
import ru.sibintek.cis.model.dto.DrawBubbleChartModel;
import ru.sibintek.cis.model.dto.IsVisualizingData;

import java.io.IOException;
import java.util.List;

public interface GraphService {

    List<IsVisualizingData> getGraphSystems() throws IOException, SolrServerException;

    List<DrawBubbleChartModel> getGraphSystem(String systemName) throws IOException, SolrServerException;

    List<DrawBubbleChartModel> getGraphInformResource(String informResourceName) throws IOException, SolrServerException;
}
