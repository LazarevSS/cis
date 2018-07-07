package ru.sibintek.cis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.sibintek.cis.dao.DataForGraphDAO;
import ru.sibintek.cis.dao.PmFuncAreaDAO;
import ru.sibintek.cis.dao.PmIrDAO;
import ru.sibintek.cis.model.PmFuncAreaEntity;
import ru.sibintek.cis.model.dto.DataGraphDrawTree;

import java.util.List;

@Controller
public class faController {
    @Autowired
    private DataForGraphDAO dataForGraphDAO;

    @Autowired
    private PmIrDAO pmIrDAO;

    @Autowired
    private PmFuncAreaDAO funcAreaDAO;

    @Autowired
    private PmFuncAreaDAO pmFuncAreaDAO;

    @RequestMapping(value = "/fa", method = RequestMethod.GET)
    public ModelAndView isController(@RequestParam(value = "FAID", required = false) Integer faId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pmFaEntity", pmFuncAreaDAO.getById(faId));
        modelAndView.addObject("functionAndRelatedJoins", pmFuncAreaDAO.getFunctionAndRelatedJoins(faId));
        modelAndView.addObject("pmIrEntities", pmIrDAO.getAll());
        modelAndView.setViewName("faView");
        return modelAndView;
    }

    @RequestMapping(value = "/fa/datasource", method = RequestMethod.GET)
    public ModelAndView isDatasource(@RequestParam(value = "FAID", required = false) Integer faId) {
        ModelAndView result = new ModelAndView("jsonView");
        PmFuncAreaEntity funcAreaEntity = pmFuncAreaDAO.getById(faId);
        List<DataGraphDrawTree> dataGraphDrawTrees = dataForGraphDAO.getDataGraphFa(faId);
        result.getModel().put("name", funcAreaEntity.getScenarioName());
        result.getModel().put("children", dataGraphDrawTrees);
        return result;
    }
}
