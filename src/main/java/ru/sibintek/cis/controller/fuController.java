package ru.sibintek.cis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.sibintek.cis.dao.CommonDao;

@Controller
public class fuController {
    @Autowired
    private CommonDao commonDao;

    @RequestMapping(value = "/fu", method = RequestMethod.GET)
    public ModelAndView isController(@RequestParam(value = "IRID", required = false) Integer irId,
                                     @RequestParam(value = "FAID", required = false) Integer faId,
                                     @RequestParam(value = "FUID", required = false) Integer fuId) {
        ModelAndView modelAndView = new ModelAndView();
        /*modelAndView.addObject("pmFaEntity", pmFuncAreaDAO.getById(faId));
        modelAndView.addObject("functionAndRelatedJoins", pmFuncAreaDAO.getFunctionAndRelatedJoins(faId));*/
        //modelAndView.addObject("pmFunctionEntity", functionDAO.getById(fuId));
        modelAndView.addObject("pmIrEntities", commonDao.getAllIr());
        modelAndView.setViewName("fuView");
        return modelAndView;
    }
}
