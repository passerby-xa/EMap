package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.Toast;

import com.esri.android.map.Callout;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapOnTouchListener;
import com.esri.android.map.MapView;
import com.esri.core.geometry.Point;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.gis.lib.Draw.DrawEvent;
import com.gis.lib.Draw.DrawEventListener;
import com.gis.lib.Draw.DrawTool;
import com.gis.lib.Draw.RouteInfo;
import com.gis.lib.Draw.TrackTool;
import com.gis.lib.map.EMap;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements DrawEventListener {

    private Context context;
    private EMap emap = null;
    private MapView mapView = null;
    private GraphicsLayer graphicsLayer = null;

    private Graphic selectGraphic = null;
    private DrawTool drawTool;
    Callout callout;
    public MyMapOnTouchListener mapDefaultOnTouchListener;//点击事件

    TrackTool tool;
    TrackTool tool2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        this.mapView = (MapView)this.findViewById(R.id.map);
        emap=new EMap(mapView);

        emap.showWorldSatelliteLayer();//天地图影像
        // emap.showWorldVectorLayer();//天地图矢量

        //居中位置
        emap.setCenter(120.09739062959324,30.266542846034017);
        //设置地图等级
        emap.setLevel(4);
        //画点
        emap.addPoint(120.09739062959324,30.266542846034017,new SimpleMarkerSymbol(Color.RED, 20, SimpleMarkerSymbol.STYLE.CIRCLE));

        //画线
        List<Double[]> myList=new ArrayList<Double[]>();
        myList.add(new Double[]{120.10123,30.2321});
        myList.add(new Double[]{123.1523,32.123321});
        emap.addPolyline(myList, new SimpleLineSymbol(Color.GREEN, 10));

        //轨迹线
        SimpleMarkerSymbol mRedMarkerSymbol = new SimpleMarkerSymbol(Color.RED, 15, SimpleMarkerSymbol.STYLE.CIRCLE);
        tool=new TrackTool(120.10123, 30.321, mRedMarkerSymbol, new SimpleLineSymbol(Color.BLACK, 5),true, mapView);
        tool.add(122.123321,30.1523);
        tool.add(124.123321,31.1523);
        tool.add(123.123321,32.1523);
        List<RouteInfo> list=tool.end();//结束

        for (int i=0;i<list.size();i++){
            Log.e("RouteInfo",list.get(i).getX()+","+list.get(i).getY()+","+list.get(i).getX1()+","+list.get(i).getY1()+","+list.get(i).getDistance());
        }

        tool2=new TrackTool(119.10123, 29.321, mRedMarkerSymbol, new SimpleLineSymbol(Color.BLUE, 5),true, mapView);
        tool2.add(120.123321,30.1523);
        tool2.add(122.123321,32.1523);
        tool2.add(121.123321,32.1523);
        List<RouteInfo> list2= tool2.end();

        graphicsLayer = new GraphicsLayer();
        emap.addLayer(graphicsLayer);


        // 初始化DrawTool实例
        this.drawTool = new DrawTool(emap.getMap());
        // 将本Activity设置为DrawTool实例的Listener
        this.drawTool.addEventListener(this);

        //设置地图事件
        mapDefaultOnTouchListener = new MyMapOnTouchListener(emap.getMap().getContext(), emap.getMap());
        emap.setOnTouchListener(mapDefaultOnTouchListener);

        ToolsOnClickListener toolsOnClickListener = new ToolsOnClickListener(context,drawTool,selectGraphic,emap.getMap());

        Button btnDrawPoint = (Button)this.findViewById(R.id.btnDrawPoint);
        btnDrawPoint.setOnClickListener(toolsOnClickListener);

        Button btnDrawPolyline = (Button)this.findViewById(R.id.btnDrawPolyline);
        btnDrawPolyline.setOnClickListener(toolsOnClickListener);

        Button btnDrawPolygon = (Button)this.findViewById(R.id.btnDrawPolygon);
        btnDrawPolygon.setOnClickListener(toolsOnClickListener);

        Button btnDrawCircle = (Button)this.findViewById(R.id.btnDrawCircle);
        btnDrawCircle.setOnClickListener(toolsOnClickListener);

        Button btnFinshDraw = (Button)this.findViewById(R.id.btnFinshDraw);
        btnFinshDraw.setOnClickListener(toolsOnClickListener);


    }

    @Override
    public void handleDrawEvent(DrawEvent event) {
        // 将画好的图形（已经实例化了Graphic），添加到drawLayer中并刷新显示
        this.graphicsLayer.addGraphic(event.getDrawGraphic());
        // 修改点击事件为默认
        this.mapView.setOnTouchListener(mapDefaultOnTouchListener);
    }


    class MyMapOnTouchListener extends MapOnTouchListener {
        public MyMapOnTouchListener(Context context, MapView view) {
            super(context, view);
        }
        public boolean onSingleTap(MotionEvent e) {
            Point point = mapView.toMapPoint(e.getX(), e.getY());
            //绘点
            emap.addPoint(point.getX(), point.getY(), new SimpleMarkerSymbol(Color.RED, 20, SimpleMarkerSymbol.STYLE.CIRCLE));
            Log.e("点击", point.getX() + "," + point.getY()); //点击的经纬度
            int idx = tool.getSelectedIndex(e.getX(), e.getY());  //点中了轨迹线中的第几个点，返回-1则表示一个也没有点中
            int idx2 = tool2.getSelectedIndex(e.getX(), e.getY());
            if (idx != -1) {
                Log.e("点击了tool的第", idx + "个点！");
                Toast.makeText(mapView.getContext(), "点击了tool的第"+idx + "个点！", Toast.LENGTH_SHORT).show();

                /*
                *******
                点中以后的一些操作，比如页面跳转等
                ********
                */
            }else{
                if (idx2 != -1) {
                    Log.e("点击了tool2的第", idx2 + "个点！");
                    Toast.makeText(mapView.getContext(), "点击了tool2的第"+idx2 + "个点！", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(mapView.getContext(), "点击了："+point.getX() + "," + point.getY(), Toast.LENGTH_SHORT).show();
                }
            }
            return super.onSingleTap(e);
        }

    }

}
