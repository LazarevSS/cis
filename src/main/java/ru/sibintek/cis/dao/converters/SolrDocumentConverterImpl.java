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
        commonModel.setId(Integer.valueOf(document.getFieldValue("id").toString()));
        commonModel.setIs_name_short(document.getFieldValue("is_name_short").toString());
        commonModel.setIs_name_short_str(document.getFieldValue("is_name_short_str").toString());
        commonModel.setIr_code(document.getFieldValue("ir_code").toString());
        commonModel.setIr_name(document.getFieldValue("ir_name").toString());
        commonModel.setObject_type(document.getFieldValue("object_type").toString());
        commonModel.setIs_owner(document.getFieldValue("is_owner").toString());
        commonModel.setObj_num_path((List<String>)document.getFieldValue("obj_num_path"));
        commonModel.setIr_owner(document.getFieldValue("ir_owner").toString());
        commonModel.setIr_num(document.getFieldValue("ir_num").toString());
        commonModel.setIs_type(document.getFieldValue("is_type").toString());
        commonModel.setName(document.getFieldValue("name").toString());
        commonModel.setIs_name(document.getFieldValue("is_name").toString());
        commonModel.setIs_num(Integer.valueOf(document.getFieldValue("is_num").toString()));
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
