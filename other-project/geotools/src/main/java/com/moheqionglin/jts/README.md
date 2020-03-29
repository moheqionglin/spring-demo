重点理解JTS Geometry model
## JTS提供了如下的空间数据类型
-   Point     
-   MultiPoint
-   LineString     
-   LinearRing  封闭的线条
-   MultiLineString    多条线
-   Polygon
-   MultiPolygon         
-   GeometryCollection  包括点，线，面

## 支持接口
-   Coordinate
   Coordinate（坐标）是用来存储坐标的轻便的类。它不同于点，点是Geometry的子类。不像模范Point的对象（包含额外的信息，例如一个信包，一个精确度模型和空间参考系统信息），Coordinate只包含纵座标值和存取方法。
-   Envelope(矩形)
   一个具体的类，包含一个最大和最小的x 值和y 值。
-   GeometryFactory
   GeometryFactory提供一系列的有效方法用来构造来自Coordinate类的Geometry对象。支持接口