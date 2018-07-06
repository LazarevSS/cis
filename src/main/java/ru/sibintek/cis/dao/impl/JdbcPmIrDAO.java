package ru.sibintek.cis.dao.impl;

import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sibintek.cis.dao.PmIrDAO;
import ru.sibintek.cis.model.PmIrEntity;
import ru.sibintek.cis.model.dto.FuncAreaIrAndJoin;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPmIrDAO implements PmIrDAO {
    @Autowired
    private DataSource dataSource;

    @Override
    public void delete(PmIrEntity psIr) {

    }

    @Override
    public PmIrEntity getById(int id) {
        String sqlQuery = "select * from pm_ir where id = ?";
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
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
                return entity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(PmIrEntity psIr, int id) {

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

    @Override
    public List<FuncAreaIrAndJoin> getFuncAreaIrAndJoins(Integer irId) {
        String sqlQuery = "with a as (\n" +
                "      select s.id is_id, s.is_name\n" +
                "      ,r.id ir_id, r.ir_name\n" +
                "      , a.id fa_id, a.scenario_name fa_name\n" +
                "       from  pm_is s, pm_ir r,\n" +
                "       (select distinct fa_id, ir_id from pf_function) aj, \n" +
                "       pm_funcarea a\n" +
                "      where r.fk_is_id=s.id and r.id=aj.ir_id and a.id=aj.fa_id\n" +
                "      ), j as (\n" +
                "      select distinct\n" +
                "      fa.ir_id air_id, fa.fa_id afa_id--, fa.function_id afunction_id \n" +
                "      ,fj.join_type\n" +
                "      ,fb.ir_id bir_id, fb.fa_id bfa_id--, fb.function_id bfunction_id\n" +
                "      from \n" +
                "      PF_FUNCTION fa, \n" +
                "      PF_FUNCTION fb,\n" +
                "        (select pf_id_a, pf_id_b, join_type from\n" +
                "        pf_function_join \n" +
                "        union\n" +
                "        select pf_id_b, pf_id_a, join_type from\n" +
                "        pf_function_join ) fj\n" +
                "      where fa.id=fj.pf_id_a and fb.id=fj.pf_id_b \n" +
                "      )\n" +
                "      select distinct  --*\n" +
                "       a.ir_id, a.ir_name, a.fa_id, a.fa_name,  join_type, b.fa_id jfa_id, b.fa_name jfa_name, b.ir_id jir_id, b.ir_name jir_name, b.is_id jis_id, b.is_name jis_name\n" +
                "      from a, j, a b\n" +
                "      where a.ir_id=j.air_id(+) and a.fa_id=j.afa_id(+)\n" +
                "        and b.ir_id(+)=j.bir_id and b.fa_id(+)=j.bfa_id\n" +
                "        and a.ir_id=?";
        Connection conn = null;
        List<FuncAreaIrAndJoin> funcAreaIrAndJoins = new ArrayList<>();
        try {
            conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, irId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                FuncAreaIrAndJoin funcAreaIrAndJoin = new FuncAreaIrAndJoin();
                funcAreaIrAndJoin.setIrId(rs.getInt(1));
                funcAreaIrAndJoin.setIrName(rs.getString(2));
                funcAreaIrAndJoin.setFaId(rs.getInt(3));
                funcAreaIrAndJoin.setFaName(rs.getString(4));
                funcAreaIrAndJoin.setJoinType(rs.getInt(5));
                funcAreaIrAndJoin.setjFaId(rs.getInt(6));
                funcAreaIrAndJoin.setjFaName(rs.getString(7));
                funcAreaIrAndJoin.setjIrId(rs.getInt(8));
                funcAreaIrAndJoin.setjIrName(rs.getString(9));
                funcAreaIrAndJoin.setjIsId(rs.getInt(10));
                funcAreaIrAndJoin.setjIsName(rs.getString(11));
                funcAreaIrAndJoins.add(funcAreaIrAndJoin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return funcAreaIrAndJoins;
    }
}
