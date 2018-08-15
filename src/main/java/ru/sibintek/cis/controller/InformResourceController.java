package ru.sibintek.cis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.sibintek.cis.dao.CommonDao;
import ru.sibintek.cis.util.VisualService;


@Controller
public class InformResourceController {
    @Autowired
    private VisualService visualService;

    @Autowired
    private CommonDao commonDao; 

    @RequestMapping(value = "/ir", method = RequestMethod.GET)
    public ModelAndView irController(@RequestParam(value = "IRNAME", required = false) String irName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("irModel", commonDao.getByIrName(irName));
        modelAndView.addObject("irModels", commonDao.getAllIr());
        modelAndView.addObject("table", commonDao.getIrRelations(irName));
        modelAndView.setViewName("informResourceView");
        return modelAndView;
    }

    @RequestMapping(value = "/ir/datasource", method = RequestMethod.GET)
    public ModelAndView isDatasource(@RequestParam(value = "IRNAME", required = false) String irName) {
        ModelAndView result = new ModelAndView("jsonView");
        result.getModel().put("name", irName);
        result.getModel().put("children", visualService.getVisualizingDataForIr(irName));
        return result;
    }
}
