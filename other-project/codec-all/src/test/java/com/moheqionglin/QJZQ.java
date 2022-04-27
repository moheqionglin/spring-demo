package com.moheqionglin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QJZQ {
    final static String path= "/Users/zhouwanli/Desktop/QJZQ";

    public static void main(String[] args) throws IOException {
        List<String> orderNos = new ArrayList<>();
        Set<String> orderNoSet = new HashSet<>();
        Files.lines(Paths.get(path)).forEach(line ->{
            String[] splits = line.split(",");
            String on = splits[1];
            if(orderNoSet.contains(on)){
                orderNos.add(on + "," + splits[0]);
            }
            orderNoSet.add(on);
        });

        orderNos.stream().forEach(l -> {
            System.out.println(l);
        });
    }
}
