package ru.sibintek.cis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.sibintek.cis.dao.IrDAO;
import ru.sibintek.cis.dao.IsDAO;
import ru.sibintek.cis.model.IrModel;
import ru.sibintek.cis.model.IsModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class RootController {
    @Autowired
    @Qualifier("solrIsDAO")
    private IsDAO isDAO;

    @Autowired
    @Qualifier("solrIrDAO")
    private IrDAO irDAO;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView root() {
        ModelAndView modelAndView = new ModelAndView();
        List<IsModel> isEntities = isDAO.getAll();
        Map<IsModel, List<IrModel>> table = new HashMap<>();
        isEntities.forEach(is -> table.put(is, irDAO.getByIsId(is.getId())));
        modelAndView.addObject("pmIsEntities", isEntities);
        modelAndView.addObject("pmIrEntities", irDAO.getAll());
        modelAndView.addObject("table", table);
        modelAndView.setViewName("root");
        return modelAndView;
    }

    @RequestMapping(value = "/addIs", method = RequestMethod.POST)
    public ModelAndView addIs(@RequestParam(value = "isId", required = false) Integer isId) {
        ModelAndView result = new ModelAndView("jsonView");
        isDAO.save(isId);
        return result;
    }

    @RequestMapping(value = "/datasource", method = RequestMethod.GET)
    public ModelAndView isDatasource() {
        ModelAndView result = new ModelAndView("jsonView");
        result.getModel().put("content", isDAO.getVisualizingData());
        result.getModel().put("sortOrder", "value-desc");
        return result;
    }
}
