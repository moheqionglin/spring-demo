package com.moheiqonglin.shard.multipleDatasource;

import com.moheiqonglin.shard.multipleDatasource.aop.DatasourceKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author wanli.zhou
 * @description
 * @time 07/12/2018 4:33 PM
 */
@Component
public class Dao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> queryAllUsers(@DatasourceKey String product){
        List<User> users = jdbcTemplate.query("select * from users", new UserMapper());
        System.out.println(">>>>>>>>>>>>>>>>" + users);
        return users;
    }

    public List<User> queryAllUsers(String product, String bbb){
        List<User> users = jdbcTemplate.query("select * from users", new UserMapper());
        System.out.println("|||>>>>>>>>>>>>>>>>" + users);
        return users;
    }

}

class User {
    private int id ;
    private String name;
    private boolean sex;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }
}


class UserMapper implements RowMapper<User> {

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
