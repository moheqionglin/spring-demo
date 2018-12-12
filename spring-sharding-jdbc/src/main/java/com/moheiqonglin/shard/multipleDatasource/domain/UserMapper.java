package com.moheiqonglin.shard.multipleDatasource.domain;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author wanli.zhou
 * @description
 * @time 12/12/2018 10:02 AM
 */
public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int arg1) throws SQLException {
        User user = new User();
        if (existsColumn(resultSet, "id")) {
            user.setId(resultSet.getInt("id"));
        }
        if (existsColumn(resultSet, "name")) {
            user.setName(resultSet.getString("name"));
        }
        if (existsColumn(resultSet, "sex")) {
            user.setSex(resultSet.getBoolean("sex"));
        }
        return user;
    }

    private boolean existsColumn(ResultSet rs, String column) {
        try {
            return rs.findColumn(column) > 0;
        } catch (SQLException sqlex) {
            return false;
        }
    }
}