package ru.sibintek.cis.controller;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.sibintek.cis.dao.CommonDao;
import ru.sibintek.cis.service.GraphService;
import ru.sibintek.cis.util.SolrParserUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


@Controller
public class RootController {
    @Autowired
    private SolrParserUtils solrParserUtils;

    @Autowired
    private GraphService graphService;

    @Autowired
    private CommonDao commonDao;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView root() throws IOException, SolrServerException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("table", commonDao.getAllIr());
        modelAndView.setViewName("root");
        return modelAndView;
    }

    @RequestMapping(value = "/getGraph", method = RequestMethod.GET)
    public ModelAndView rootGraph() throws IOException, SolrServerException {
        ModelAndView result = new ModelAndView("jsonView");
        result.getModel().put("content", graphService.getGraphSystems());
        result.getModel().put("sortOrder", "value-desc");
        return result;
    }
}
