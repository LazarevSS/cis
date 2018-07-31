package ru.sibintek.cis.dao.converters;

import org.apache.solr.common.SolrDocument;
import ru.sibintek.cis.model.CommonModel;
import ru.sibintek.cis.model.FunctionModel;
import ru.sibintek.cis.model.IsModel;

import java.util.List;

public interface SolrDocumentConverter {

    IsModel toIsModel(SolrDocument document);

    List<IsModel> toIsModel(List<SolrDocument> documents);

    CommonModel toCommonModel(SolrDocument document);

    List<CommonModel> toCommonModel(List<SolrDocument> documents);

    FunctionModel toFunctionModel(SolrDocument document);

    List<FunctionModel> toFunctionModel(List<SolrDocument> documents);
}
