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

import java.util.List;


@Controller
public class RootController {
    @Autowired
    private JdbcSystemAndInformResDAO systemAndInformResDAO;

    @Autowired
    private JdbcPmIrDAO pmIrDAO;

    @Autowired
    private DataForGraphUtils dataForGraphUtils;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView root() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("systemsAndInformRes", systemAndInformResDAO.getAll());
        modelAndView.addObject("pmIrEntities", pmIrDAO.getAll());
        modelAndView.setViewName("root");
        return modelAndView;
    }

    @RequestMapping(value = "/is", method = RequestMethod.GET)
    public ModelAndView isController(@RequestParam(value = "ISID", required = false) Integer isid) {
        ModelAndView modelAndView = new ModelAndView();
        /*modelAndView.addObject("systemsAndInformRes", systemAndInformResDAO.getAll());
        modelAndView.addObject("pmIrEntities", pmIrDAO.getAll());*/
        modelAndView.setViewName("isView");
        return modelAndView;
    }

    @RequestMapping(value = "/is/datasource", method = RequestMethod.GET)
    public ModelAndView isDatasource(@RequestParam(value = "ISID", required = false) Integer isid) {
        ModelAndView result = new ModelAndView("jsonView");
        List<DataGraphIs> dataGraphIsList = dataForGraphUtils.dataGraphIs(isid);
        result.getModel().put("result", dataGraphIsList);
        return result;
    }

    public static void main(String[] args) {
        System.out.println("Hibernate tutorial");

    }

}
