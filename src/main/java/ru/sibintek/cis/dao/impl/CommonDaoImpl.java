package ru.sibintek.cis.dao.impl;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sibintek.cis.dao.CommonDao;
import ru.sibintek.cis.dao.converters.SolrDocumentConverter;
import ru.sibintek.cis.model.CommonModel;
import ru.sibintek.cis.util.SolrConnector;
import ru.sibintek.cis.util.SparkConnector;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CommonDaoImpl implements CommonDao {
    private SolrConnector solrConnector = new SolrConnector();

    @Autowired
    private SolrDocumentConverter converter;

    @Override
    public void delete(CommonModel psIr) {

    }

    @Override
    public SolrInputDocument save(String name, String type) throws IOException, SolrServerException {
        if (type == null || type.isEmpty()) {
            return null;
        }
        HttpSolrClient client = solrConnector.getClient();
        SolrInputDocument document = new SolrInputDocument();
        switch (type) {
            case "is":
                document.addField("is_name_short", name);
                document.addField("name", name);
                document.addField("object_type", type);
                document.addField("is_owner", "Рай С.П.");
                document.addField("obj_num_path", Arrays.asList("20"));
                document.addField("is_num", 13);
                document.addField("is_name", name);
                break;
            case "ir":
                document.addField("ir_code", "TBD");
                document.addField("ir_name", name);
                document.addField("object_type", type);
                document.addField("obj_num_path", Arrays.asList("20.1"));
                document.addField("ir_num", "20.1");
                document.addField("name", name);
                break;
            case "fu":
                document.addField("name", name);
                document.addField("object_type", type);
                document.addField("obj_num_path", Arrays.asList("11"));
                break;
        }
        client.add(document);
        client.commit();
        return document;
    }

    @Override
    public SolrInputDocument addRelation(String name, String type, String joinName, String joinType) throws IOException, SolrServerException {
        return null;
    }

    @Override
    public CommonModel getById(int id) {
        return null;
    }

    @Override
    public CommonModel getByIsName(String isName) {
        if (isName.equals("БФ Управление договорами")) {
            isName = "БФ Управление договорами\n";
        }
        String finalIsName = isName;
        Function<SolrDocument, Boolean> filter = doc -> {
            Object is_Name = doc.getFieldValue("is_name");
            if (is_Name == null) return false;
            return is_Name.toString().equals(finalIsName);
        };
        JavaRDD<SolrDocument> filterDocuments = SparkConnector.getInstance().getResultRDD().filter(filter);
        return converter.toCommonModel(filterDocuments.collect().get(0));
    }

    @Override
    public CommonModel getByIrName(String irName) {
        Function<SolrDocument, Boolean> filter = doc -> (doc.getFieldValue("name").equals(irName));
        JavaRDD<SolrDocument> filterDocuments = SparkConnector.getInstance().getResultRDD().filter(filter);
        return converter.toCommonModel(filterDocuments.collect().get(0));
    }

    @Override
    public CommonModel getByFuName(String fuName) {
        Function<SolrDocument, Boolean> filter = doc -> (doc.getFieldValue("name").equals(fuName));
        JavaRDD<SolrDocument> filterDocuments = SparkConnector.getInstance().getResultRDD().filter(filter);
        return converter.toCommonModel(filterDocuments.collect().get(0));
    }

    @Override
    public List<CommonModel> getAllIr() {
        Function<SolrDocument, Boolean> filter = doc -> {
            Object objectType = doc.getFieldValue("object_type");
            if (objectType == null) {
                return false;
            }
            return objectType.toString().equals("ir");
        };
        JavaRDD<SolrDocument> irEntities = SparkConnector.getInstance().getResultRDD().filter(filter);
        return converter.toCommonModel(irEntities.collect());
    }

    @Override
    public List<CommonModel> getAllFunctions() {
        Function<SolrDocument, Boolean> filter = doc -> {
            Object objectType = doc.getFieldValue("object_type");
            if (objectType == null) {
                return false;
            }
            return objectType.toString().equals("fu");
        };
        JavaRDD<SolrDocument> irEntities = SparkConnector.getInstance().getResultRDD().filter(filter);
        return converter.toCommonModel(irEntities.collect());
    }

    @Override
    public List<CommonModel> getChildrenFunctions(String fuName) {
        CommonModel function = getByFuName(fuName);
        Function<SolrDocument, Boolean> filter = doc -> {
            List<String> fieldValues = (List<String>) doc.getFieldValue("obj_num_path");
            if (fieldValues == null) return false;
            String functionPath = function.getObjNumPath().get(0).replaceFirst("^[0-9]+\\.[0-9]+\\.", "");
            Pattern p = Pattern.compile("^[0-9]+\\.[0-9]+\\." + functionPath + ".+");
            Matcher m = p.matcher(fieldValues.get(0));
            return m.matches();
        };
        JavaRDD<SolrDocument> filterDocuments = SparkConnector.getInstance().getResultRDD().filter(filter);
        return converter.toCommonModel(filterDocuments.collect());
    }

    @Override
    public List<CommonModel> getParentFunctions(String fuName) {
        CommonModel function = getByFuName(fuName);
        List<CommonModel> parentFunctions = new ArrayList<>();
        String irPath = function.getIrName();
        String functionPath = function.getObjNumPath().get(0);
        while (functionPath.contains(".")) {
            functionPath = functionPath.replaceFirst("\\.[0-9]+$", "");
            String finalFunctionPath = functionPath;
            Function<SolrDocument, Boolean> filter = doc -> {
                List<String> fieldValues = (List<String>) doc.getFieldValue("obj_num_path");
                if (fieldValues == null) return false;
                String path = fieldValues.get(0);
                String functionParentPath = irPath + "." + finalFunctionPath;
                return path.equals(functionParentPath);
            };
            JavaRDD<SolrDocument> filterDocuments = SparkConnector.getInstance().getResultRDD().filter(filter);
            parentFunctions.addAll(converter.toCommonModel(filterDocuments.collect()));
        }
        return parentFunctions;
    }

    @Override
    public List<CommonModel> getParentIrs(String fuName) {
        CommonModel function = getByFuName(fuName);
        Function<SolrDocument, Boolean> filter = doc -> {
            List<String> fieldValues = (List<String>) doc.getFieldValue("obj_num_path");
            if (fieldValues == null) return false;
            String functionPath = function.getObjNumPath().get(0).replaceFirst("^[0-9]+\\.[0-9]+\\.", "");
            Pattern p = Pattern.compile("^[0-9]+\\.[0-9]+\\." + functionPath);
            Matcher m = p.matcher(fieldValues.get(0));
            return m.matches();
        };
        JavaRDD<SolrDocument> filterDocuments = SparkConnector.getInstance().getResultRDD().filter(filter);
        return converter.toCommonModel(filterDocuments.collect());
    }

    public static void main(String[] args) {
        String function = "2.1.1.2.1";
        String replaceChild = function.replaceFirst("^[0-9]+\\.[0-9]+\\.", "");
        String root = "4.6.1.2.1";
        Pattern p = Pattern.compile("^[0-9]+\\.[0-9]+\\." + replaceChild);
        Matcher m = p.matcher(root);
        function.startsWith(root + ".");
        System.out.println();
    }

    @Override
    public Map<CommonModel, List<CommonModel>> getIsRelations(String isName) {
        Function<SolrDocument, Boolean> irChildrenFilter = doc -> {
            Object object_type = doc.getFieldValue("object_type");
            Object is_name = doc.getFieldValue("is_name");
            if (object_type == null || is_name == null) return false;
            return doc.getFieldValue("object_type").equals("ir") && doc.getFieldValue("is_name").equals(isName);
        };
        JavaRDD<SolrDocument> irEntities = SparkConnector.getInstance().getResultRDD().filter(irChildrenFilter);
        List<CommonModel> commonModels = converter.toCommonModel(irEntities.collect());
        Map<CommonModel, List<CommonModel>> docAndJoinDoc = new HashMap<>();
        for (CommonModel commonModel : commonModels) {
            Function<SolrDocument, Boolean> functionFilter = doc -> (doc.getFieldValue("object_type").equals("fu") && doc.getFieldValue("ir_num").equals(commonModel.getIrNum()));
            JavaRDD<SolrDocument> functionEntities = SparkConnector.getInstance().getResultRDD().filter(functionFilter);
            List<CommonModel> functions = converter.toCommonModel(functionEntities.collect());
            if (functions.isEmpty()) continue;
            if (functions.get(0).getObjNumPath() == null) continue;
            String path = functions.get(0).getObjNumPath().get(0);
            String functionPath = path.replaceFirst(commonModel.getObjNumPath().get(0) + ".", "");
            Function<SolrDocument, Boolean> joinFilter = doc -> {
                Pattern p = Pattern.compile("^[0-9]+\\.[0-9]+\\." + functionPath + "$");
                if (doc.getFieldValue("obj_num_path") != null) {
                    List<String> fieldValues = (List<String>) doc.getFieldValue("obj_num_path");
                    Matcher m = p.matcher(fieldValues.get(0));
                    return m.matches();
                }
                return false;
            };
            JavaRDD<SolrDocument> joinSolrDocument = SparkConnector.getInstance().getResultRDD().filter(joinFilter);
            List<CommonModel> joinCommonModel = converter.toCommonModel(joinSolrDocument.collect());
            docAndJoinDoc.put(commonModel, joinCommonModel);
        }
        return docAndJoinDoc;
    }

    @Override
    public Map<CommonModel, List<CommonModel>> getIrRelations(String irName) {
        CommonModel commonModel = getByIrName(irName);
        Map<CommonModel, List<CommonModel>> docAndJoinDoc = new HashMap<>();
        Function<SolrDocument, Boolean> functionFilter = doc -> (doc.getFieldValue("object_type").equals("fu") && doc.getFieldValue("ir_num").equals(commonModel.getIrNum()));
        JavaRDD<SolrDocument> functionEntities = SparkConnector.getInstance().getResultRDD().filter(functionFilter);
        List<CommonModel> functions = converter.toCommonModel(functionEntities.collect());
        if (functions.isEmpty()) return docAndJoinDoc;
        String path = functions.get(0).getObjNumPath().get(0);
        String functionPath = path.replaceFirst(commonModel.getObjNumPath().get(0) + ".", "");
        Function<SolrDocument, Boolean> joinFilter = doc -> {
            Pattern p = Pattern.compile("^[0-9]+\\.[0-9]+\\." + functionPath + "$");
            if (doc.getFieldValue("obj_num_path") != null) {
                List<String> fieldValues = (List<String>) doc.getFieldValue("obj_num_path");
                Matcher m = p.matcher(fieldValues.get(0));
                return m.matches();
            }
            return false;
        };
        JavaRDD<SolrDocument> joinSolrDocument = SparkConnector.getInstance().getResultRDD().filter(joinFilter);
        List<CommonModel> joinCommonModel = converter.toCommonModel(joinSolrDocument.collect());
        docAndJoinDoc.put(commonModel, joinCommonModel);
        return docAndJoinDoc;
    }
}
