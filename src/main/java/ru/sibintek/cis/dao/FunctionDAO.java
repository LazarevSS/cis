package ru.sibintek.cis.dao;

import ru.sibintek.cis.model.FunctionModel;
import ru.sibintek.cis.model.dto.FunctionWithStructure;
import ru.sibintek.cis.model.dto.FunctionInOtherFuncAreas;

import java.util.List;

public interface FunctionDAO {

    FunctionModel getById(int id);

    List<FunctionWithStructure> getFunctionStructure(Integer fuId);

    List<FunctionInOtherFuncAreas> getFunctionsInOtherFuncAreas(Integer fuId, Integer faId, Integer irId);
}
