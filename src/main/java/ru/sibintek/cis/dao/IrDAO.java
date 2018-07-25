package ru.sibintek.cis.dao;

import ru.sibintek.cis.model.FunctionModel;
import ru.sibintek.cis.model.IrModel;
import ru.sibintek.cis.model.dto.IrVisualizingData;


import java.util.List;

public interface IrDAO {
    void delete(IrModel psIr);

    IrModel getById(int id);

    IrModel getByIdWithIs(int id);

    void save(IrModel psIr, int id);

    List<IrModel> getAll();

    List<IrModel> getByIsId(int id);

    List<FunctionModel> getJoinFunctions(int irId);

    List<IrModel> getRelationsIr(int irId);

    List<IrVisualizingData> getVisualizingData(int isId);

}
