package com.moheqionglin.classLoader.hotreload;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-12-02 17:34
 */
public class BizService {

    private BizDao bizDao = null;
    private int i = 0;

    public BizService(){
        bizDao = new BizDao();
    }

    public void doSth(){
        System.out.println( i ++ + " invoke biz service. change ");
        bizDao.doDao();
    }
}