package ru.sibintek.cis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.sibintek.cis.dao.utils.DataForGraphUtils;
import ru.sibintek.cis.model.dto.DataGraphIs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IsController {
    @Autowired
    private DataForGraphUtils dataForGraphUtils;

    @RequestMapping(value = "/is", method = RequestMethod.GET)
    public ModelAndView isController(@RequestParam(value = "ISID", required = false) Integer isid) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("isView");
        return modelAndView;
    }

    @RequestMapping(value = "/is/datasource", method = RequestMethod.GET)
    public ModelAndView isDatasource(@RequestParam(value = "ISID", required = false) Integer isid) {
        ModelAndView result = new ModelAndView("jsonView");
        Map<String, Object> a = new HashMap<>();
        List<DataGraphIs> dataGraphIsList = dataForGraphUtils.getDataGraphIs(isid);
        a.put("children", dataGraphIsList);
        a.put("name", "Tcode");
        result.getModel().put("name", "Tcode");
        result.getModel().put("children", dataGraphIsList);
        return result;
    }
}
