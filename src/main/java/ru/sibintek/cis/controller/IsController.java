package ru.sibintek.cis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.sibintek.cis.dao.DataForGraphDAO;
import ru.sibintek.cis.dao.PmIrDAO;

import ru.sibintek.cis.dao.PmIsDAO;
import ru.sibintek.cis.model.dto.DataGraphDrawBubbleChart;

import java.util.List;

@Controller
public class IsController {
    @Autowired
    private DataForGraphDAO dataForGraphDAO;

    @Autowired
    @Qualifier("jdbcPmIrDAO")
    private PmIrDAO pmIrDAO;

    @Autowired
    @Qualifier("jdbcPmIsDAO")
    private PmIsDAO pmIsDAO;

    @RequestMapping(value = "/is", method = RequestMethod.GET)
    public ModelAndView isController(@RequestParam(value = "ISID", required = false) Integer isId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pmIsEntity", pmIsDAO.getById(isId));
        modelAndView.addObject("informResAndJoins", pmIsDAO.getInformResIsAndJoins(isId));
        modelAndView.addObject("pmIrEntities", pmIrDAO.getAll());
        modelAndView.setViewName("isView");
        return modelAndView;
    }

    @RequestMapping(value = "/is/datasource", method = RequestMethod.GET)
    public ModelAndView isDatasource(@RequestParam(value = "ISID", required = false) Integer isId) {
        ModelAndView result = new ModelAndView("jsonView");
        List<DataGraphDrawBubbleChart> dataGraphDrawBubbleChartList = dataForGraphDAO.getDataGraphIs(isId);
        result.getModel().put("name", "Tcode");
        result.getModel().put("children", dataGraphDrawBubbleChartList);
        return result;
    }
}
