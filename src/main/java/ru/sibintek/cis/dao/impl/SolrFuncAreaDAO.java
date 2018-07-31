package ru.sibintek.cis.dao.impl;

import org.springframework.stereotype.Component;
import ru.sibintek.cis.dao.FuncAreaDAO;
import ru.sibintek.cis.model.FuncAreaModel;
import ru.sibintek.cis.model.dto.DrawBubbleChartModel;

import java.util.List;


@Component
public class SolrFuncAreaDAO implements FuncAreaDAO {
    @Override
    public void delete(FuncAreaModel pmFuncarea) {

    }

    @Override
    public FuncAreaModel getById(int id) {
        return null;
    }

    @Override
    public void save(FuncAreaModel pmFuncarea, int id) {

    }

    @Override
    public List<FuncAreaModel> getAll() {
        return null;
    }

    @Override
    public List<DrawBubbleChartModel> getVisualizingData(int isId) {
        return null;
    }
}
