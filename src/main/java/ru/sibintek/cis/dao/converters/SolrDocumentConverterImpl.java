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
        commonModel.setIsNameShort(document.getFieldValue("is_name_short").toString());
        commonModel.setIsNameShortStr(document.getFieldValue("is_name_short_str").toString());
        commonModel.setIrCode(document.getFieldValue("ir_code").toString());
        commonModel.setIrName(document.getFieldValue("ir_name").toString());
        commonModel.setObjectType(document.getFieldValue("object_type").toString());
        commonModel.setIsOwner(document.getFieldValue("is_owner").toString());
        commonModel.setObjNumPath((List<String>)document.getFieldValue("obj_num_path"));
        commonModel.setIrOwner(document.getFieldValue("ir_owner").toString());
        commonModel.setIrNum(document.getFieldValue("ir_num").toString());
        commonModel.setIsType(document.getFieldValue("is_type").toString());
        commonModel.setName(document.getFieldValue("name").toString());
        commonModel.setIsName(document.getFieldValue("is_name").toString());
        commonModel.setIsNum(Integer.valueOf(document.getFieldValue("is_num").toString()));
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
