package ru.sibintek.cis.dao;

import org.hibernate.criterion.Criterion;
import ru.sibintek.cis.model.PmIsEntity;
import ru.sibintek.cis.model.dto.InformResIsAndJoin;
import ru.sibintek.cis.model.dto.SystemAndInformRes;

import java.util.List;

public interface PmIsDAO {

    void delete(PmIsEntity psIs);

    PmIsEntity getById(int id);

    void save(PmIsEntity psIs, int id);

    List<PmIsEntity> getByCriteria(Criterion criterion);

    List<PmIsEntity> getAll();

    List<InformResIsAndJoin> getInformResIsAndJoins(Integer isId);

    List<SystemAndInformRes> getSystemsAndInformRes();
}
