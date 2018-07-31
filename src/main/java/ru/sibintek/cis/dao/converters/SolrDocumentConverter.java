package ru.sibintek.cis.dao.converters;

import org.apache.solr.common.SolrDocument;
import ru.sibintek.cis.model.CommonModel;

import java.util.List;

public interface SolrDocumentConverter {
    CommonModel toCommonModel(SolrDocument document);

    List<CommonModel> toCommonModel(List<SolrDocument> documents);
}
