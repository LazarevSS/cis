package ru.sibintek.cis.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sibintek.cis.dao.PmFunctionDAO;
import ru.sibintek.cis.model.PmFunctionEntity;
import ru.sibintek.cis.model.dto.FunctionWithStructure;
import ru.sibintek.cis.model.dto.FunctionInOtherFuncAreas;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPmFunctionDAO implements PmFunctionDAO {
    @Autowired
    private DataSource dataSource;

    @Override
    public PmFunctionEntity getById(int id) {
        String sqlQuery = "select * from PM_FUNCTION where id = ?";
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                PmFunctionEntity entity = new PmFunctionEntity();
                entity.setId(rs.getInt(1));
                entity.setFunctionNum(rs.getString(2));
                entity.setFunctionName(rs.getString(3));
                return entity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<FunctionWithStructure> getFunctionStructure(Integer fuId) {
        String sqlQuery = "with a as (\n" +
                "        select \n" +
                "        rownum rn,\n" +
                "        j.id,\n" +
                "        lpad(' ',level*3)||j.function_name  fu_name \n" +
                "        , connect_by_isleaf isl\n" +
                "        ,level fu_lev\n" +
                "        from \n" +
                "        (select f.id, fr.fk_func_parent_id, f.function_name  from pm_function f, pm_function_relation fr\n" +
                "        where f.id=fr.fk_func_id(+)) j\n" +
                "          connect by prior id=fk_func_parent_id\n" +
                "          start with id=?\n" +
                "      )  , jf as (\n" +
                "            select distinct\n" +
                "            fa.ir_id air_id, fa.fa_id afa_id, fa.function_id afunction_id\n" +
                "            ,fj.join_type\n" +
                "            ,fb.ir_id bir_id, fb.fa_id bfa_id, fb.function_id bfunction_id\n" +
                "            from\n" +
                "            PF_FUNCTION fa,\n" +
                "            PF_FUNCTION fb,\n" +
                "              (select pf_id_a, pf_id_b, join_type from\n" +
                "              pf_function_join\n" +
                "              union\n" +
                "              select pf_id_b, pf_id_a, join_type from\n" +
                "              pf_function_join ) fj\n" +
                "            where fa.id=fj.pf_id_a and fb.id=fj.pf_id_b\n" +
                "            )\n" +
                "      select distinct a.*\n" +
                "       from a, jf, pm_funcarea pa, pm_ir pi\n" +
                "      where a.id=jf.afunction_id(+) and jf.bfa_id=pa.id(+) and jf.bir_id=pi.id(+)\n" +
                "      order by a.rn";
        Connection conn = null;
        List<FunctionWithStructure> functionStructures = new ArrayList<>();
        try {
            conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, fuId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                FunctionWithStructure functionWithStructure = new FunctionWithStructure();
                functionWithStructure.setId(rs.getInt(2));
                functionWithStructure.setName(rs.getString(3));
                functionWithStructure.setLevel(rs.getInt(5));
                functionStructures.add(functionWithStructure);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return functionStructures;
    }

    @Override
    public List<FunctionInOtherFuncAreas> getFunctionsInOtherFuncAreas(Integer fuId, Integer faId, Integer irId) {
        String sqlQuery = "select s.id is_id, s.is_name\n" +
                "      ,r.id ir_id, r.ir_name\n" +
                "      , a.id fa_id, a.scenario_name fa_name\n" +
                "       from  pm_is s, pm_ir r,\n" +
                "       (select distinct fa_id, ir_id, function_id from pf_function) aj, \n" +
                "       pm_funcarea a, pm_function fu\n" +
                "      where r.fk_is_id=s.id and r.id=aj.ir_id and a.id=aj.fa_id and fu.id=aj.function_id\n" +
                "        and aj.function_id=?\n" +
                "        and not (a.id=? and r.id=?)";
        Connection conn = null;
        List<FunctionInOtherFuncAreas> functionsInOtherFuncAreas = new ArrayList<>();
        try {
            conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, fuId);
            statement.setInt(2, faId);
            statement.setInt(3, irId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                FunctionInOtherFuncAreas functionInOtherFuncAreas = new FunctionInOtherFuncAreas();
                functionInOtherFuncAreas.setIsId(rs.getInt(1));
                functionInOtherFuncAreas.setIsName(rs.getString(2));
                functionInOtherFuncAreas.setIrId(rs.getInt(3));
                functionInOtherFuncAreas.setIrName(rs.getString(4));
                functionInOtherFuncAreas.setFaId(rs.getInt(5));
                functionInOtherFuncAreas.setFaName(rs.getString(6));
                functionsInOtherFuncAreas.add(functionInOtherFuncAreas);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return functionsInOtherFuncAreas;
    }
}
