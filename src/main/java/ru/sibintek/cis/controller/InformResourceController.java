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
import ru.sibintek.cis.service.GraphService;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


@Controller
public class InformResourceController {
    @Autowired
    private GraphService graphService;

    @Autowired
    private CommonDao commonDao; 

    @RequestMapping(value = "/ir", method = RequestMethod.GET)
    public ModelAndView irController(@RequestParam(value = "IRNAME", required = false) String irName) throws IOException, SolrServerException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("irModel", commonDao.getByIrName(irName));
        modelAndView.addObject("irModels", commonDao.getAllIr());
        modelAndView.addObject("table", commonDao.getAllIr());
        modelAndView.setViewName("informResourceView");
        return modelAndView;
    }

    @RequestMapping(value = "/ir/getGraph", method = RequestMethod.GET)
    public ModelAndView irGraph(@RequestParam(value = "IRNAME", required = false) String irName) throws IOException, SolrServerException {
        ModelAndView result = new ModelAndView("jsonView");
        result.getModel().put("name", irName);
        result.getModel().put("children", graphService.getGraphInformResource(irName));
        return result;
    }

    @RequestMapping(value = "ir/getIr", method = RequestMethod.POST)
    public ModelAndView getInformResources() throws IOException, SolrServerException {
        ModelAndView result = new ModelAndView("jsonView");
        HashSet<String> informResources = new HashSet<>();
        for (CommonModel informResource : commonDao.getAllIr()) {
            informResources.add(informResource.getName());
        }
        result.getModel().put("elements", informResources);
        return result;
    }
}
