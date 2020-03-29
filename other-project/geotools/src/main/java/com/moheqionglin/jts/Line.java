package com.moheqionglin.jts;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

/**
 * @ClassName : Line
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-29 16:27
 */
public class Line {
    private GeometryFactory geometryFactory = new GeometryFactory();

    /**
     *
     * create a line
     * @return
     */
    public LineString createLine(){
        Coordinate[] coords  = new Coordinate[] {new Coordinate(2, 2), new Coordinate(2, 2)};
        LineString line = geometryFactory.createLineString(coords);
        return line;
    }

    /**
     * create a line by WKT
     * @return
     */
    public LineString createLineByWKT() throws ParseException {
        WKTReader reader = new WKTReader( geometryFactory );
        LineString line = (LineString) reader.read("LINESTRING(0 0, 2 0)");
        return line;
    }

    /**
     * create multiLine
     * @return
     */
    public MultiLineString createMLine(){
        Coordinate[] coords1  = new Coordinate[] {new Coordinate(2, 2), new Coordinate(2, 2)};
        LineString line1 = geometryFactory.createLineString(coords1);
        Coordinate[] coords2  = new Coordinate[] {new Coordinate(2, 2), new Coordinate(2, 2)};
        LineString line2 = geometryFactory.createLineString(coords2);
        LineString[] lineStrings = new LineString[2];
        lineStrings[0]= line1;
        lineStrings[1] = line2;
        MultiLineString ms = geometryFactory.createMultiLineString(lineStrings);
        return ms;
    }

    /**
     * create multiLine by WKT
     * @return
     * @throws ParseException
     */
    public MultiLineString createMLineByWKT()throws ParseException{
        WKTReader reader = new WKTReader( geometryFactory );
        MultiLineString line = (MultiLineString) reader.read("MULTILINESTRING((0 0, 2 0),(1 1,2 2))");
        return line;
    }
}