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
import ru.sibintek.cis.model.dto.Link;
import ru.sibintek.cis.model.dto.Node;
import ru.sibintek.cis.service.GraphService;
import ru.sibintek.cis.util.SolrParserUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class FunctionController {
    @Autowired
    private GraphService graphService;

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private SolrParserUtils solrParserUtils;

    @RequestMapping(value = "/fu", method = RequestMethod.GET)
    public ModelAndView fuController(@RequestParam(value = "FUNAME", required = false) String fuName) throws IOException, SolrServerException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("fuModel", commonDao.getByFuName(fuName));
        modelAndView.addObject("childrenFunction", commonDao.getChildrenFunctions(fuName, "fu"));
        modelAndView.addObject("irModels", commonDao.getAllIr());
        modelAndView.addObject("irsParent", commonDao.getParentIrs(fuName));
        modelAndView.setViewName("functionView");
        return modelAndView;
    }

    @RequestMapping(value = "/fu/getGraph", method = RequestMethod.GET)
    public ModelAndView fuDatasource(@RequestParam(value = "FUNAME", required = false) String fuName,
                                     @RequestParam(value = "FUID", required = false) String fuId) throws IOException, SolrServerException {
        ModelAndView result = new ModelAndView("jsonView");
        Map<List<Link>, List<Node>> graph = graphService.getGraphFunction(fuId, fuName);
        graph.forEach((links, nodes) -> {
            result.getModel().put("nodes", nodes);
            result.getModel().put("links", links);
        });
        return result;
    }

    @RequestMapping(value = "fu/getFu", method = RequestMethod.POST)
    public ModelAndView getInformResources() throws IOException, SolrServerException {
        ModelAndView result = new ModelAndView("jsonView");
        List<String> functions = new ArrayList<>();
        for (CommonModel function : commonDao.getAllFunctions()) {
            functions.add(function.getName());
        }
        result.getModel().put("elements", functions);
        return result;
    }

    @RequestMapping(value = "fu/download", method = RequestMethod.GET)
    public void download(@RequestParam(value = "fuName", required = false) String fuName,
                         HttpServletResponse response) throws IOException, SolrServerException {
        response.setContentType(solrParserUtils.getSupportsFormats().get("xls") + ";charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=DocumentFU.xls");
        HSSFWorkbook wb = solrParserUtils.createWorkBook(fuName);
        response.getOutputStream().write(wb.getBytes());
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    @RequestMapping(value = "fu/upload", method = RequestMethod.POST)
    public void uploadFile(@RequestParam("file") MultipartFile file) throws IOException, InvalidFormatException {
        List<Map<String, Object>> excel = solrParserUtils.parseExcel(file.getInputStream());
        List<SolrInputDocument> solrDocuments = solrParserUtils.createSolrDocuments(excel);
        commonDao.saveFromExcel(solrDocuments);
    }
}
