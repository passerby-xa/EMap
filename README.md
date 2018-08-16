# EMap
EMap demo

====在线emap的依赖库=====

外层build.gradle添加maven库：


 
 maven {
 
 		url  "https://dl.bintray.com/passerby-xa/EMap"
		
       }
       

内层build.gradle增加emap的lib依赖	  

	compile 'com.gis.lib:lib:1.0.1'   
	
	
-------------------------3.16更新--------------------------------

1，emap版本更新至1.0.2，

compile 'com.gis.lib:lib:1.0.2'

2，demo中MainActivity中增加了MyMapOnTouchListener内部类，一些点击操作，比如获取地图上任意一点的经纬度、点中了轨迹线中哪个点等常用操作，看onSingleTap方法中的示例代码

3，地图事件的变更，具体看MainActivity即可

-------------------------4.13更新--------------------------------

1，emap版本更新至1.0.5，compile 'com.gis.lib:lib:1.0.5'

2，监听器中增加了长按事件

3，增加画扇面方法，addSector(Point center, double radius, double startAngle,double endAngle,Symbol symbol); //原点,半径（米）,开始角度,结束角度，样式

4，增加两点间距离计算方法，getDistance

5，按需求更改了轨迹线中add方法，直接add即加点连线，同时返回线段编号，去除了end()方法。

6，增加了线段分段选中getSelectedIndexLine方法。

以上更新在demo的MainActivity中都有示例。

-------------------------4.18更新--------------------------------

1，compile 'com.gis.lib:lib:1.0.6'

2，点返回唯一值，指定点删除。
int m=tool_point.addPoint(122.123321,30.1523); 
tool_point.clear_point(m); //删除某一点

3，轨迹线，点分开了。要点就点，点线都要就都调用
int i=tool_line.addLine(122.123321,30.1523);

4，删除扇面圆，删除其他
emap.clearSector();//删除扇面和圆
emap.clear();//删除其它

5，画圆覆盖物
emap.addCircle(center, 1000,mFillSymbol2); //原点,半径（米）,样式

-------------------------4.26更新--------------------------------

1，compile 'com.gis.lib:lib:1.0.7'

2, 删除圆 clearCircle()，删除扇形 clearSector()  

3, 删除指定点 emap.clear(int id);

4，轨迹线-线
tool_line=new TrackTool(120.10123, 30.321, Symbol, mapView);   
 
轨迹线-点
tool_point=new TrackTool(Symbol,  mapView);

5, 显示顺序，点线最上面，圆中间，扇面最下面

-------------------------4.27更新--------------------------------

1，compile 'com.gis.lib:lib:1.0.8'

2, 绘图选中点返回id  

3, 增加了选点按钮示例，通过锁定地图，拖动准十字星来精准选点

-------------------------6.15更新--------------------------------

1，compile 'com.gis.lib:lib:1.0.9'

2, 影像图修正  

3, 轨迹线-线每次需传两个点
tool_line.addLine(120.10123, 30.321,122.123321,30.1523);

4，离线地图
	增加获取城市接口：getCitys()；
	下载城市离线地图接口：downloadCity(0571);
	当前已下载量接口：getDownloadCurrent();
    下载总量接口：getDownloadCount：getDownloadCount(0571);
	
-------------------------6.21更新--------------------------------

1，compile 'com.gis.lib:lib:1.0.10'

2, Map map= emap.getCity(120.10171884705784,30.275667119589798); //获取当前所在城市

3, String time=emap.downloadCity(0571); //返回下载时间

-------------------------6.22更新--------------------------------

1，compile 'com.gis.lib:lib:1.0.11'

2, 返回时间采用回调函数
  
    emap.setCallBack(this);//注册监听

	继承CallBack接口
	
	@Override
    public void backTime(String time) {
        //将城市编号和时间存在sp中，便于以后查询
        SharedPreferences.Editor editor = getSharedPreferences("map", MODE_WORLD_WRITEABLE).edit();
         editor.putString("0571", time);
        editor.commit();
    }

3, 增加变更点样式方法
    tool_point.updatePointSymbol(m,new SimpleMarkerSymbol(Color.BLUE, 15, SimpleMarkerSymbol.STYLE.CIRCLE));

-------------------------6.28更新--------------------------------

1，compile 'com.gis.lib:lib:1.0.12'

2, 返回进度采用回调函数
  
    emap.setCallBack(this);//注册监听

	继承CallBack接口
	
    @Override
    public void count(int count){
        //count进度值

    }
3，增加了EMap()的无参构造函数。 这样下载和地图可分开。	

3, 取消了根据经纬度获取当前所在城市

-------------------------8.14更新--------------------------------

1，compile 'com.gis.lib:lib:1.0.20'

2, 回调函数中增加了城市编号
  
    @Override
    public void backTime(String time,int citycode){
        //将城市编号和时间存在sp中，便于以后查询
        /* SharedPreferences.Editor editor = getSharedPreferences("map", MODE_WORLD_WRITEABLE).edit();
         editor.putString("0571", time);
        editor.commit();*/
    }

    public void count(int count,int citycode){
      //进度
        Log.e("tttt"+citycode,count+"");
    }
	
3， 下载离线地图 emap.downloadTiled(double xmin,double ymin,double xmax,double ymax,int citycode); //单线程下载
     emap.downloadTiledThread(double xmin,double ymin,double xmax,double ymax,int citycode);   //多线程下载

4,  取消了水印

5， 离线地图下载包含注记层

6， 单独增加了定位的图层和加点以及删除方法 emap.addPosition(); emap.clearPosition(); 

7,  点位边上增加文字，emap.addPoint(120.10171884705784,30.275667119589798,new TextSymbol(20, " abc你好！", Color.RED).setFontFamily("DroidSansFallback.ttf"));

-------------------------8.15更新--------------------------------

1，compile 'com.gis.lib:lib:1.0.21'

2, 增加了按钮，矢量地图和影像地图直接切换即可