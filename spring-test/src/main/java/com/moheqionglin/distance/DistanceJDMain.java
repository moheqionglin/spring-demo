package com.moheqionglin.distance;


import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class DistanceJDMain {
    private static final double EARTH_RADIUS = 6371393;


    public static double getDistance(Double lat1,Double lng1,Double lat2,Double lng2) {
        // 经纬度（角度）转弧度。弧度用作参数，以调用Math.cos和Math.sin
        double radiansAX = Math.toRadians(lng1); // A经弧度
        double radiansAY = Math.toRadians(lat1); // A纬弧度
        double radiansBX = Math.toRadians(lng2); // B经弧度
        double radiansBY = Math.toRadians(lat2); // B纬弧度

        // 公式中“cosβ1cosβ2cos（α1-α2）+sinβ1sinβ2”的部分，得到∠AOB的cos值
        double cos = Math.cos(radiansAY) * Math.cos(radiansBY) * Math.cos(radiansAX - radiansBX)
                + Math.sin(radiansAY) * Math.sin(radiansBY);
//        System.out.println("cos = " + cos); // 值域[-1,1]
        double acos = Math.acos(cos); // 反余弦值
//        System.out.println("acos = " + acos); // 值域[0,π]
//        System.out.println("∠AOB = " + Math.toDegrees(acos)); // 球心角 值域[0,180]
        return EARTH_RADIUS * acos; // 最终结果
    }


    public void read() throws IOException {

        PrintWriter printer = new PrintWriter("/Users/zhouwanli/Desktop/jd-station/rst2");
        List<JdStation> jdStationList = new ArrayList<>();
        List<XXStation> xxStationList = new ArrayList<>();
        HashMap<Integer, String> station2SupplierId = new HashMap<>();

        this.loadJdStation(jdStationList);
        this.loadOtherStation(xxStationList);
        this.loadJd(station2SupplierId);

        xxStationList.stream().forEach(xxStation -> {
            List<JdStation> ss = new ArrayList<>();
            jdStationList.stream().forEach(jdStation -> {
                double dis = getDistance(xxStation.lat, xxStation.lon,
                        jdStation.lat, jdStation.lon);
                if(dis <= 3000){
                    jdStation.station = station2SupplierId.get(jdStation.id);
                    ss.add(jdStation);
                }
            });
            if(ss.size() > 0){
                printer.println(xxStation.toString() + " 3KM附近京东站点 " + ss.stream().map(JdStation::toString).collect(Collectors.joining(" | ")));
            }

        });
        printer.close();
    }

    private void loadJd(HashMap<Integer, String> station2SupplierId) throws IOException {
        Files.lines(Paths.get("/Users/zhouwanli/Desktop/jd-station/jdsupp/1.csv")).forEach(line -> {
            String ss[] = line.split(",");
            station2SupplierId.put(Integer.valueOf(ss[1]), ss[0]);
        });
    }

    private void loadOtherStation(List<XXStation> xxStationList) throws IOException {
        Files.lines(Paths.get("/Users/zhouwanli/Desktop/jd-station/jd-bianli.csv")).forEach(line -> {
            String ss[] = line.split(",");
            xxStationList.add(new XXStation(ss[0], Double.valueOf(ss[2]), Double.valueOf(ss[1])));
        });
        System.out.println("xx stations = " + xxStationList.size());
    }

    private void loadJdStation(List<JdStation> jdStationList) throws IOException {
        Files.lines(Paths.get("/Users/zhouwanli/Desktop/jd-station/1.csv")).forEach(line -> {
            String ss[] = line.split(",");
            jdStationList.add(new JdStation(Integer.valueOf(ss[0].replace("\"", "")),
                    Double.valueOf(ss[1].replace("\"", "")),
                    Double.valueOf(ss[2].replace("\"", "")),
                    ss[3].replace("\"", ""),
                    ss[4].replace("\"", "")));
        });
        System.out.println("jd stations = " + jdStationList.size());
    }

    public static void main(String[] args) throws IOException {
//        //121.717594,31.12055    121.817629,31.090867
//        double distance = getDistance(31.12055, 121.717594,
//                31.090867, 121.817629);
//        System.out.println("距离" + distance  + "米");
        new DistanceJDMain().read();
    }
    class JdStation{
        public int id;
        public String station;
        public double lat;
        public double lon;
        public String address;
        private String name;

        public JdStation(int id, double lat, double lon, String address, String name) {
            this.id = id;
            this.lat = lat;
            this.lon = lon;
            this.address = address;
            this.name = name;
        }

        @Override
        public String toString() {
            return id + "," + station +
                    "," + lat +
                    "," + lon +
                    "," + address +
                    "," + name  ;
        }
    }
    class XXStation{
        public String id;
        public double lat;
        public double lon;

        public XXStation(String id, double lat, double lon) {
            this.id = id;
            this.lat = lat;
            this.lon = lon;
        }

        @Override
        public String toString() {
            return id +
                    "," + lat +
                    "," + lon ;
        }
    }
}
