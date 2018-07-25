package ru.sibintek.cis.dao.converters;

import org.apache.solr.common.SolrDocument;
import org.springframework.stereotype.Component;
import ru.sibintek.cis.model.FunctionModel;
import ru.sibintek.cis.model.IrModel;
import ru.sibintek.cis.model.IsModel;
import ru.sibintek.cis.model.dto.IsVisualizingData;

import java.util.ArrayList;
import java.util.List;

@Component
public class SolrDocumentConverterImpl implements SolrDocumentConverter {

    @Override
    public IsVisualizingData toIsVisualizingData(SolrDocument document) {
        return null;
    }

    @Override
    public List<IsVisualizingData> toIsVisualizingData(List<SolrDocument> documents) {
        return null;
    }

    @Override
    public IsModel toIsModel(SolrDocument document) {
        IsModel isModel = new IsModel();
        isModel.setId(Integer.valueOf(document.getFieldValue("id").toString()));
        isModel.setIsNum(document.getFieldValue("is_num").toString());
        isModel.setIsName(document.getFieldValue("name").toString());
        return isModel;
    }

    @Override
    public List<IsModel> toIsModel(List<SolrDocument> documents) {
        List<IsModel> isModels = new ArrayList<>();
        for (SolrDocument document : documents) {
            isModels.add(toIsModel(document));
        }
        return isModels;
    }

    @Override
    public IrModel toIrModel(SolrDocument document) {
        IrModel irModel = new IrModel();
        irModel.setId(Integer.valueOf(document.getFieldValue("id").toString()));
        irModel.setIrNum(document.getFieldValue("ir_num").toString());
        irModel.setIrName(document.getFieldValue("name").toString());
        irModel.setIsId(getId(document.getFieldValue("is_id")));
        irModel.setScenarioNum(document.getFieldValue("scenario_num").toString());
        irModel.setIrOwner(document.getFieldValue("ir_owner").toString());
        return irModel;
    }

    @Override
    public List<IrModel> toIrModel(List<SolrDocument> documents) {
        List<IrModel> irModels = new ArrayList<>();
        for (SolrDocument document : documents) {
            irModels.add(toIrModel(document));
        }
        return irModels;
    }

    @Override
    public FunctionModel toFunctionModel(SolrDocument document) {
        FunctionModel functionModel = new FunctionModel();
        functionModel.setId(Integer.valueOf(document.getFieldValue("id").toString()));
        //functionModel.setFunctionNum(document.getFieldValue("function_num").toString());
        functionModel.setFunctionName(document.getFieldValue("name").toString());
        functionModel.setIsIds((List<String>)document.getFieldValue("is_id"));
        return functionModel;
    }

    @Override
    public List<FunctionModel> toFunctionModel(List<SolrDocument> documents) {
        List<FunctionModel> functionModels = new ArrayList<>();
        for (SolrDocument document : documents) {
            functionModels.add(toFunctionModel(document));
        }
        return functionModels;
    }

    private int getId(Object list) {
        List<String> ids = (List<String>) list;
        return Integer.valueOf(ids.get(0));
    }
}
