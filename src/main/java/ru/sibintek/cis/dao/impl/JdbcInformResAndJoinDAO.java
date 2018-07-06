package ru.sibintek.cis.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sibintek.cis.dao.InformResAndJoinDAO;
import ru.sibintek.cis.model.dto.InformResAndJoin;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcInformResAndJoinDAO implements InformResAndJoinDAO {
    @Autowired
    private DataSource dataSource;

    @Override
    public List<InformResAndJoin> getAll(Integer isId) {
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
        List<InformResAndJoin> informResAndJoins = new ArrayList<>();
        try {
            conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, isId);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                InformResAndJoin informResAndJoin = new InformResAndJoin();
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
}
