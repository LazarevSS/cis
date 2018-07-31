package ru.sibintek.cis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.sibintek.cis.dao.CommonDao;


@Controller
public class IrController {

    @Autowired
    private CommonDao commonDao;

    @RequestMapping(value = "/ir", method = RequestMethod.GET)
    public ModelAndView isController(@RequestParam(value = "IRID", required = false) Integer irId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("irModel", commonDao.getByIdWithIs(irId));
        modelAndView.addObject("irModels", commonDao.getAllIr());
       // modelAndView.addObject("table", commonDao.getRelationsIr(irId));
        modelAndView.setViewName("irView");
        return modelAndView;
    }

    @RequestMapping(value = "/ir/datasource", method = RequestMethod.GET)
    public ModelAndView isDatasource(@RequestParam(value = "IRID", required = false) Integer irId) {
        ModelAndView result = new ModelAndView("jsonView");
        result.getModel().put("name", "Tcode");
        return result;
    }
}
