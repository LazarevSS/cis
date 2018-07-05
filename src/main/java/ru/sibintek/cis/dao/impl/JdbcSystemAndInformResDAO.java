package ru.sibintek.cis.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sibintek.cis.dao.SystemAndInformResDAO;
import ru.sibintek.cis.model.dto.SystemAndInformRes;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcSystemAndInformResDAO implements SystemAndInformResDAO {
    @Autowired
    private DataSource dataSource;

    @Override
    public List<SystemAndInformRes> getAll() {
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
