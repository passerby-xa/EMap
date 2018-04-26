package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapOnTouchListener;
import com.esri.android.map.MapView;
import com.esri.core.geometry.Point;
import com.esri.core.symbol.FillSymbol;
import com.esri.core.symbol.LineSymbol;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.gis.lib.Draw.DrawEvent;
import com.gis.lib.Draw.DrawEventListener;
import com.gis.lib.Draw.DrawTool;
import com.gis.lib.Draw.TrackTool;
import com.gis.lib.map.EMap;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements DrawEventListener {

    private Context context;
    private EMap emap = null;
    private MapView mapView = null;
    private GraphicsLayer graphicsLayer = null;
    private DrawTool drawTool;
    public MyMapOnTouchListener mapDefaultOnTouchListener;//点击事件

    TrackTool tool_line;
    TrackTool tool_point;
    List<Integer> list_line;
    List<Integer> list_point;
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
       int id= emap.addPoint(120.09739062959324,30.266542846034017,new SimpleMarkerSymbol(Color.RED, 20, SimpleMarkerSymbol.STYLE.CIRCLE));


        //画线
        List<Double[]> myList=new ArrayList<Double[]>();
        myList.add(new Double[]{120.10123,30.2321});
        myList.add(new Double[]{123.1523,32.123321});
        emap.addPolyline(myList, new SimpleLineSymbol(Color.GREEN, 10));


        //画扇面
        LineSymbol mLineSymbol = new SimpleLineSymbol(Color.BLACK, 2);//边线样式
        FillSymbol mFillSymbol =  new SimpleFillSymbol(Color.BLACK).setOutline(mLineSymbol).setAlpha(100);//面样式
        Point center=new Point();
        center.setX(120.10171884705784);
        center.setY(30.275667119589798);
        emap.addSector(center, 1000, 0,60,mFillSymbol); //原点,半径（米）,开始角度,结束角度，样式
        // emap.clearSector();//删除扇面
        //emap.clearCircle();//删除圆
        // emap.clear();//删除其它
        //emap.clear(id);删点

        //画圆
        FillSymbol mFillSymbol2 =  new SimpleFillSymbol(Color.BLUE).setOutline(mLineSymbol).setAlpha(100);//面样式
        emap.addCircle(center, 1000,mFillSymbol2); //原点,半径（米）,样式

        //两点间距离（米）
        double distance= emap.getDistance(119.2248,29.60848,119.2346,29.60893);
        Log.e("distance:",distance+"");

        //轨迹线-线
        tool_line=new TrackTool(120.10123, 30.321, new SimpleLineSymbol(Color.BLACK, 5), mapView);
        int i=tool_line.addLine(122.123321,30.1523);
        int j=tool_line.addLine(124.123321,31.1523);
        int k= tool_line.addLine(123.123321,32.1523);
        list_line=new ArrayList();
        list_line.add(i);
        list_line.add(j);
        list_line.add(k);
       // tool_line.clear_line(j);//删除线
       // tool_line.clear_line(k);

        //轨迹线-点
        SimpleMarkerSymbol mRedMarkerSymbol = new SimpleMarkerSymbol(Color.RED, 15, SimpleMarkerSymbol.STYLE.CIRCLE);
        tool_point=new TrackTool(mRedMarkerSymbol,  mapView);
        int x=tool_point.addPoint(120.10123, 30.321);
        int m=tool_point.addPoint(122.123321,30.1523);
        int n=tool_point.addPoint(124.123321,31.1523);
        int q= tool_point.addPoint(123.123321,32.1523);
        list_point=new ArrayList();
        list_point.add(x);
        list_point.add(m);
        list_point.add(n);
        list_point.add(q);

      //  tool_point.clear_point(m); //删除某一点
     //   tool_point.clear_point(n);


        //设置地图事件
        mapDefaultOnTouchListener = new MyMapOnTouchListener(emap.getMap().getContext(), emap.getMap());
        emap.setOnTouchListener(mapDefaultOnTouchListener);

        /*ToolsOnClickListener toolsOnClickListener = new ToolsOnClickListener(context,drawTool,selectGraphic,emap.getMap());

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
*/



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

            int idx = tool_point.getSelectedIndex(e.getX(), e.getY());  //点中了轨迹线中的第几个点，返回-1则表示一个也没有点中
            if (idx != 0) {
                for (int i=0;i<list_point.size();i++){
                    if ( idx==list_point.get(i)){
                        Log.e("点击了tool_point的第", i + "个点！");
                        Toast.makeText(mapView.getContext(), "点击了tool_point的第"+i + "个点！", Toast.LENGTH_SHORT).show();
                    }
                }
                /*
                *******
                点中以后的一些操作，比如页面跳转等
                ********
                */
            }else{
                //线段点击
                int j= tool_line.getSelectedIndexLine(e.getX(), e.getY());
                for (int i=0;i<list_line.size();i++){
                    if ( j==list_line.get(i)){
                        Toast.makeText(mapView.getContext(), "点击了tool_line的第"+i + "段线！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            return super.onSingleTap(e);
        }

        public void onLongPress(MotionEvent point) {
            //长按
            Toast.makeText(mapView.getContext(), "长按事件", Toast.LENGTH_SHORT).show();
        }

    }

}
