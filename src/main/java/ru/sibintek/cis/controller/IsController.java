package ru.sibintek.cis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.sibintek.cis.dao.IrDAO;

import ru.sibintek.cis.dao.IsDAO;


@Controller
public class IsController {

    @Autowired
    private IrDAO irDAO;

    @Autowired
    private IsDAO isDAO;

    @RequestMapping(value = "/is", method = RequestMethod.GET)
    public ModelAndView isController(@RequestParam(value = "ISID", required = false) Integer isId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("isModel", isDAO.getById(isId));
        modelAndView.addObject("irModel", irDAO.getAll());
        modelAndView.addObject("table", isDAO.getRelationsIs(isId));
        modelAndView.setViewName("isView");
        return modelAndView;
    }

    @RequestMapping(value = "/is/datasource", method = RequestMethod.GET)
    public ModelAndView isDatasource(@RequestParam(value = "ISID", required = false) Integer isId) {
        ModelAndView result = new ModelAndView("jsonView");
        result.getModel().put("name", "Tcode");
        return result;
    }
}
