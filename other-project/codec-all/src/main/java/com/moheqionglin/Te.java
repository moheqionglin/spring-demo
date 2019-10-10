package com.moheqionglin;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-08-07 15:23
 */
public class Te {
     static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        SortedMap<Integer, String> sortedMap = new TreeMap();
        sortedMap.put(1, "v1");
        sortedMap.put(2, "v1");
        sortedMap.put(3, "v1");
        sortedMap.put(4, "v1");
        sortedMap.put(5, "v1");
        sortedMap.put(6, "v1");
        System.out.println(sortedMap.headMap(3));
    }



}