package ru.sibintek.cis.dao.impl;

import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sibintek.cis.dao.PmIrDao;
import ru.sibintek.cis.model.PmIrEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPmIrDAO implements PmIrDao {
    @Autowired
    private DataSource dataSource;

    @Override
    public void delete(PmIrEntity psIr) {

    }

    @Override
    public PmIrEntity getById(Long id) {
        return null;
    }

    @Override
    public void save(PmIrEntity psIr, Long id) {

    }

    @Override
    public List<PmIrEntity> getByCriteria(Criterion criterion) {
        return null;
    }

    @Override
    public List<PmIrEntity> getAll() {
        String sqlQuery = "select * from pm_ir";
        Connection conn = null;
        List<PmIrEntity> pmIrEntities = new ArrayList<>();
        try {
            conn = dataSource.getConnection();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sqlQuery);
            while(rs.next()) {
                PmIrEntity entity = new PmIrEntity();
                entity.setId(rs.getInt(1));
                entity.setScenarioNum(rs.getString(2));
                entity.setScenarioType(rs.getString(3));
                entity.setIrNum(rs.getString(4));
                entity.setIrName(rs.getString(5));
                entity.setIrOwner(rs.getString(6));
                entity.setInstantion(rs.getString(7));
                entity.setSoftwareVersion(rs.getString(8));
                entity.setFkIsId(rs.getInt(9));
                pmIrEntities.add(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pmIrEntities;
    }
}
