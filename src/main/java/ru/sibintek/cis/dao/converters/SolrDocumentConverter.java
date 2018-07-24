package ru.sibintek.cis.dao.converters;

import org.apache.solr.common.SolrDocument;
import org.springframework.stereotype.Component;
import ru.sibintek.cis.model.PmIrEntity;
import ru.sibintek.cis.model.PmIsEntity;

import java.util.ArrayList;
import java.util.List;

@Component
public class SolrDocumentConverter {
    public PmIsEntity toIsEntity(SolrDocument document) {
        PmIsEntity entity = new PmIsEntity();
        entity.setId(Integer.valueOf(document.getFieldValue("id").toString()));
        entity.setIsNum(document.getFieldValue("is_num").toString());
        entity.setIsName(document.getFieldValue("name").toString());
        return entity;
    }

    public List<PmIsEntity> toIsEntity(List<SolrDocument> documents) {
        List<PmIsEntity> entities = new ArrayList<>();
        for (SolrDocument document : documents) {
            entities.add(toIsEntity(document));
        }
        return entities;
    }

    public PmIrEntity toIrEntity(SolrDocument document) {
        PmIrEntity entity = new PmIrEntity();
        entity.setId(Integer.valueOf(document.getFieldValue("id").toString()));
        entity.setIrNum(document.getFieldValue("ir_num").toString());
        entity.setIrName(document.getFieldValue("name").toString());
        entity.setIsId(Integer.valueOf(document.getFieldValue("is_id").toString()));
        entity.setScenarioNum(document.getFieldValue("scenario_num").toString());
        entity.setIrOwner(document.getFieldValue("ir_owner").toString());
        return entity;
    }

    public List<PmIrEntity> toIrEntity(List<SolrDocument> documents) {
        List<PmIrEntity> entities = new ArrayList<>();
        for (SolrDocument document : documents) {
            entities.add(toIrEntity(document));
        }
        return entities;
    }
}
