package next.dao;

import next.model.User;

import java.util.List;

public class UserDao {
    public void insert(User user) {
        JdbcTemplate template = new JdbcTemplate();
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        PreparedStatementSetter setter = psmt -> {
            psmt.setString(1, user.getUserId());
            psmt.setString(2, user.getPassword());
            psmt.setString(3, user.getName());
            psmt.setString(4, user.getEmail());
            return psmt;
        };

        template.update(sql, setter);
    }

    public void update(User user) {
        JdbcTemplate template = new JdbcTemplate();
        String sql = "update USERS set password = ?, name = ?, email = ? where userId = ?";
        PreparedStatementSetter setter = psmt -> {
            psmt.setString(1, user.getPassword());
            psmt.setString(2, user.getName());
            psmt.setString(3, user.getEmail());
            psmt.setString(4, user.getUserId());
            return psmt;
        };

        template.update(sql, setter);
    }

    public List<User> findAll() {
        JdbcTemplate template = new JdbcTemplate();
        String sql = "select * from users";
        RowMapper mapper = rs -> new User(rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email"));

        return template.queryForList(sql, mapper);
    }

    public User findByUserId(String userId) {
        JdbcTemplate template = new JdbcTemplate();
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        PreparedStatementSetter setter = psmt -> {
            psmt.setString(1, userId);
            return psmt;
        };
        RowMapper mapper = rs -> new User(rs.getString("userId"),
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("email"));
        return (User) template.query(sql, mapper, setter);
    }
}
