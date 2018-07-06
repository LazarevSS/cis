package ru.sibintek.cis.dao;

import ru.sibintek.cis.model.dto.DataGraphDrawBubbleChart;

import java.util.List;

public interface DataForGraphDAO {
    List<DataGraphDrawBubbleChart> getDataGraphIs(Integer isId);

    List<DataGraphDrawBubbleChart> getDataGraphIr(Integer irId);
}
