package ru.sibintek.cis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.sibintek.cis.dao.FuncAreaDAO;
import ru.sibintek.cis.dao.IrDAO;
import ru.sibintek.cis.model.FuncAreaModel;

@Controller
public class faController {

    @Autowired
    private IrDAO irDAO;

    @Autowired
    private FuncAreaDAO funcAreaDAO;

    @Autowired
    private FuncAreaDAO funcAreaDAO;

    @RequestMapping(value = "/fa", method = RequestMethod.GET)
    public ModelAndView isController(@RequestParam(value = "FAID", required = false) Integer faId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pmFaEntity", funcAreaDAO.getById(faId));
        modelAndView.addObject("functionAndRelatedJoins", funcAreaDAO.getFunctionAndRelatedJoins(faId));
        modelAndView.addObject("pmIrEntities", irDAO.getAll());
        modelAndView.setViewName("faView");
        return modelAndView;
    }

    @RequestMapping(value = "/fa/datasource", method = RequestMethod.GET)
    public ModelAndView isDatasource(@RequestParam(value = "FAID", required = false) Integer faId) {
        ModelAndView result = new ModelAndView("jsonView");
        FuncAreaModel funcAreaEntity = funcAreaDAO.getById(faId);
        //List<DataGraphDrawTree> dataGraphDrawTrees = dataForGraphDAO.getDataGraphFa(faId);
        result.getModel().put("name", funcAreaEntity.getScenarioName());
        //result.getModel().put("children", dataGraphDrawTrees);
        return result;
    }
}
