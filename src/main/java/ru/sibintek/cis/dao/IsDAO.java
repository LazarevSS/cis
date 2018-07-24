package ru.sibintek.cis.dao;

import ru.sibintek.cis.model.IrModel;
import ru.sibintek.cis.model.IsModel;
import ru.sibintek.cis.model.dto.InformResIsAndJoin;
import ru.sibintek.cis.model.dto.SystemAndInformRes;

import java.util.List;
import java.util.Map;

public interface IsDAO {

    void delete(IsModel psIs);

    IsModel getById(int id);

    void save(int id);

    List<IsModel> getAll();

    List<InformResIsAndJoin> getInformResIsAndJoins(Integer isId);

    Map<IsModel, List<IrModel>> getSystemsAndInformRes();
}
