package ru.sibintek.cis.util;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sibintek.cis.dao.CommonDao;
import ru.sibintek.cis.dao.converters.SolrDocumentConverter;
import ru.sibintek.cis.model.CommonModel;
import ru.sibintek.cis.model.dto.*;

import java.io.IOException;
import java.util.*;

@Service
public class VisualService {

    @Autowired
    private CommonDao commonDao;

    public Map<List<Link>, List<Node>> getGraph(String fuName) throws IOException, SolrServerException {
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

    private DrawMytreed3 convertToDrawMyTreed(CommonModel model) {
        DrawMytreed3 drawMytreed3 = new DrawMytreed3();
        drawMytreed3.setName(model.getName());
        drawMytreed3.setUrl("\\fu/?FUNAME=" + model.getName());
        return drawMytreed3;
    }

}
