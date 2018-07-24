package ru.sibintek.cis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.sibintek.cis.dao.PmFunctionDAO;
import ru.sibintek.cis.dao.PmIrDAO;

@Controller
public class fuController {
    @Autowired
    @Qualifier("jdbcPmIrDAO")
    private PmIrDAO pmIrDAO;

    @Autowired
    private PmFunctionDAO pmFunctionDAO;

    @RequestMapping(value = "/fu", method = RequestMethod.GET)
    public ModelAndView isController(@RequestParam(value = "IRID", required = false) Integer irId,
                                     @RequestParam(value = "FAID", required = false) Integer faId,
                                     @RequestParam(value = "FUID", required = false) Integer fuId) {
        ModelAndView modelAndView = new ModelAndView();
        /*modelAndView.addObject("pmFaEntity", pmFuncAreaDAO.getById(faId));
        modelAndView.addObject("functionAndRelatedJoins", pmFuncAreaDAO.getFunctionAndRelatedJoins(faId));*/
        modelAndView.addObject("pmFunctionEntity", pmFunctionDAO.getById(fuId));
        modelAndView.addObject("functionStructure", pmFunctionDAO.getFunctionStructure(fuId));
        modelAndView.addObject("functionsInOtherFuncAreas", pmFunctionDAO.getFunctionsInOtherFuncAreas(fuId, faId, irId));
        modelAndView.addObject("pmIrEntities", pmIrDAO.getAll());
        modelAndView.setViewName("fuView");
        return modelAndView;
    }
}
