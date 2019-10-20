package next.dao;

import core.exception.DataAccessException;
import core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class JdbcTemplate<T> {

    void update(String sql, PreparedStatementSetter setter) {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement psmt = con.prepareStatement(sql)
        ) {
            setter.values(psmt);
            psmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    void update(String sql, Object... params) {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement psmt = con.prepareStatement(sql)
        ) {
            PreparedStatementSetter setter = createPreparedStatementSetter(params);
            setter.values(psmt);
            psmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    private PreparedStatementSetter createPreparedStatementSetter(Object[] params) {
        return new PreparedStatementSetter() {
            @Override
            public PreparedStatement values(PreparedStatement psmt) throws SQLException {
                psmt.setString(1, String.valueOf(params[0]));
                psmt.setString(2, String.valueOf(params[1]));
                psmt.setString(3, String.valueOf(params[2]));
                psmt.setString(4, String.valueOf(params[3]));
                return psmt;
            }
        };
    }

    List<T> queryForList(String sql, RowMapper<T> mapper) {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement psmt = con.prepareStatement(sql);
        ) {
            List<T> list;
            try (ResultSet rs = psmt.executeQuery()) {
                list = new ArrayList<>();
                while (rs.next()) {
                    list.add(mapper.mapRow(rs));
                }
            }
            return list;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    T query(String sql, RowMapper<T> mapper, PreparedStatementSetter setter) {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement psmt = con.prepareStatement(sql)
        ) {
            setter.values(psmt);
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    return mapper.mapRow(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    T query(String sql, RowMapper<T> mapper, Object... params) {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement psmt = con.prepareStatement(sql);
        ) {
            PreparedStatementSetter setter = createPreparedStatementSetter(params);
            setter.values(psmt);
            try (ResultSet rs = psmt.executeQuery()) {
                if (rs.next()) {
                    return mapper.mapRow(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
}
