package ru.sibintek.cis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.sibintek.cis.dao.PmIrDAO;
import ru.sibintek.cis.dao.PmIsDAO;


@Controller
public class RootController {
    @Autowired
    private PmIsDAO pmIsDAO;

    @Autowired
    private PmIrDAO pmIrDAO;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView root() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("systemsAndInformRes", pmIsDAO.getSystemsAndInformRes());
        modelAndView.addObject("pmIrEntities", pmIrDAO.getAll());
        modelAndView.setViewName("root");
        return modelAndView;
    }
}
