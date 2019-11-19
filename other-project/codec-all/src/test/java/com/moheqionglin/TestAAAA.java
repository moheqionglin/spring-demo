package com.moheqionglin;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wanli.zhou
 * @description
 * @time 2019-11-04 10:33
 */
public class TestAAAA {

    static String ipRangeFiles = "file:///Users/wanli.zhou/Desktop/ip_list.txt";

    public static void main(String[] args) throws URISyntaxException, IOException {

      List<String> ipstr = Arrays.asList(ipListsTr.split("\n"));

        List<String[]> ipRanges = Files.lines(Paths.get(new URI(ipRangeFiles)))
                .filter(s -> s.contains("-- 禁用IP为连续网段"))
                .map(s -> s.replace("-- 禁用IP为连续网段，从", ""))
                .map(s -> s.replace("起始，至", ","))
                .map(s -> s.replace("结束", ","))
                .map(s -> {
                    String[] is = s.split(",");
                    String[] iss = new String[2];
                    iss[0] = is[0];
                    iss[1] = is[1];
                    return iss;
                })
                .collect(Collectors.toList());

        Map<String[], List<String>> result = new HashMap<>();

        ipstr.stream().forEach(ips -> {
            ipRanges.stream().forEach(ipsRanges -> {
                if(ipToLong(ips) <= ipToLong(ipsRanges[1]) && ipToLong(ips) >= ipToLong(ipsRanges[0])){
                    if(!result.containsKey(ipsRanges)){
                        result.put(ipsRanges, new ArrayList<>());
                    }
                    result.get(ipsRanges).add(ips);
                }
            });
        });

        result.entrySet().stream().map(e -> e.getKey()[0] + " -- " + e.getKey()[1] + ", " + e.getValue().size()).forEach(System.out::println);

        System.out.println("---");
        result.entrySet().stream().map(e -> e.getKey()[0] + " -- " + e.getKey()[1] + ", " + e.getValue()).forEach(System.out::println);

        System.out.println("不在里面的ip");
        ipstr.stream()
                .filter(i -> result.entrySet().stream().filter(e -> e.getValue().contains(i)).count() == 0)
                .forEach(System.out::println);

    }

    public static long ipToLong(String strIp) {
        String[] s = strIp.split("\\.");
        long ip = (Long.parseLong(s[0]) << 24)
                + (Long.parseLong(s[1]) << 16) +
                (Long.parseLong(s[2]) << 8)
                + (Long.parseLong(s[3]));
        return ip;
    }

    static String ipListsTr = "11.11.11.11\n" +
            "22.22.22.22";
}