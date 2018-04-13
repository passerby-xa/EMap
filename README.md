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

1，emap版本更新至1.0.2，compile 'com.gis.lib:lib:1.0.2'

2，demo中MainActivity中增加了MyMapOnTouchListener内部类，一些点击操作，比如获取经纬度和点中了哪个点等常用操作，看onSingleTap方法中的示例代码

3，地图事件的变更，具体看MainActivity即可

-------------------------4.13更新--------------------------------

1，emap版本更新至1.0.5，compile 'com.gis.lib:lib:1.0.5'

2，监听器中增加了长按事件

3，增加画扇面方法，addSector(Point center, double radius, double startAngle,double endAngle,Symbol symbol); //原点,半径（米）,开始角度,结束角度，样式

4，增加两点间距离计算方法，getDistance

5，按需求更改了轨迹线中add方法，直接add即加点连线，同时返回线段编号，去除了end()方法。

6，增加了线段分段选中getSelectedIndexLine方法。

以上更新在demo的MainActivity中都有示例。


