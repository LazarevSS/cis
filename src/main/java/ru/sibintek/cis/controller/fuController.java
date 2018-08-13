package ru.sibintek.cis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.sibintek.cis.dao.CommonDao;
import ru.sibintek.cis.model.dto.Link;
import ru.sibintek.cis.model.dto.Node;
import ru.sibintek.cis.util.VisualService;

import java.util.List;
import java.util.Map;

@Controller
public class fuController {
    @Autowired
    private VisualService visualService;

    @Autowired
    private CommonDao commonDao;

    @RequestMapping(value = "/fu", method = RequestMethod.GET)
    public ModelAndView fuController(@RequestParam(value = "FUNAME", required = false) String fuName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("fuModel", commonDao.getByFuName(fuName));
        modelAndView.addObject("childrenFunction", commonDao.getChildrenFunctions(fuName));
        modelAndView.addObject("irModels", commonDao.getAllIr());
        modelAndView.addObject("irsParent", commonDao.getParentIrs(fuName));
        modelAndView.setViewName("fuView");
        return modelAndView;
    }

    @RequestMapping(value = "/fu/datasource", method = RequestMethod.GET)
    public ModelAndView fuDatasource(@RequestParam(value = "FUNAME", required = false) String fuName) {
        ModelAndView result = new ModelAndView("jsonView");
        Map<List<Link>, List<Node>> graph = visualService.getGraph(fuName);
        graph.forEach((links, nodes) -> {
            result.getModel().put("nodes", nodes);
            result.getModel().put("links", links);
        });
        return result;
    }
}
