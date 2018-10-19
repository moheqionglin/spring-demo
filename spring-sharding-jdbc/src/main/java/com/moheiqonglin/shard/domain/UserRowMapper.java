package com.moheiqonglin.shard.domain;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User lp = new User();
        if(existsColumn(resultSet, "id")){
            lp.setId(resultSet.getLong("id"));
        }
        if(existsColumn(resultSet, "name")){
            lp.setName(resultSet.getString("name"));
        }

        if(existsColumn(resultSet, "createdTime")){
            lp.setCreatedTime(resultSet.getTimestamp("createdTime"));
        }
        if(existsColumn(resultSet, "modifiedTime")){
            lp.setModifiedTime(resultSet.getTimestamp("modifiedTime"));
        }

        return lp;
    }

    private boolean existsColumn(ResultSet rs, String column) {
        try {
            return rs.findColumn(column) > 0;
        } catch (SQLException sqlex) {
            return false;
        }
    }
}
