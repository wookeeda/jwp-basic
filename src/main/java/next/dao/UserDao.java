package next.dao;

import next.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public void insert(User user) throws SQLException {
        JdbcTemplate template = new JdbcTemplate();

        CreateQuery query = new CreateQuery() {
            @Override
            String createQuery() {
                return "INSERT INTO USERS VALUES (?, ?, ?, ?)";
            }
        };

        SetValues setter = new SetValues() {
            @Override
            void setValues(PreparedStatement psmt) throws SQLException {
                psmt.setString(1, user.getUserId());
                psmt.setString(2, user.getPassword());
                psmt.setString(3, user.getName());
                psmt.setString(4, user.getEmail());
            }
        };

        template.createQuery(query);
        template.setValues(setter);

        template.executeUpdate();
    }

    public void update(User user) throws SQLException {
        JdbcTemplate template = new JdbcTemplate();

        CreateQuery query = new CreateQuery() {
            @Override
            String createQuery() {
                return "update USERS set password = ?, name = ?, email = ? where userId = ?";
            }
        };

        SetValues setter = new SetValues() {
            @Override
            void setValues(PreparedStatement psmt) throws SQLException {
                psmt.setString(1, user.getPassword());
                psmt.setString(2, user.getName());
                psmt.setString(3, user.getEmail());
                psmt.setString(4, user.getUserId());
            }
        };

        template.createQuery(query);
        template.setValues(setter);

        template.executeUpdate();
    }

    public List<User> findAll() throws SQLException {
        JdbcTemplate template = new JdbcTemplate();

        CreateQuery query = new CreateQuery() {
            @Override
            String createQuery() {
                return "select * from users";
            }
        };

        template.createQuery(query);

        ResultSet rs = null;
        List<User> list = new ArrayList<>();

        try {
            rs = template.executeQuery();
            while (rs.next()) {
                list.add(
                        new User(
                                rs.getString("userId"),
                                rs.getString("password"),
                                rs.getString("name"),
                                rs.getString("email")
                        )
                );
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

        return list;

    }

    public User findByUserId(String userId) throws SQLException {
        JdbcTemplate template = new JdbcTemplate();

        CreateQuery query = new CreateQuery() {
            @Override
            String createQuery() {
                return "SELECT userId, password, name, email FROM USERS WHERE userid=?";
            }
        };

        SetValues setter = new SetValues() {
            @Override
            void setValues(PreparedStatement psmt) throws SQLException {
                psmt.setString(1, userId);
            }
        };

        template.createQuery(query);
        template.setValues(setter);

        ResultSet rs = null;
        User user = null;

        try {
            rs = template.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
        return user;
    }
}
