package com.example.administrator.myapplication;

import android.content.Context;
import android.view.View;

import com.esri.android.map.MapView;
import com.esri.core.map.Graphic;
import com.gis.lib.Draw.DrawTool;

/**
 * 绘图点击事件
 */
public class ToolsOnClickListener implements View.OnClickListener {

    private Context context = null;
    private DrawTool drawTool = null;
    private Graphic selectGraphic =null;
    private MapView mapView = null;

    public ToolsOnClickListener(Context context, DrawTool drawTool, Graphic selectGraphic, MapView mapView) {
        this.context = context;
        this.drawTool = drawTool;
        this.selectGraphic = selectGraphic;
        this.mapView = mapView;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnDrawPolyline://绘制线
                drawTool.activate(DrawTool.POLYLINE);
                break;

            case R.id.btnDrawPolygon://绘制面
                drawTool.activate(DrawTool.POLYGON);
                break;

            case R.id.btnDrawCircle://绘制圆
                drawTool.activate(DrawTool.CIRCLE);
                break;

            case R.id.btnFinshDraw://完成绘制
                drawTool.finshdraw();
                break;

        }

    }
}
