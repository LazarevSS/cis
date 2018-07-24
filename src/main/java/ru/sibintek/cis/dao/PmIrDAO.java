package ru.sibintek.cis.dao;

import ru.sibintek.cis.model.PmIrEntity;
import ru.sibintek.cis.model.dto.FuncAreaIrAndJoin;


import java.util.List;

public interface PmIrDAO {
    void delete(PmIrEntity psIr);

    PmIrEntity getById(int id);

    void save(PmIrEntity psIr, int id);

    List<PmIrEntity> getAll();

    List<PmIrEntity> getByIsId(int id);

    List<FuncAreaIrAndJoin> getFuncAreaIrAndJoins(Integer irId);
}
