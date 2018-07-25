package ru.sibintek.cis.dao;

import ru.sibintek.cis.model.FunctionModel;
import ru.sibintek.cis.model.IsModel;
import ru.sibintek.cis.model.dto.IsVisualizingData;

import java.util.List;

public interface IsDAO {

    void delete(IsModel psIs);

    IsModel getById(int id);

    IsModel getByIdWithIr(int id);

    void save(int id);

    List<FunctionModel> getJoinFunctions(int isId);

    List<IsModel> getAll();

    List<IsModel> getRelationsIs(int isId);

    List<IsVisualizingData> getVisualizingData();
}
