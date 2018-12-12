package com.moheiqonglin.shard.multipleDatasource.domain;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author wanli.zhou
 * @description
 * @time 12/12/2018 11:40 AM
 */
public class SplitDbConfigBeanRowMapper implements RowMapper<SplitDbConfigBean> {

    @Override
    public SplitDbConfigBean mapRow(ResultSet resultSet, int arg1) throws SQLException {
        SplitDbConfigBean sdcb = new SplitDbConfigBean();
        if (existsColumn(resultSet, "id")) {
            sdcb.setId(resultSet.getInt("id"));
        }
        if (existsColumn(resultSet, "shard_key")) {
            sdcb.setShardKey(resultSet.getString("shard_key"));
        }
        if (existsColumn(resultSet, "url")) {
            sdcb.setUrl(resultSet.getString("url"));
        }
        if (existsColumn(resultSet, "userName")) {
            sdcb.setUserName(resultSet.getString("userName"));
        }
        if (existsColumn(resultSet, "password")) {
            sdcb.setPassword(resultSet.getString("password"));
        }
        return sdcb;
    }

    private boolean existsColumn(ResultSet rs, String column) {
        try {
            return rs.findColumn(column) > 0;
        } catch (SQLException sqlex) {
            return false;
        }
    }
}