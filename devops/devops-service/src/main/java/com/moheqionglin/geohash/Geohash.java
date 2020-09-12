package com.moheqionglin.geohash;


import com.github.davidmoten.geo.Direction;
import com.github.davidmoten.geo.GeoHash;

public class Geohash {
    private static final double HARTFORD_LON = -72.727175;
    private static final double HARTFORD_LAT = 41.842967;
    private static final double SCHENECTADY_LON = -73.950691;
    private static final double SCHENECTADY_LAT = 42.819581;
    private static final double PRECISION = 0.000000001;
    private static final int I_LEFT      = 0;
    private static final int I_RIGHT     = 1;
    private static final int I_TOP       = 2;
    private static final int I_BOTTOM    = 3;
    private static final int I_LEFT_TOP  = 4;
    private static final int I_LEFT_BOT  = 5;
    private static final int I_RIGHT_TOP = 6;
    private static final int I_RIGHT_BOT = 7;

    public static void main(String[] args) {
        String s = GeoHash.encodeHash(31.257829, 121.540025, 6);
        System.out.println(s);
        String centerGeoHash = "ws10k0";
        System.out.println("左上" + GeoHash.adjacentHash(GeoHash.adjacentHash(centerGeoHash, Direction.LEFT), Direction.TOP));
        System.out.println("上" + GeoHash.adjacentHash(centerGeoHash, Direction.TOP));
        System.out.println("右上" + GeoHash.adjacentHash(GeoHash.adjacentHash(centerGeoHash, Direction.RIGHT), Direction.TOP));
        System.out.println("左" + GeoHash.adjacentHash(centerGeoHash, Direction.LEFT));
        System.out.println("中" + centerGeoHash);
        System.out.println("右" + GeoHash.adjacentHash(centerGeoHash, Direction.RIGHT));
        System.out.println("左下" + GeoHash.adjacentHash(GeoHash.adjacentHash(centerGeoHash, Direction.LEFT), Direction.BOTTOM));
        System.out.println("下" + GeoHash.adjacentHash("ws10k0", Direction.BOTTOM));
        System.out.println("右下" + GeoHash.adjacentHash(GeoHash.adjacentHash(centerGeoHash, Direction.RIGHT), Direction.BOTTOM));
    }


}
