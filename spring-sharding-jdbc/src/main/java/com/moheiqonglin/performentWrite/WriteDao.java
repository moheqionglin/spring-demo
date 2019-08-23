package com.moheiqonglin.performentWrite;

import com.moheiqonglin.shard.domain.User;
import com.moheiqonglin.shard.domain.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wanli.zhou
 * @description
 * @time 10/10/2018 2:16 PM
 */
@Component
public class WriteDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void write(int rowNum){
        String sql = "insert into test_enum(name, age, sex) values(?,?,?)";
        int batchSize = 1000;

        for(int i = 0 ; i < rowNum; i+= batchSize){

            List<PerDomain> perDomains = mockData(i, batchSize);
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, perDomains.get(i).getName());
                    ps.setInt(2, perDomains.get(i).getAge());
                    ps.setString(3, perDomains.get(i).getSex());
                }

                @Override
                public int getBatchSize() {
                    return perDomains.size();
                }
            });
        }

    }

    private List<PerDomain> mockData(int index, int batchSize) {
        List<PerDomain> pds = new ArrayList<>();
        for(int i = 0 ; i < batchSize; i++){
            pds.add(new PerDomain("name-" + index + "-" + i, i, i % 2 == 0 ? "男" : "女"));
        }
        return pds;
    }


}

