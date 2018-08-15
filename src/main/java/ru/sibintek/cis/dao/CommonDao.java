package ru.sibintek.cis.dao;

import ru.sibintek.cis.model.CommonModel;


import java.util.List;
import java.util.Map;

public interface CommonDao {
    void delete(CommonModel psIr);

    CommonModel getById(int id);

    CommonModel getByIsName(String isName);

    CommonModel getByIrName(String irName);

    CommonModel getByFuName(String fuName);

    void save(CommonModel psIr, int id);

    List<CommonModel> getAllIr();

    List<CommonModel> getChildrenFunctions(String fuName);

    List<CommonModel> getParentFunctions(String fuName);

    List<CommonModel> getParentIrs(String fuName);

    Map<CommonModel, List<CommonModel>> getIsRelations(String isName);

    Map<CommonModel, List<CommonModel>> getIrRelations(String irName);

}
