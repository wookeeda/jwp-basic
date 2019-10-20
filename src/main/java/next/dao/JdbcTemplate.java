package next.dao;

import core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class JdbcTemplate {

    private Connection con;
    private PreparedStatement psmt = null;

    JdbcTemplate() {
        this.con = ConnectionManager.getConnection();
    }

    void executeUpdate() throws SQLException {
        try {
            psmt.executeUpdate();
        } finally {
            if (psmt != null) {
                psmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    ResultSet executeQuery() throws SQLException {
        try {
            ResultSet rs =
                    psmt.executeQuery();

            return rs;
        } finally {
            if (psmt != null) {
                psmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }


    void createQuery(CreateQuery query) throws SQLException {
        String sql = query.createQuery();
        psmt = con.prepareStatement(sql);
    }

    void setValues(SetValues setter) throws SQLException {
        setter.setValues(psmt);
    }
}
