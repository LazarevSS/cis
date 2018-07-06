package ru.sibintek.cis.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sibintek.cis.dao.DataForGraphDAO;
import ru.sibintek.cis.model.dto.DataGraphDrawBubbleChart;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcDataForGraphDAO implements DataForGraphDAO {
    @Autowired
    private DataSource dataSource;

    @Override
    public List<DataGraphDrawBubbleChart> getDataGraphIs(Integer isid){
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
        List<DataGraphDrawBubbleChart> dataGraphDrawBubbleChartList = new ArrayList<>();
        try {
            conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, isid);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                DataGraphDrawBubbleChart dataGraphDrawBubbleChart = new DataGraphDrawBubbleChart();
                dataGraphDrawBubbleChart.setHighlight(false);
                dataGraphDrawBubbleChart.setLabel(rs.getString(1));
                dataGraphDrawBubbleChart.setUrl("/ir/?IRID=" + rs.getString(2));
                dataGraphDrawBubbleChart.setValue(String.valueOf(rs.getInt(3)));
                dataGraphDrawBubbleChartList.add(dataGraphDrawBubbleChart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataGraphDrawBubbleChartList;
    }

    @Override
    public List<DataGraphDrawBubbleChart> getDataGraphIr(Integer irId) {
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
                "      fa.scenario_name fa_name, fa.id, sum(b.cnt) cnt\n" +
                "      from b, pm_funcarea fa, pm_ir r\n" +
                "      where b.fa_id=fa.id and fa.fk_pm_id=r.id\n" +
                "        and r.id=?\n" +
                "      group by fa.scenario_name , fa.id";
        Connection conn = null;
        List<DataGraphDrawBubbleChart> dataGraphDrawBubbleChartList = new ArrayList<>();
        try {
            conn = dataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(sqlQuery);
            statement.setInt(1, irId);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                DataGraphDrawBubbleChart dataGraphDrawBubbleChart = new DataGraphDrawBubbleChart();
                dataGraphDrawBubbleChart.setHighlight(false);
                dataGraphDrawBubbleChart.setLabel(rs.getString(1));
                dataGraphDrawBubbleChart.setUrl("/fa/?FAID=" + rs.getString(2));
                dataGraphDrawBubbleChart.setValue(String.valueOf(rs.getInt(3)));
                dataGraphDrawBubbleChartList.add(dataGraphDrawBubbleChart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataGraphDrawBubbleChartList;
    }
}
