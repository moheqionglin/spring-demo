package com.moheqionglin.dao;

import com.moheqionglin.BaseTest;
import com.moheqionglin.es.dao.EsCommonDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-08-22 17:25
 */
public class EsCommonDaoTest extends BaseTest {
    @Autowired
    private EsCommonDao esCommonDao;

    @Test
    public void insertTest() throws IOException {
        esCommonDao.insert("point", "id-1", null);
    }

}