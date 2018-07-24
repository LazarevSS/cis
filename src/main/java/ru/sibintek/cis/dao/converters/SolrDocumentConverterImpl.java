package ru.sibintek.cis.dao.converters;

import org.apache.solr.common.SolrDocument;
import org.springframework.stereotype.Component;
import ru.sibintek.cis.model.IrModel;
import ru.sibintek.cis.model.IsModel;

import java.util.ArrayList;
import java.util.List;

@Component
public class SolrDocumentConverterImpl implements SolrDocumentConverter {

    @Override
    public IsModel toIsEntity(SolrDocument document) {
        IsModel entity = new IsModel();
        entity.setId(Integer.valueOf(document.getFieldValue("id").toString()));
        entity.setIsNum(document.getFieldValue("is_num").toString());
        entity.setIsName(document.getFieldValue("name").toString());
        return entity;
    }

    @Override
    public List<IsModel> toIsEntity(List<SolrDocument> documents) {
        List<IsModel> entities = new ArrayList<>();
        for (SolrDocument document : documents) {
            entities.add(toIsEntity(document));
        }
        return entities;
    }

    @Override
    public IrModel toIrEntity(SolrDocument document) {
        IrModel entity = new IrModel();
        entity.setId(Integer.valueOf(document.getFieldValue("id").toString()));
        entity.setIrNum(document.getFieldValue("ir_num").toString());
        entity.setIrName(document.getFieldValue("name").toString());
        entity.setIsId(Integer.valueOf(document.getFieldValue("is_id").toString()));
        entity.setScenarioNum(document.getFieldValue("scenario_num").toString());
        entity.setIrOwner(document.getFieldValue("ir_owner").toString());
        return entity;
    }

    @Override
    public List<IrModel> toIrEntity(List<SolrDocument> documents) {
        List<IrModel> entities = new ArrayList<>();
        for (SolrDocument document : documents) {
            entities.add(toIrEntity(document));
        }
        return entities;
    }
}
