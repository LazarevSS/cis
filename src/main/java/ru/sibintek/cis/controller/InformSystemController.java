package ru.sibintek.cis.controller;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.sibintek.cis.dao.CommonDao;

import ru.sibintek.cis.model.CommonModel;
import ru.sibintek.cis.service.GraphService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


@Controller
public class InformSystemController {
    @Autowired
    private GraphService graphService;

    @Autowired
    private CommonDao commonDao;

    @RequestMapping(value = "/is", method = RequestMethod.GET)
    public ModelAndView isController(@RequestParam(value = "ISNAME", required = false) String isName) throws IOException, SolrServerException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("isModel", commonDao.getByIsName(isName));
        modelAndView.addObject("irModels", commonDao.getAllIr());
        modelAndView.addObject("table", /*commonDao.getIsRelations(isName)*/ commonDao.getAllIr());
        modelAndView.setViewName("informSystemView");
        return modelAndView;
    }

    @RequestMapping(value = "/is/getGraph", method = RequestMethod.GET)
    public ModelAndView isGraph(@RequestParam(value = "ISNAME", required = false) String isName) throws IOException, SolrServerException {
        ModelAndView result = new ModelAndView("jsonView");
        result.getModel().put("name", "Tcode");
        result.getModel().put("children", graphService.getGraphSystem(isName));
        return result;
    }

    @RequestMapping(value = "is/getIs", method = RequestMethod.POST)
    public ModelAndView getInformSystems() throws IOException, SolrServerException {
        ModelAndView result = new ModelAndView("jsonView");
        HashSet<String> informSystems = new HashSet<>();
        for (CommonModel informResource : commonDao.getAllIr()) {
            informSystems.add(informResource.getIsName());
        }
        result.getModel().put("elements", informSystems);
        return result;
    }

    @RequestMapping(value = "is/download", method = RequestMethod.GET)
    public void download(@RequestParam(value = "isName", required = false) String isName,
                         HttpServletResponse response) throws IOException, InvalidFormatException, SolrServerException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=MyExcel.xls");
        String urlString = "http://192.168.1.8:8983/solr/mycoll";
        HttpSolrClient client = new HttpSolrClient.Builder(urlString).build();
        SolrQuery q = new SolrQuery();
        q.add("q", "is_name:" + "БФ Управление договорами");
        QueryResponse rsp = client.query(q);
        SolrDocumentList docs = rsp.getResults();
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("MySheet");
        CreationHelper createHelper = wb.getCreationHelper();

        // Create a row and put some cells in it. Rows are 0 based.
        Row headerRow = sheet.createRow((short)0);
        /*row.createCell(1).setCellValue(docs.get(0).getFieldValue("is_name").toString());
        row.createCell(2).setCellValue( createHelper.createRichTextString("This is a string") );
        row.createCell(3).setCellValue(true);*/
        response.getOutputStream().write(((HSSFWorkbook) wb).getBytes());
        response.getOutputStream().flush();
        response.getOutputStream().close();
        wb.close();
    }
}
