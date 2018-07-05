package ru.sibintek.cis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.sibintek.cis.dao.PmFuncareaDao;
import ru.sibintek.cis.dao.PmFunctionDao;
import ru.sibintek.cis.dao.PmIrDao;
import ru.sibintek.cis.dao.PmIsDao;
import ru.sibintek.cis.model.PmFuncareaEntity;
import ru.sibintek.cis.model.PmFunctionEntity;
import ru.sibintek.cis.model.PmIrEntity;
import ru.sibintek.cis.model.PmIsEntity;
import ru.sibintek.cis.service.PmIrService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RootController {
    @Autowired
    private PmIrService service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView root(@RequestParam(value = "rowCount", required = false) Integer rowCount,
                             @RequestParam(value = "pageNumber", required = false) Integer pageNumber) {
        ModelAndView modelAndView = new ModelAndView();
        Page page = new Page<>(service.getAll(), rowCount, pageNumber);
        modelAndView.addObject("page", page);
        modelAndView.setViewName("root");
        return modelAndView;
    }

    public static void main(String[] args) {
        System.out.println("Hibernate tutorial");

    }

}
