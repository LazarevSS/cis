package ru.sibintek.cis.controller;

import org.apache.solr.client.solrj.SolrServerException;
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
}
