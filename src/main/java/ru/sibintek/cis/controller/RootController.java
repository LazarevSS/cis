package ru.sibintek.cis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.sibintek.cis.dao.PmIrDAO;
import ru.sibintek.cis.dao.PmIsDAO;
import ru.sibintek.cis.model.PmIrEntity;
import ru.sibintek.cis.model.PmIsEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class RootController {
    @Autowired
    @Qualifier("solrPmIsDAO")
    private PmIsDAO pmIsDAO;

    @Autowired
    @Qualifier("solrPmIrDAO")
    private PmIrDAO pmIrDAO;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView root() {
        ModelAndView modelAndView = new ModelAndView();
        List<PmIsEntity> isEntities = pmIsDAO.getAll();
        Map<PmIsEntity, List<PmIrEntity>> table = new HashMap<>();
        isEntities.forEach(is -> table.put(is, pmIrDAO.getByIsId(is.getId())));
        modelAndView.addObject("pmIsEntities", isEntities);
        modelAndView.addObject("pmIrEntities", pmIrDAO.getAll());
        modelAndView.addObject("table", table);
        modelAndView.setViewName("root");
        return modelAndView;
    }

    @RequestMapping(value = "/addIs", method = RequestMethod.POST)
    public ModelAndView addIs(@RequestParam(value = "isId", required = false) Integer isId) {
        ModelAndView result = new ModelAndView("jsonView");
        pmIsDAO.save(isId);
        return result;
    }
}
