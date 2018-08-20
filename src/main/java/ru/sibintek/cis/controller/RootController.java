package ru.sibintek.cis.controller;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.sibintek.cis.dao.CommonDao;
import ru.sibintek.cis.util.SolrConnector;
import ru.sibintek.cis.util.VisualService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
public class RootController {
    @Autowired
    private VisualService visualService;

    @Autowired
    private CommonDao commonDao;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView root() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("table", commonDao.getAllIr());
        modelAndView.setViewName("root");
        return modelAndView;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addIs(@RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "type", required = false) String type) throws IOException, SolrServerException {

        commonDao.save(name, type);
        return new ModelAndView("jsonView");
    }

    @RequestMapping(value = "/datasource", method = RequestMethod.GET)
    public ModelAndView rootDatasource() {
        ModelAndView result = new ModelAndView("jsonView");
        result.getModel().put("content", visualService.getVisualizingDataForRoot());
        result.getModel().put("sortOrder", "value-desc");
        return result;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView search(@RequestParam(value = "value", required = false) String value,
                              @RequestParam(value = "type", required = false) String type) throws IOException, SolrServerException {
        ModelAndView result = new ModelAndView("jsonView");
        String urlString = "http://192.168.1.8:8983/solr/mycoll";
        HttpSolrClient client = new HttpSolrClient.Builder(urlString).build();
        if (value == null || value.isEmpty()) {
            return result;
        }
        SolrQuery q = new SolrQuery();
        Set<String> foundWorks = new HashSet<>();
        if (type.equals("is")) {
            q.add("q", "is_name:" + value + "*");
            QueryResponse rsp = client.query(q);
            SolrDocumentList docs = rsp.getResults();
            docs.forEach(doc -> foundWorks.add(String.valueOf(doc.getFieldValue("is_name"))));
        } if (type.equals("ir")) {
            q.add("q", "ir_name:" + value + "*");
            QueryResponse rsp = client.query(q);
            SolrDocumentList docs = rsp.getResults();
            docs.forEach(doc -> foundWorks.add(String.valueOf(doc.getFieldValue("ir_name"))));
        }
        result.getModel().put("foundWorks", foundWorks);
        return result;
    }
}
