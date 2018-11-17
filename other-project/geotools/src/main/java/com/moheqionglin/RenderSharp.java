package com.moheqionglin;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.data.JFileDataStoreChooser;

import java.io.File;
import java.nio.charset.Charset;

public class RenderSharp {
 
	public static void main(String[] args) throws Exception{
		
		//1.数据源选择 shp扩展类型的
		File file = JFileDataStoreChooser.showOpenFile("shp", null);
		if(file==null){
			return;
		}
		
		//2.得到打开的文件的数据源
		FileDataStore store = FileDataStoreFinder.getDataStore(file);
		
		//3.设置数据源的编码，防止中文乱码
		((ShapefileDataStore)store).setCharset(Charset.forName("UTF-8"));
		
		
		/**
		 * 使用FeatureSource管理要素数据
		 * 使用Style（SLD）管理样式
		 * 使用Layer管理显示
		 * 使用MapContent管理所有地图相关信息
		 */
		
		//4.以java对象的方式访问地理信息
		SimpleFeatureSource featureSource = store.getFeatureSource();
		
		//5.创建映射内容，并将我们的shapfile添加进去
	    MapContent mapContent = new MapContent();
	    
	    //6.设置容器的标题
	    mapContent.setTitle("Appleyk's GeoTools");
	    
	    //7.创建简单样式
	    Style style = SLD.createSimpleStyle(featureSource.getSchema());
	    
	    //8.显示【shapfile地理信息+样式】
	    Layer layer = new FeatureLayer(featureSource, style);
	    
	    //9.将显示添加进map容器
	    mapContent.addLayer(layer);
	    
	    //10.窗体打开，高大尚的操作开始
	    JMapFrame.showMap(mapContent);
	    
	}
 
}
