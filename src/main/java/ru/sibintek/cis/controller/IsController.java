package ru.sibintek.cis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.sibintek.cis.dao.DataForGraphDAO;
import ru.sibintek.cis.dao.InformResAndJoinDAO;
import ru.sibintek.cis.dao.PmIrDAO;

import ru.sibintek.cis.model.dto.DataGraphIs;

import java.util.List;

@Controller
public class IsController {
    @Autowired
    private DataForGraphDAO dataForGraphDAO;

    @Autowired
    private PmIrDAO pmIrDAO;

    @Autowired
    private InformResAndJoinDAO jdbcInformResAndJoinDAO;

    @RequestMapping(value = "/is", method = RequestMethod.GET)
    public ModelAndView isController(@RequestParam(value = "ISID", required = false) Integer isid) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("informResAndJoins", jdbcInformResAndJoinDAO.getAll(isid));
        modelAndView.addObject("pmIrEntities", pmIrDAO.getAll());
        modelAndView.setViewName("isView");
        return modelAndView;
    }

    @RequestMapping(value = "/is/datasource", method = RequestMethod.GET)
    public ModelAndView isDatasource(@RequestParam(value = "ISID", required = false) Integer isid) {
        ModelAndView result = new ModelAndView("jsonView");
        List<DataGraphIs> dataGraphIsList = dataForGraphDAO.getDataGraphIs(isid);
        result.getModel().put("name", "Tcode");
        result.getModel().put("children", dataGraphIsList);
        return result;
    }
}
