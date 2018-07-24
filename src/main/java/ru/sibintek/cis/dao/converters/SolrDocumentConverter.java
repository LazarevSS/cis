package ru.sibintek.cis.dao.converters;

import org.apache.solr.common.SolrDocument;
import ru.sibintek.cis.model.PmIrEntity;
import ru.sibintek.cis.model.PmIsEntity;

import java.util.List;

public interface SolrDocumentConverter {
    PmIsEntity toIsEntity(SolrDocument document);

    List<PmIsEntity> toIsEntity(List<SolrDocument> documents);

    PmIrEntity toIrEntity(SolrDocument document);

    List<PmIrEntity> toIrEntity(List<SolrDocument> documents);
}
