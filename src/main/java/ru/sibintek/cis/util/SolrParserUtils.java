package ru.sibintek.cis.util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class SolrParserUtils {
    static HashMap<String,String> supportsFormats;
    static {
        supportsFormats = new HashMap<>();
        supportsFormats.put("xml", "application/xml");
        supportsFormats.put("csv", "text/csv");
        supportsFormats.put("json", "application/json");
        supportsFormats.put("jsonl", "application/json");
        supportsFormats.put("pdf", "application/pdf");
        supportsFormats.put("rtf", "text/rtf");
        supportsFormats.put("html", "text/html");
        supportsFormats.put("htm", "text/html");
        supportsFormats.put("doc", "application/msword");
        supportsFormats.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        supportsFormats.put("ppt", "application/vnd.ms-powerpoint");
        supportsFormats.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        supportsFormats.put("xls", "application/vnd.ms-excel");
        supportsFormats.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        supportsFormats.put("odt", "application/vnd.oasis.opendocument.text");
        supportsFormats.put("ott", "application/vnd.oasis.opendocument.text");
        supportsFormats.put("odp", "application/vnd.oasis.opendocument.presentation");
        supportsFormats.put("otp", "application/vnd.oasis.opendocument.presentation");
        supportsFormats.put("ods", "application/vnd.oasis.opendocument.spreadsheet");
        supportsFormats.put("ots", "application/vnd.oasis.opendocument.spreadsheet");
        supportsFormats.put("txt", "text/plain");
        supportsFormats.put("log", "text/plain");
    }

    public static boolean saveDocument(HttpSolrClient client, SolrInputDocument document) {
        try {
            client.add(document);
            client.commit();
            return true;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void saveDocuments(final HttpSolrClient client, List<SolrInputDocument> documents) {
        documents.forEach(document -> saveDocument(client, document));
    }

    public static List<SolrInputDocument> createSolrDocuments(List<Map<String, Object>> excelDocuments) {
        List<SolrInputDocument> documents = new ArrayList<>();
        for (Map<String, Object> excelDocument : excelDocuments) {
            SolrInputDocument document = new SolrInputDocument();
            excelDocument.forEach(document::addField);
            documents.add(document);
        }
        return documents;
    }

    public static List<Map<String, Object>> parseExcel(InputStream inputStream) throws IOException, InvalidFormatException {
        Workbook wb = WorkbookFactory.create(inputStream);
        List<Map<String, Object>> documents = new ArrayList<>();
        for (Sheet sheet : wb) {
            int firstRowNum = sheet.getFirstRowNum();
            Row headerRow = sheet.getRow(firstRowNum);
            List<String> headers = new ArrayList<>();
            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                headers.add(headerRow.getCell(i).toString());
            }
            for (int i = firstRowNum + 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                Map<String, Object> document = new HashMap<>();
                for (int j = 0; j < headerRow.getLastCellNum(); j++) {
                    /*UUID uuid = UUID.randomUUID();
                    document.put("id", uuid.toString());*/
                    String header = headers.get(j);
                    Object value = getObjectWithTypeFromCell(row.getCell(j));
                    /*if (header instanceof String) {
                        header += "_s";
                    }*/
                    document.put(header, value);
                }
                documents.add(document);
            }
        }
        return documents;
    }

    private static Object getObjectWithTypeFromCell(Cell cell) {
        if (cell == null) {
            return null;
        }
        Object value = null;
        switch (cell.getCellType()) {
            case 0:
                value = cell.getNumericCellValue();
                break;
            case 1:
                value = cell.getStringCellValue();
                break;
            case 2:
                value = cell.getNumericCellValue();
                break;
            case 4:
                value = cell.getBooleanCellValue();
                break;
            case 5:
                value = cell.getErrorCellValue();
                break;
        }
        return value;
    }

}
