package ru.sibintek.cis.service;

import org.apache.solr.client.solrj.SolrServerException;
import ru.sibintek.cis.model.dto.DrawBubbleChartModel;
import ru.sibintek.cis.model.dto.IsVisualizingData;
import ru.sibintek.cis.model.dto.Link;
import ru.sibintek.cis.model.dto.Node;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface GraphService {

    List<IsVisualizingData> getGraphSystems() throws IOException, SolrServerException;

    List<DrawBubbleChartModel> getGraphSystem(String systemName) throws IOException, SolrServerException;

    List<DrawBubbleChartModel> getGraphInformResource(String informResourceName) throws IOException, SolrServerException;

    Map<List<Link>, List<Node>> getGraphFunction(String functionId, String functionName) throws IOException, SolrServerException;
}
