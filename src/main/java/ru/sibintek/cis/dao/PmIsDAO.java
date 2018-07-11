package ru.sibintek.cis.dao;

import ru.sibintek.cis.model.PmIsEntity;
import ru.sibintek.cis.model.dto.InformResIsAndJoin;
import ru.sibintek.cis.model.dto.SystemAndInformRes;

import java.util.List;

public interface PmIsDAO {

    void delete(PmIsEntity psIs);

    PmIsEntity getById(int id);

    void save(PmIsEntity psIs, int id);

    List<PmIsEntity> getAll();

    List<InformResIsAndJoin> getInformResIsAndJoins(Integer isId);

    List<SystemAndInformRes> getSystemsAndInformRes();
}
