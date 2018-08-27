package ru.sibintek.cis.controller;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.sibintek.cis.dao.CommonDao;
import ru.sibintek.cis.model.CommonModel;
import ru.sibintek.cis.model.dto.Link;
import ru.sibintek.cis.model.dto.Node;
import ru.sibintek.cis.util.VisualService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Controller
public class FunctionController {
    @Autowired
    private VisualService visualService;

    @Autowired
    private CommonDao commonDao;

    @RequestMapping(value = "/fu", method = RequestMethod.GET)
    public ModelAndView fuController(@RequestParam(value = "FUNAME", required = false) String fuName) throws IOException, SolrServerException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("fuModel", commonDao.getByFuName(fuName));
        modelAndView.addObject("childrenFunction", commonDao.getChildrenFunctions(fuName, "fu"));
        modelAndView.addObject("irModels", commonDao.getAllIr());
        modelAndView.addObject("irsParent", commonDao.getParentIrs(fuName));
        modelAndView.setViewName("functionView");
        return modelAndView;
    }

    @RequestMapping(value = "/fu/datasource", method = RequestMethod.GET)
    public ModelAndView fuDatasource(@RequestParam(value = "FUNAME", required = false) String fuName) throws IOException, SolrServerException {
        ModelAndView result = new ModelAndView("jsonView");
        Map<List<Link>, List<Node>> graph = visualService.getGraph(fuName);
        graph.forEach((links, nodes) -> {
            result.getModel().put("nodes", nodes);
            result.getModel().put("links", links);
        });
        return result;
    }

    @RequestMapping(value = "fu/getFu", method = RequestMethod.POST)
    public ModelAndView getInformResources() throws IOException, SolrServerException {
        ModelAndView result = new ModelAndView("jsonView");
        List<String> functions = new ArrayList<>();
        for (CommonModel function : commonDao.getAllFunctions()) {
            functions.add(function.getName());
        }
        result.getModel().put("elements", functions);
        return result;
    }
}
