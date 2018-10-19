package com.moheiqonglin.shard;

import com.moheiqonglin.shard.domain.User;
import com.moheiqonglin.shard.domain.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wanli.zhou
 * @description
 * @time 10/10/2018 2:16 PM
 */
@Component
public class ShardingDao {

    @Autowired
    private JdbcTemplate shardingJdbcTemplate;


    public List<User> query(){
        final String sql = "select id, name, createdTime,modifiedTime from users " +
                " where id = ? and createdTime between ? and ? order by createdTime ASC offset ? limit ?";

        return shardingJdbcTemplate.query(sql, new Object[]{1l, 1535437543113l, 1536734305793l, 0, 1000}, new UserRowMapper());
    }

}