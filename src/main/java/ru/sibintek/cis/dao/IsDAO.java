package ru.sibintek.cis.dao;

import ru.sibintek.cis.model.PmIrEntity;
import ru.sibintek.cis.model.PmIsEntity;
import ru.sibintek.cis.model.dto.InformResIsAndJoin;
import ru.sibintek.cis.model.dto.SystemAndInformRes;

import java.util.List;
import java.util.Map;

public interface IsDAO {

    void delete(PmIsEntity psIs);

    PmIsEntity getById(int id);

    void save(int id);

    List<PmIsEntity> getAll();

    List<InformResIsAndJoin> getInformResIsAndJoins(Integer isId);

    Map<PmIsEntity, List<PmIrEntity>> getSystemsAndInformRes();
}
