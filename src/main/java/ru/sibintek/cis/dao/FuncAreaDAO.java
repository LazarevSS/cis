package ru.sibintek.cis.dao;

import ru.sibintek.cis.model.FuncAreaModel;

import java.util.List;

public interface FuncAreaDAO {
    void delete(FuncAreaModel pmFuncarea);

    FuncAreaModel getById(int id);

    void save(FuncAreaModel pmFuncarea, int id);

    List<FuncAreaModel> getAll();
}
