package ru.sibintek.cis.dao;

import ru.sibintek.cis.model.dto.DataGraphIs;

import java.util.List;

public interface DataForGraphDAO {
    public List<DataGraphIs> getDataGraphIs(Integer isid);
}
