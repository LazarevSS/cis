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
public class InformSystemController {
    @Autowired
    private VisualService visualService;

    @Autowired
    private CommonDao commonDao;

    @RequestMapping(value = "/is", method = RequestMethod.GET)
    public ModelAndView isController(@RequestParam(value = "ISNAME", required = false) String isName) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("isModel", commonDao.getByIsName(isName));
        modelAndView.addObject("irModels", commonDao.getAllIr());
        modelAndView.addObject("table", commonDao.getIsRelations(isName));
        modelAndView.setViewName("informSystemView");
        return modelAndView;
    }

    @RequestMapping(value = "/is/datasource", method = RequestMethod.GET)
    public ModelAndView isDatasource(@RequestParam(value = "ISNAME", required = false) String isName) {
        ModelAndView result = new ModelAndView("jsonView");
        result.getModel().put("name", "Tcode");
        result.getModel().put("children", visualService.getVisualizingDataForIs(isName));
        return result;
    }
}
