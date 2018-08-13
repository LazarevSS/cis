package ru.sibintek.cis.dao.impl;

import org.apache.solr.common.SolrDocument;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sibintek.cis.dao.CommonDao;
import ru.sibintek.cis.dao.converters.SolrDocumentConverter;
import ru.sibintek.cis.model.CommonModel;
import ru.sibintek.cis.util.SparkConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SolrCommonDaoImpl implements CommonDao {
    private JavaRDD<SolrDocument> resultsRDD = SparkConnector.getInstance().getResultRDD();

    @Autowired
    private SolrDocumentConverter converter;

    @Override
    public void delete(CommonModel psIr) {

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
        Function<SolrDocument, Boolean> filter = doc -> (doc.getFieldValue("is_name").equals(finalIsName));
        JavaRDD<SolrDocument> filterDocuments = resultsRDD.filter(filter);
        return converter.toCommonModel(filterDocuments.collect().get(0));
    }

    @Override
    public CommonModel getByIrName(String irName) {
        Function<SolrDocument, Boolean> filter = doc -> (doc.getFieldValue("name").equals(irName));
        JavaRDD<SolrDocument> filterDocuments = resultsRDD.filter(filter);
        return converter.toCommonModel(filterDocuments.collect().get(0));
    }

    @Override
    public CommonModel getByFuName(String fuName) {
        Function<SolrDocument, Boolean> filter = doc -> (doc.getFieldValue("name").equals(fuName));
        JavaRDD<SolrDocument> filterDocuments = resultsRDD.filter(filter);
        return converter.toCommonModel(filterDocuments.collect().get(0));
    }

    @Override
    public void save(CommonModel psIr, int id) {

    }

    @Override
    public List<CommonModel> getAllIr() {
        Function<SolrDocument, Boolean> filter = doc -> (doc.getFieldValue("object_type").equals("ir"));
        JavaRDD<SolrDocument> irEntities = resultsRDD.filter(filter);
        return converter.toCommonModel(irEntities.collect());
    }

    @Override
    public List<CommonModel> getChildrenFunctions(String fuName) {
        CommonModel function = getByFuName(fuName);
        Function<SolrDocument, Boolean> filter = doc -> {
            List<String> fieldValues = (List<String>) doc.getFieldValue("obj_num_path");
            if (fieldValues == null) return false;
            String functionPath = function.getObj_num_path().get(0).replaceFirst("^[0-9]+\\.[0-9]+\\.", "");
            Pattern p = Pattern.compile("^[0-9]+\\.[0-9]+\\." + functionPath + ".+");
            Matcher m = p.matcher(fieldValues.get(0));
            return m.matches();
        };
        JavaRDD<SolrDocument> filterDocuments = resultsRDD.filter(filter);
        return converter.toCommonModel(filterDocuments.collect());
    }

    @Override
    public List<CommonModel> getParentIrs(String fuName) {
        CommonModel function = getByFuName(fuName);
        Function<SolrDocument, Boolean> filter = doc -> {
            List<String> fieldValues = (List<String>) doc.getFieldValue("obj_num_path");
            if (fieldValues == null) return false;
            String functionPath = function.getObj_num_path().get(0).replaceFirst("^[0-9]+\\.[0-9]+\\.", "");
            Pattern p = Pattern.compile("^[0-9]+\\.[0-9]+\\." + functionPath);
            Matcher m = p.matcher(fieldValues.get(0));
            return m.matches();
        };
        JavaRDD<SolrDocument> filterDocuments = resultsRDD.filter(filter);
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
        Function<SolrDocument, Boolean> irChildrenFilter = doc -> (doc.getFieldValue("object_type").equals("ir") && doc.getFieldValue("is_name").equals(isName));
        JavaRDD<SolrDocument> irEntities = resultsRDD.filter(irChildrenFilter);
        List<CommonModel> commonModels = converter.toCommonModel(irEntities.collect());
        Map<CommonModel, List<CommonModel>> docAndJoinDoc = new HashMap<>();
        for (CommonModel commonModel : commonModels) {
            Function<SolrDocument, Boolean> functionFilter = doc -> (doc.getFieldValue("object_type").equals("fu") && doc.getFieldValue("ir_num").equals(commonModel.getIr_num()));
            JavaRDD<SolrDocument> functionEntities = resultsRDD.filter(functionFilter);
            List<CommonModel> functions = converter.toCommonModel(functionEntities.collect());
            if (functions.isEmpty()) continue;
            String path = functions.get(0).getObj_num_path().get(0);
            String functionPath = path.replaceFirst(commonModel.getObj_num_path().get(0) + ".", "");
            Function<SolrDocument, Boolean> joinFilter = doc -> {
                Pattern p = Pattern.compile("^[0-9]+\\.[0-9]+\\." + functionPath + "$");
                if (doc.getFieldValue("obj_num_path") != null) {
                    List<String> fieldValues = (List<String>) doc.getFieldValue("obj_num_path");
                    Matcher m = p.matcher(fieldValues.get(0));
                    return m.matches();
                }
                return false;
            };
            JavaRDD<SolrDocument> joinSolrDocument = resultsRDD.filter(joinFilter);
            List<CommonModel> joinCommonModel = converter.toCommonModel(joinSolrDocument.collect());
            docAndJoinDoc.put(commonModel, joinCommonModel);
        }
        return docAndJoinDoc;
    }

    @Override
    public Map<CommonModel, List<CommonModel>> getIrRelations(String irName) {
        CommonModel commonModel = getByIrName(irName);
        Map<CommonModel, List<CommonModel>> docAndJoinDoc = new HashMap<>();
        Function<SolrDocument, Boolean> functionFilter = doc -> (doc.getFieldValue("object_type").equals("fu") && doc.getFieldValue("ir_num").equals(commonModel.getIr_num()));
        JavaRDD<SolrDocument> functionEntities = resultsRDD.filter(functionFilter);
        List<CommonModel> functions = converter.toCommonModel(functionEntities.collect());
        if (functions.isEmpty()) return docAndJoinDoc;
        String path = functions.get(0).getObj_num_path().get(0);
        String functionPath = path.replaceFirst(commonModel.getObj_num_path().get(0) + ".", "");
        Function<SolrDocument, Boolean> joinFilter = doc -> {
            Pattern p = Pattern.compile("^[0-9]+\\.[0-9]+\\." + functionPath + "$");
            if (doc.getFieldValue("obj_num_path") != null) {
                List<String> fieldValues = (List<String>) doc.getFieldValue("obj_num_path");
                Matcher m = p.matcher(fieldValues.get(0));
                return m.matches();
            }
            return false;
        };
        JavaRDD<SolrDocument> joinSolrDocument = resultsRDD.filter(joinFilter);
        List<CommonModel> joinCommonModel = converter.toCommonModel(joinSolrDocument.collect());
        docAndJoinDoc.put(commonModel, joinCommonModel);
        return docAndJoinDoc;
    }
}
