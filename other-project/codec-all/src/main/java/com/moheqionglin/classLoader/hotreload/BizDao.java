package com.moheqionglin.classLoader.hotreload;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-02 17:35
 *
 *
 */
public class BizDao {

    private int i = 0;

    public void doDao(){
        System.out.println(i ++ + " biz dao select data from database. ad");
    }
}