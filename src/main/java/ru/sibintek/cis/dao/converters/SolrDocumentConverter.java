package ru.sibintek.cis.dao.converters;

import org.apache.solr.common.SolrDocument;
import ru.sibintek.cis.model.IrModel;
import ru.sibintek.cis.model.IsModel;

import java.util.List;

public interface SolrDocumentConverter {
    IsModel toIsEntity(SolrDocument document);

    List<IsModel> toIsEntity(List<SolrDocument> documents);

    IrModel toIrEntity(SolrDocument document);

    List<IrModel> toIrEntity(List<SolrDocument> documents);
}
