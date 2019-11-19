package com.moheqionglin.dubboSingleTcp;

import java.util.HashMap;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-10-29 13:42
 */
public class RequestFutureHolder {
    public static final HashMap<Integer, CustomDubboResponseFuture> requestResultMap = new HashMap<>();

}