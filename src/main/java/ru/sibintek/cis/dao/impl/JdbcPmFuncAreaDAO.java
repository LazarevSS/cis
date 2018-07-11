package ru.sibintek.cis.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sibintek.cis.dao.PmFuncAreaDAO;
import ru.sibintek.cis.model.PmFuncAreaEntity;
import ru.sibintek.cis.model.dto.FunctionAndRelatedJoin;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPmFuncAreaDAO implements PmFuncAreaDAO {
    @Autowired
    private DataSource dataSource;

    @Override
    public void delete(PmFuncAreaEntity pmFuncArea) {

    }

    @Override
    public PmFuncAreaEntity getById(int id) {
        String sqlQuery = "select * from PM_FUNCAREA where id = ?";
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                PmFuncAreaEntity entity = new PmFuncAreaEntity();
                entity.setId(rs.getInt(1));
                entity.setScenarioName(rs.getString(2));
                entity.setFuncAreaNum(rs.getString(3));
                entity.setFkPmId(rs.getInt(4));
                return entity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(PmFuncAreaEntity pmFuncArea, int id) {

    }

    @Override
    public List<PmFuncAreaEntity> getAll() {
        return null;
    }

    @Override
    public List<FunctionAndRelatedJoin> getFunctionAndRelatedJoins(Integer faId) {
        String sqlQuery = "with a as (\n" +
                "      select s.id is_id, s.is_name\n" +
                "      ,r.id ir_id, r.ir_name\n" +
                "      , a.id fa_id, a.scenario_name fa_name\n" +
                "      ,fu.id fu_id, fu.function_name fu_name\n" +
                "       from  pm_is s, pm_ir r,\n" +
                "       (select distinct fa_id, ir_id, function_id from pf_function) aj, \n" +
                "       pm_funcarea a, pm_function fu\n" +
                "      where r.fk_is_id=s.id and r.id=aj.ir_id and a.id=aj.fa_id and fu.id=get_root_parent_id(aj.function_id)\n" +
                "      ), j as (\n" +
                "      select distinct\n" +
                "      fa.ir_id air_id, fa.fa_id afa_id, fa.function_id afunction_id \n" +
                "      ,fj.join_type\n" +
                "      ,fb.ir_id bir_id, fb.fa_id bfa_id, fb.function_id bfunction_id\n" +
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
                "      select distinct \n" +
                "        a.ir_id, a.fa_id, a.fa_name, a.fu_id, a.fu_name,  join_type, b.fu_id jfu_id, b.fu_name jfu_name, b.fa_id jfa_id, b.fa_name jfa_name, b.ir_id jir_id\n" +
                "      from a, j, a b\n" +
                "      where a.ir_id=j.air_id(+) and a.fa_id=j.afa_id(+)\n" +
                "        and b.ir_id(+)=j.bir_id and b.fa_id(+)=j.bfa_id\n" +
                "        and a.fu_id=get_root_parent_id(j.afunction_id(+)) and b.fu_id(+)=get_root_parent_id(j.bfunction_id)\n" +
                "        and a.fa_id=?";
        Connection conn = null;
        List<FunctionAndRelatedJoin> functionAndRelatedJoins = new ArrayList<>();
        try {
            conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, faId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                FunctionAndRelatedJoin functionAndRelatedJoin = new FunctionAndRelatedJoin();
                functionAndRelatedJoin.setIrId(rs.getInt(1));
                functionAndRelatedJoin.setFaId(rs.getInt(2));
                functionAndRelatedJoin.setFaName(rs.getString(3));
                functionAndRelatedJoin.setFuId(rs.getInt(4));
                functionAndRelatedJoin.setFuName(rs.getString(5));
                functionAndRelatedJoin.setJoinType(rs.getInt(6));
                functionAndRelatedJoin.setjFuId(rs.getInt(7));
                functionAndRelatedJoin.setjFuName(rs.getString(8));
                functionAndRelatedJoin.setjFaId(rs.getInt(9));
                functionAndRelatedJoin.setjFaName(rs.getString(10));
                functionAndRelatedJoin.setjIrId(rs.getInt(11));
                functionAndRelatedJoins.add(functionAndRelatedJoin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return functionAndRelatedJoins;
    }
}
