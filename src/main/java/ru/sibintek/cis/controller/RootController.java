package ru.sibintek.cis.controller;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.sibintek.cis.dao.CommonDao;
import ru.sibintek.cis.service.GraphService;
import ru.sibintek.cis.util.SolrParserUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


@Controller
public class RootController {

    @Autowired
    private GraphService graphService;

    @Autowired
    private CommonDao commonDao;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView root() throws IOException, SolrServerException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("table", commonDao.getAllIr());
        modelAndView.setViewName("root");
        return modelAndView;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addIs(@RequestParam("file") MultipartFile file) throws IOException, InvalidFormatException {
        List<Map<String, Object>> excel = SolrParserUtils.parseExcel(file.getInputStream());
        List<SolrInputDocument> solrDocuments = SolrParserUtils.createSolrDocuments(excel);
        commonDao.saveFromExcel(solrDocuments);
    }

    @RequestMapping(value = "/getGraph", method = RequestMethod.GET)
    public ModelAndView rootGraph() throws IOException, SolrServerException {
        ModelAndView result = new ModelAndView("jsonView");
        result.getModel().put("content", graphService.getGraphSystems());
        result.getModel().put("sortOrder", "value-desc");
        return result;
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(@RequestParam("fileName") String fileName,
                         HttpServletResponse response) throws IOException, InvalidFormatException, SolrServerException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=MyExcel.xls");
        String urlString = "http://192.168.1.8:8983/solr/mycoll";
        HttpSolrClient client = new HttpSolrClient.Builder(urlString).build();
        SolrQuery q = new SolrQuery();
        q.add("q", "name:" + fileName);
        QueryResponse rsp = client.query(q);
        SolrDocumentList docs = rsp.getResults();
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("MySheet");
        CreationHelper createHelper = wb.getCreationHelper();

        // Create a row and put some cells in it. Rows are 0 based.
        Row row = sheet.createRow((short)0);
        Cell cell = row.createCell(0);
        cell.setCellValue(1);
        row.createCell(1).setCellValue(1.2);
        row.createCell(2).setCellValue( createHelper.createRichTextString("This is a string") );
        row.createCell(3).setCellValue(true);
        response.getOutputStream().write(((HSSFWorkbook) wb).getBytes());
        response.getOutputStream().flush();
        response.getOutputStream().close();
        wb.close();
    }
}
