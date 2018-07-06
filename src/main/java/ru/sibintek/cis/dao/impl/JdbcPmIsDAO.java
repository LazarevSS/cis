package ru.sibintek.cis.dao.impl;

import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sibintek.cis.dao.PmIsDAO;
import ru.sibintek.cis.model.PmIsEntity;
import ru.sibintek.cis.model.dto.InformResIsAndJoin;
import ru.sibintek.cis.model.dto.SystemAndInformRes;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPmIsDAO implements PmIsDAO {
    @Autowired
    private DataSource dataSource;

    @Override
    public void delete(PmIsEntity psIs) {

    }

    @Override
    public PmIsEntity getById(int id) {
        String sqlQuery = "select * from pm_is where id = ?";
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                PmIsEntity pmIsEntity = new PmIsEntity();
                pmIsEntity.setId(rs.getInt(1));
                pmIsEntity.setIsNum(rs.getString(2));
                pmIsEntity.setIsName(rs.getString(3));
                pmIsEntity.setIsOwner(rs.getString(4));
                return pmIsEntity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(PmIsEntity psIs, int id) {

    }

    @Override
    public List<PmIsEntity> getByCriteria(Criterion criterion) {
        return null;
    }

    @Override
    public List<PmIsEntity> getAll() {
        return null;
    }

    @Override
    public List<InformResIsAndJoin> getInformResIsAndJoins(Integer isId) {
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
                "      a.is_id, a.is_name, a.ir_id, a.ir_name, join_type, b.ir_id jir_id, b.ir_name jir_name, b.is_id jis_id, b.is_name jis_name\n" +
                "      from a, j, a b\n" +
                "      where a.ir_id=j.air_id(+) and a.fa_id=j.afa_id(+)\n" +
                "        and b.ir_id(+)=j.bir_id and b.fa_id(+)=j.bfa_id \n" +
                "        and  a.is_id=?";
        Connection conn = null;
        List<InformResIsAndJoin> informResAndJoins = new ArrayList<>();
        try {
            conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, isId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                InformResIsAndJoin informResAndJoin = new InformResIsAndJoin();
                informResAndJoin.setIsId(rs.getInt(1));
                informResAndJoin.setIsName(rs.getString(2));
                informResAndJoin.setIrId(rs.getInt(3));
                informResAndJoin.setIrName(rs.getString(4));
                informResAndJoin.setJoinType(rs.getInt(5));
                informResAndJoin.setjIrId(rs.getInt(6));
                informResAndJoin.setjIrName(rs.getString(7));
                informResAndJoin.setjIsId(rs.getInt(8));
                informResAndJoin.setjIsName(rs.getString(9));
                informResAndJoins.add(informResAndJoin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return informResAndJoins;
    }

    @Override
    public List<SystemAndInformRes> getSystemsAndInformRes() {
        String sqlQuery = "select s.id sid, s.is_name, r.id rid, r.ir_name\n" +
                "from PM_IS s, PM_IR r\n" +
                "where r.fk_is_id=s.id";
        Connection conn = null;
        List<SystemAndInformRes> systemsAndInformRes = new ArrayList<>();
        try {
            conn = dataSource.getConnection();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sqlQuery);
            while (rs.next()) {
                SystemAndInformRes systemAndInformRes = new SystemAndInformRes();
                systemAndInformRes.setSid(rs.getInt(1));
                systemAndInformRes.setIsName(rs.getString(2));
                systemAndInformRes.setRid(rs.getInt(3));
                systemAndInformRes.setIrName(rs.getString(4));
                systemsAndInformRes.add(systemAndInformRes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return systemsAndInformRes;
    }
}
