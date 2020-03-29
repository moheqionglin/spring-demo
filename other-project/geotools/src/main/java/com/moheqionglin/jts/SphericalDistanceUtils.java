package com.moheqionglin.jts;

public class SphericalDistanceUtils {
    //地球半径 km
    private static double EARTH_RADIUS = 6371.0;

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = convertDegreesToRadians(lat1);
        lon1 = convertDegreesToRadians(lon1);
        lat2 = convertDegreesToRadians(lat2);
        lon2 = convertDegreesToRadians(lon2);

        double vLon = Math.abs(lon1 - lon2);
        double vLat = Math.abs(lat1 - lat2);

        //就是一个球体上的切面，它的圆心即是球心的一个周长最大的圆。
        double h = haverSin(vLat) + Math.cos(lat1) * Math.cos(lat2) * haverSin(vLon);

        double distance = 2 * EARTH_RADIUS * Math.asin(Math.sqrt(h));

        return distance;
    }

    private static double haverSin(double theta) {
        double v = Math.sin(theta / 2);
        return v * v;
    }

    //将角度换算为弧度。
    private static double convertDegreesToRadians(double degrees) {
        return degrees * Math.PI / 180;
    }
}
