package ru.sibintek.cis.controller;

import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.sibintek.cis.User;
import ru.sibintek.cis.dao.PmFuncareaDao;
import ru.sibintek.cis.dao.PmFunctionDao;
import ru.sibintek.cis.dao.PmIrDao;
import ru.sibintek.cis.dao.PmIsDao;
import ru.sibintek.cis.model.PmFuncareaEntity;
import ru.sibintek.cis.model.PmFunctionEntity;
import ru.sibintek.cis.model.PmIrEntity;
import ru.sibintek.cis.model.PmIsEntity;
import ru.sibintek.cis.utils.HibernateSessionFactory;

import java.util.List;

@Controller
public class RootController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView main() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userJSP", new User());
        modelAndView.setViewName("root");
        return modelAndView;
    }

    public static void main(String[] args) {
        System.out.println("Hibernate tutorial");
        PmIsDao pmIsDao = new PmIsDao();
        List<PmIsEntity> list = pmIsDao.getAll();
        PmIrDao pmIrDao = new PmIrDao();
        List<PmIrEntity> list2 = pmIrDao.getAll();
        PmFuncareaDao pmFuncareaDao = new PmFuncareaDao();
        List<PmFuncareaEntity> list3 = pmFuncareaDao.getAll();
        PmFunctionDao pmFunctionDao = new PmFunctionDao();
        List<PmFunctionEntity> list4 = pmFunctionDao.getAll();
        System.out.println("success");



    }

}
