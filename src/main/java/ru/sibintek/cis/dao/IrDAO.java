package ru.sibintek.cis.dao;

import ru.sibintek.cis.model.IrModel;
import ru.sibintek.cis.model.dto.FuncAreaIrAndJoin;


import java.util.List;

public interface IrDAO {
    void delete(IrModel psIr);

    IrModel getById(int id);

    void save(IrModel psIr, int id);

    List<IrModel> getAll();

    List<IrModel> getByIsId(int id);

    List<FuncAreaIrAndJoin> getFuncAreaIrAndJoins(Integer irId);
}
