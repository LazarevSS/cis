package ru.sibintek.cis.dao;

import ru.sibintek.cis.model.IsModel;

import java.util.List;

public interface IsDAO {

    void delete(IsModel psIs);

    IsModel getById(int id);

    void save(int id);

    List<IsModel> getAll();

}
