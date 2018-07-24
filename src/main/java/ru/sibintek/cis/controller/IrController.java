package ru.sibintek.cis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.sibintek.cis.dao.DataForGraphDAO;
import ru.sibintek.cis.dao.IrDAO;
import ru.sibintek.cis.model.dto.DataGraphDrawBubbleChart;

import java.util.List;

@Controller
public class IrController {
    @Autowired
    private DataForGraphDAO dataForGraphDAO;

    @Autowired
    @Qualifier("jdbcPmIrDAO")
    private IrDAO irDAO;

    @RequestMapping(value = "/ir", method = RequestMethod.GET)
    public ModelAndView isController(@RequestParam(value = "IRID", required = false) Integer irId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pmIrEntity", irDAO.getById(irId));
        modelAndView.addObject("funcAreaIrAndJoins", irDAO.getFuncAreaIrAndJoins(irId));
        modelAndView.addObject("pmIrEntities", irDAO.getAll());
        modelAndView.setViewName("irView");
        return modelAndView;
    }

    @RequestMapping(value = "/ir/datasource", method = RequestMethod.GET)
    public ModelAndView isDatasource(@RequestParam(value = "IRID", required = false) Integer irId) {
        ModelAndView result = new ModelAndView("jsonView");
        List<DataGraphDrawBubbleChart> dataGraphDrawBubbleChartList = dataForGraphDAO.getDataGraphIr(irId);
        result.getModel().put("name", "Tcode");
        result.getModel().put("children", dataGraphDrawBubbleChartList);
        return result;
    }
}
