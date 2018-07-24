package ru.sibintek.cis.dao;

import ru.sibintek.cis.model.IrModel;


import java.util.List;

public interface IrDAO {
    void delete(IrModel psIr);

    IrModel getById(int id);

    void save(IrModel psIr, int id);

    List<IrModel> getAll();

    List<IrModel> getByIsId(int id);

}
