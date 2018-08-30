package ru.sibintek.cis.controller;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.sibintek.cis.dao.CommonDao;
import ru.sibintek.cis.model.CommonModel;
import ru.sibintek.cis.service.GraphService;
import ru.sibintek.cis.util.SolrParserUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


@Controller
public class InformResourceController {
    @Autowired
    private GraphService graphService;

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private SolrParserUtils solrParserUtils;

    @RequestMapping(value = "/ir", method = RequestMethod.GET)
    public ModelAndView irController(@RequestParam(value = "IRNAME", required = false) String irName) throws IOException, SolrServerException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("irModel", commonDao.getByIrName(irName));
        modelAndView.addObject("irModels", commonDao.getAllIr());
        modelAndView.addObject("table", commonDao.getAllIr());
        modelAndView.setViewName("informResourceView");
        return modelAndView;
    }

    @RequestMapping(value = "/ir/getGraph", method = RequestMethod.GET)
    public ModelAndView irGraph(@RequestParam(value = "IRNAME", required = false) String irName) throws IOException, SolrServerException {
        ModelAndView result = new ModelAndView("jsonView");
        result.getModel().put("name", irName);
        result.getModel().put("children", graphService.getGraphInformResource(irName));
        return result;
    }

    @RequestMapping(value = "ir/getIr", method = RequestMethod.POST)
    public ModelAndView getInformResources() throws IOException, SolrServerException {
        ModelAndView result = new ModelAndView("jsonView");
        HashSet<String> informResources = new HashSet<>();
        for (CommonModel informResource : commonDao.getAllIr()) {
            informResources.add(informResource.getName());
        }
        result.getModel().put("elements", informResources);
        return result;
    }

    @RequestMapping(value = "ir/download", method = RequestMethod.GET)
    public void download(@RequestParam(value = "irName", required = false) String irName,
                         HttpServletResponse response) throws IOException, SolrServerException {
        response.setContentType(solrParserUtils.getSupportsFormats().get("xls") + ";charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=DocumentIR.xls");
        HSSFWorkbook wb = solrParserUtils.createWorkBook(irName);
        response.getOutputStream().write(wb.getBytes());
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    @RequestMapping(value = "ir/upload", method = RequestMethod.POST)
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException, InvalidFormatException {
        List<Map<String, Object>> excel = solrParserUtils.parseExcel(file.getInputStream());
        List<SolrInputDocument> solrDocuments = solrParserUtils.createSolrDocuments(excel);
        commonDao.saveFromExcel(solrDocuments);
    }
}
