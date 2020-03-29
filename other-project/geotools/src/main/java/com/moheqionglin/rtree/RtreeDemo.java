package com.moheqionglin.rtree;

import com.vividsolutions.jts.algorithm.locate.IndexedPointInAreaLocator;
import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.index.strtree.STRtree;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @ClassName : RtreeDemo
 * @Description :
 * @Author : wanli.zhou
 * @Date: 2020-03-28 22:50
 */
public class RtreeDemo {
    private STRtree tree = new STRtree();
    private GeometryFactory geometryFactory = new GeometryFactory();


    /**
     * 正方形 1
     * 120.10 40.20,        120.20 40.20,
     * 120.10 40.10         120.20 40.10
     *
     * 正方形2
     *
     * 110.10 40.20,        110.20 40.20,
     * 110.10 40.10         110.20 40.10
     *
     * 正方体3
     *
     * 100.10 40.20,        100.20 40.20,
     * 100.10 40.10         100.20 40.10
     *  z高100， z底0
     * @param args
     */
    public static void main(String[] args) {
        RtreeDemo rtreeDemo = new RtreeDemo();
        //没有buffer， 纯粹平面
        Envelope envelope1 = new Envelope(120.10, 40.20,120.20, 40.10);
        Envelope envelope2 = new Envelope(110.10, 40.20,110.20, 40.10);

        rtreeDemo.upsertRtree(rtreeDemo.generatePolygon("POLYGON((120.10 40.20, 120.20 40.20, 120.20 40.10,120.10 40.10, 120.10 40.20))", 0, null));
        rtreeDemo.upsertRtree(rtreeDemo.generatePolygon("POLYGON((110.10 40.20, 110.20 40.20, 110.20 40.10,110.10 40.10, 110.10 40.20))", 0, null));
        //没有buffer的， 中轴线跟z平行的 3D
        rtreeDemo.upsertRtree(rtreeDemo.generatePolygon("POLYGON((100.10 40.20, 100.20 40.20, 100.20 40.10,100.10 40.10, 100.10 40.20))", 0, "100, 0"));
        //有buffer的 纯粹平面
        rtreeDemo.upsertRtree(rtreeDemo.generatePolygon("POLYGON((100.10 40.20, 100.20 40.20, 100.20 40.10,100.10 40.10, 100.10 40.20))", 100000, null));

        boolean inGeometry = rtreeDemo.isInGeometry(110.11, 40.11, null);
        boolean inGeometry2 = rtreeDemo.isInGeometry(100.11, 40.11, 102d);
        boolean inGeometry3 = rtreeDemo.isInGeometry(100.11, 40.11, 99d);
        boolean inGeometry4 = rtreeDemo.isInGeometry(100.20000009, 40.200000009, null);
        System.out.println(inGeometry);
        System.out.println(inGeometry2);
        System.out.println(inGeometry3);
        System.out.println(inGeometry4);
    }

    public boolean isInGeometry(Double lon, Double lat, Double alt){
        final Coordinate coordinate = alt != null ?
                new Coordinate(lon, lat, alt) : new Coordinate(lon, lat);
        List<Polygon> inPols = new ArrayList();
        tree.query(new Envelope(coordinate), (Object item) ->{
            RtreeAttach attach = (RtreeAttach) item;
                int location = attach.locator.locate(coordinate);
                if (location == 0 || location == 1) {
                    String userdata = (String) attach.polygon.getUserData();
                    if(userdata != null){//处理 模拟 特殊3D空间体： 中轴线平行于z轴的，  我们可以把 3D物体的 在Z轴的上下限放进去。
                        String[] split = userdata.split(",");
                        double altUp = Double.valueOf(split[0]);
                        double altDown = Double.valueOf(split[1]);
                        if(coordinate.z <= altUp && coordinate.z >= altDown){
                            inPols.add(attach.polygon);
                        }
                    }else{
                        inPols.add(attach.polygon);
                    }
                }
        });
        return !inPols.isEmpty();
    }

    /**
     * @param polygon
     */
    public void upsertRtree(Polygon polygon){
        IndexedPointInAreaLocator locator = new IndexedPointInAreaLocator(polygon);
        tree.insert(polygon.getEnvelopeInternal(), new RtreeAttach(locator, polygon, null));
    }

    /**
     *
     * @param wkt
     * @param buffer
     * @param userData
     * @return
     * userData 可以放一些附属信息， 比如：
     *  - 模拟 特殊3D空间体： 中轴线平行于z轴的，  我们可以把 3D物体的 在Z轴的上下限放进去。
     *  - 模拟 特殊3D空间体： 中轴线不平行于z轴，
     */
    public Polygon generatePolygon(String wkt, double buffer, String userData){
        Polygon polygon = null;
        try {
            polygon = (Polygon) new WKTReader(geometryFactory).read(wkt);
            if(buffer > 0){
                polygon.buffer( ((buffer / (111 * 1000d)) + (buffer / (111 * 1000 * Math.cos(Math.toRadians(polygon.getCoordinate().y))))) / 2);
            }
            //可以定义
            polygon.setUserData(userData);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return polygon;
    }

    class RtreeAttach{
        public IndexedPointInAreaLocator locator;
        public Polygon polygon;
        public HashMap<String, String> attr = new HashMap<>();

        public RtreeAttach(IndexedPointInAreaLocator locator, Polygon polygon, HashMap<String, String> attr) {
            this.locator = locator;
            this.polygon = polygon;
            this.attr = attr;
        }
    }
}