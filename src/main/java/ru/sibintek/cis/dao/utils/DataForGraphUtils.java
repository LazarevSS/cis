package ru.sibintek.cis.dao.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sibintek.cis.model.dto.DataGraphIs;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataForGraphUtils {
    @Autowired
    private DataSource dataSource;

    public List<DataGraphIs> getDataGraphIs(Integer isid){
        String sqlQuery = "with a as (\n" +
                "      select  connect_by_root r.fk_func_id  func_id\n" +
                "      , level l\n" +
                "      ,sys_connect_by_path(fk_func_id,'/')\n" +
                "      ,connect_by_iscycle\n" +
                "      ,connect_by_isleaf leaf\n" +
                "      from pm_function_relation r\n" +
                "      connect by nocycle prior r.fk_func_id=r.fk_func_parent_id\n" +
                "      ), b as ( select f.fa_id, f.function_id, nvl(a.l,0)+1 cnt\n" +
                "       from pf_function f, a\n" +
                "      where f.function_id=a.func_id(+) and a.leaf(+)=1\n" +
                "      )\n" +
                "      select --* \n" +
                "      r.ir_name, r.id, sum(b.cnt) cnt\n" +
                "      from b, pm_funcarea fa, pm_ir r\n" +
                "      where b.fa_id=fa.id and fa.fk_pm_id=r.id\n" +
                "        and r.fk_is_id=?\n" +
                "      group by r.ir_name, r.id";
        Connection conn = null;
        List<DataGraphIs> dataGraphIsList = new ArrayList<>();
        try {
            conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, isid);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                DataGraphIs dataGraphIs = new DataGraphIs();
                dataGraphIs.setHighlight(false);
                dataGraphIs.setLabel(rs.getString(1));
                dataGraphIs.setUrl("/ir/?IRID=" + rs.getString(2));
                dataGraphIs.setValue(String.valueOf(rs.getInt(3)));
                dataGraphIsList.add(dataGraphIs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataGraphIsList;


    }

}
