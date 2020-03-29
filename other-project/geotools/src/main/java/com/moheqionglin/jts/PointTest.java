package com.moheqionglin.jts;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

/**
 * @ClassName : Point
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-29 16:27
 */
public class PointTest {
    private GeometryFactory geometryFactory = new GeometryFactory();

    public static void main(String[] args) throws ParseException {
        PointTest pointTest = new PointTest();
        Point point = pointTest.createPoint("POINT(120.10 40.20)");
        Point point1 = pointTest.createPoint(new Coordinate(120.11, 40.21));
        //平面距离
        System.out.println(point.distance(point1));
        //球面距离
        System.out.println(SphericalDistanceUtils.distance(40.20, 120.10, 40.21, 120.11));

        System.out.println("-----------");
        MultiPoint multiPoint = pointTest.createMulPointByWKT("MULTIPOINT(109.013388 32.715519,119.32488 31.435678)");

    }
    /**
     * create multiPoint by wkt
     * @return
     */
    public MultiPoint createMulPointByWKT(String wkt)throws ParseException{
        WKTReader reader = new WKTReader( geometryFactory );
        MultiPoint mpoint = (MultiPoint) reader.read(wkt);
        return mpoint;
    }

    public Point createPoint(Coordinate coordinate){
        return geometryFactory.createPoint(coordinate);
    }

    public Point createPoint(String wkt){
        WKTReader r = new WKTReader(new GeometryFactory());
        Point p = null;
        try {
            p = (Point) r.read(wkt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return p;
    }
}