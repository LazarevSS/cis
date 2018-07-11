package ru.sibintek.cis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.sibintek.cis.dao.impl.JdbcPmIrDAO;
import ru.sibintek.cis.dao.impl.JdbcSystemAndInformResDAO;
import ru.sibintek.cis.dao.utils.DataForGraphUtils;
import ru.sibintek.cis.model.dto.DataGraphIs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class RootController {
    @Autowired
    private JdbcSystemAndInformResDAO systemAndInformResDAO;

    @Autowired
    private JdbcPmIrDAO pmIrDAO;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView root() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("systemsAndInformRes", systemAndInformResDAO.getAll());
        modelAndView.addObject("pmIrEntities", pmIrDAO.getAll());
        modelAndView.setViewName("root");
        return modelAndView;
    }

    public static void main(String[] args) {
        System.out.println("Hibernate tutorial");

    }

}
