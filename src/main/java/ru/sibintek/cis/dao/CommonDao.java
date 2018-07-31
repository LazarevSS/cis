package ru.sibintek.cis.dao;

import ru.sibintek.cis.model.CommonModel;


import java.util.List;
import java.util.Map;

public interface CommonDao {
    void delete(CommonModel psIr);

    CommonModel getById(int id);

    List<CommonModel> getByIsName(String isName);

    CommonModel getByIdWithIs(int id);

    void save(CommonModel psIr, int id);

    List<CommonModel> getAllIr();

    Map<CommonModel, List<CommonModel>> getIsRelations(String isName);

}
