package ru.sibintek.cis.dao.converters;

import org.apache.solr.common.SolrDocument;
import org.springframework.stereotype.Component;
import ru.sibintek.cis.model.CommonModel;

import java.util.ArrayList;
import java.util.List;

@Component
public class SolrDocumentConverterImpl implements SolrDocumentConverter {

    @Override
    public CommonModel toCommonModel(SolrDocument document) {
        CommonModel commonModel = new CommonModel();
        commonModel.setId(document.getFieldValue("id") == null ? "" : document.getFieldValue("id").toString());
        commonModel.setIsNameShort(document.getFieldValue("is_name_short") == null ? "" : document.getFieldValue("is_name_short").toString());
        commonModel.setIsNameShortStr(document.getFieldValue("is_name_short_str") == null ? "" : document.getFieldValue("is_name_short_str").toString());
        commonModel.setIrCode(document.getFieldValue("ir_code") == null ? "": document.getFieldValue("ir_code").toString());
        commonModel.setIrName(document.getFieldValue("ir_name") == null ? "" : document.getFieldValue("ir_name").toString());
        commonModel.setObjectType(document.getFieldValue("object_type") == null ? "" : document.getFieldValue("object_type").toString());
        commonModel.setIsOwner(document.getFieldValue("is_owner") == null ? "" : document.getFieldValue("is_owner").toString());
        commonModel.setObjNumPath(document.getFieldValue("obj_num_path") == null ? new ArrayList<>() : (List<String>)document.getFieldValue("obj_num_path"));
        commonModel.setIrOwner(document.getFieldValue("ir_owner") == null ? "" : document.getFieldValue("ir_owner").toString());
        commonModel.setIrNum(document.getFieldValue("ir_num") == null ? "" : document.getFieldValue("ir_num").toString());
        commonModel.setIsType(document.getFieldValue("is_type") == null ? "" : document.getFieldValue("is_type").toString());
        commonModel.setName(document.getFieldValue("name") == null ? "" : document.getFieldValue("name").toString());
        commonModel.setIsName(document.getFieldValue("is_name") == null ? "" : document.getFieldValue("is_name").toString());
        commonModel.setIsNum(document.getFieldValue("is_num") == null ? null : Integer.valueOf(document.getFieldValue("is_num").toString()));
        commonModel.setRelation_id(document.getFieldValue("reference_id") == null ? new ArrayList<>() : (List<Integer>) document.getFieldValue("reference_id"));
        return commonModel;
    }

    @Override
    public List<CommonModel> toCommonModel(List<SolrDocument> documents) {
        List<CommonModel> commonModels = new ArrayList<>();
        for (SolrDocument document : documents) {
            commonModels.add(toCommonModel(document));
        }
        return commonModels;
    }
}
