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

