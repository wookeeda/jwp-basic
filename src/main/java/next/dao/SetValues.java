package next.dao;

import next.model.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public  abstract class SetValues {
    abstract void setValues(PreparedStatement psmt) throws SQLException;
}
