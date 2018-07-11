package ru.sibintek.cis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RootController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView main(@RequestParam(value = "rowCount", required = false) Integer rowCount,
                             @RequestParam(value = "pageNumber", required = false) Integer pageNumber) {
        ModelAndView modelAndView = new ModelAndView();
        PmIrDao pmIrDao = new PmIrDao();
        PmIrPage page = new PmIrPage(pmIrDao.getAll(), rowCount, pageNumber);
        modelAndView.addObject("page", page);
        modelAndView.setViewName("root");
        return modelAndView;
    }

    public static void main(String[] args) {
        System.out.println("Hibernate tutorial");

        PmIsDao pmIsDao = new PmIsDao();
        List<PmIsEntity> list = pmIsDao.getAll();
        Map<PmIsEntity, List<PmIrEntity>> pmIrAndPmIsEntityMap = new HashMap<>();
        PmIrDao pmIrDao = new PmIrDao();
        for (PmIsEntity pmIsEntity : list) {
            pmIrAndPmIsEntityMap.put(pmIsEntity, pmIrDao.getJoinWithPmIs(pmIsEntity.getId()));
        }
        pmIrDao.getAll();
        PmFuncareaDao pmFuncareaDao = new PmFuncareaDao();
        List<PmFuncareaEntity> list3 = pmFuncareaDao.getAll();
        PmFunctionDao pmFunctionDao = new PmFunctionDao();
        List<PmFunctionEntity> list4 = pmFunctionDao.getAll();
        System.out.println("success");


    }

}
