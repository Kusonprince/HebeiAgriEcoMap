package com.example.hebeiagriecomap;

import android.app.Activity;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.geobeans.android.input.AndroidMotionEvent;
import cn.geobeans.backend.CanvasAdapter;
import cn.geobeans.backend.canvas.Bitmap;
import cn.geobeans.common.GeoPoint;
import cn.geobeans.event.Gesture;
import cn.geobeans.layers.GPathLayer;
import cn.geobeans.layers.GPolygonLayer;
import cn.geobeans.layers.ItemizedLayer;
import cn.geobeans.layers.MarkerItem;
import cn.geobeans.layers.MarkerSymbol;
import cn.geobeans.layers.OnItemGestureListener;
import cn.geobeans.map.Map;
import cn.geobeans.map.ViewController;
public class PGISEventHandler implements OnGestureListener, OnDoubleTapListener,OnItemGestureListener
{
    private final AndroidMotionEvent mMotionEvent;
    private final Map mMap;
    private ViewController mViewport;
    private Activity context;

    public PGISEventHandler(Map map,Activity context)//构造方法
    {
        mMotionEvent = new AndroidMotionEvent();
        mMap = map;
        mViewport = mMap.viewport();
        this.context=context;

    }
    //采集相关代码
    private int caijioverlayType=-1;
    public void setCaijiOperation(int type)
    {
        caijioverlayType=type;
        if(caijioverlayType>=1)
        {
            initPolygon();
            initMark();
            initPath();
        }
    }
	/* GesturListener */

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        //	return mMap.handleGesture(Gesture.TAP, mMotionEvent.wrap(e));


        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        mMap.handleGesture(Gesture.LONG_PRESS, mMotionEvent.wrap(e));
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return mMap.handleGesture(Gesture.PRESS, mMotionEvent.wrap(e));
    }

    /* DoubleTapListener */
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e)
    {
        GeoPoint gp=mViewport.fromScreenPoint(e.getX(), e.getY());
        if(caijioverlayType==1)
            addMark(gp.getLatitude(),gp.getLongitude());
        else if(caijioverlayType==2)
            addLinePt(gp);
        else if(caijioverlayType==3)
            addPolygonPt(gp);
        return mMap.handleGesture(Gesture.TAP, mMotionEvent.wrap(e));
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e)
    {
        if(caijioverlayType>=1)
        {
            caijioverlayType=-1;
            return false;
        }
        else
            return mMap.handleGesture(Gesture.DOUBLE_TAP, mMotionEvent.wrap(e));
    }

    ItemizedLayer markerLayer;
    public void initMark()
    {
        if(markerLayer==null)
        {
            Bitmap bitmap =CanvasAdapter.getBitmapAsset("1.png");
            MarkerSymbol symbol;
            symbol = new MarkerSymbol(bitmap, 0.5f, 0.5f);
            markerLayer = new ItemizedLayer(mMap, new ArrayList<MarkerItem>(),
                    symbol, this);
            mMap.layers().add(markerLayer);
        }
    }

    public void addMark(double lat,double lon)
    {
        markerLayer.addItem(new MarkerItem(lat + "/" + lon, "",  new GeoPoint(lat, lon)));
        mMap.render();
    }

    GPathLayer pathLayer;
    private void initPath()
    {
        if(pathLayer==null)
        {
            int c = 0xFF00FF00;
            pathLayer = new GPathLayer(mMap, c,2); //线色， 线宽
            mMap.layers().add(pathLayer);
        }
    }
    public void addLinePt(GeoPoint pt)
    {
        pathLayer.addPoint(pt);
        mMap.render();
    }

    public void setLinePts(List<GeoPoint> pts)
    {
        pathLayer.setPoints(pts);
        mMap.render();
    }

    GPolygonLayer polygonPath;
    public void initPolygon()
    {
        if(polygonPath==null)
        {
            polygonPath=new GPolygonLayer(mMap,0xff00ff00,3,0x440000ff);
            mMap.layers().add(polygonPath);
        }

    }

    public void addPolygonPt(GeoPoint pt)
    {
        polygonPath.addPoint(pt);
        mMap.updateMap(true);
    }

    //Mark点击事件处理,来自OnItemGestureListener.class
    public boolean onItemSingleTapUp(int index, Object obj)
    {
        MarkerItem item=(MarkerItem)obj;
        Toast toast = Toast.makeText(this.context, item.getTitle(), Toast.LENGTH_SHORT);
        toast.show();
        return true;
    }

    //Mark点击事件处理,来自OnItemGestureListener.class
    public boolean onItemLongPress(int index, Object item)
    {
        return false;
    }

    public List<GeoPoint> getPolyData()
    {
        if(polygonPath==null) return null;
        return polygonPath.getPoints();
    }

    public void clear()
    {
        mMap.layers().remove(pathLayer);
        mMap.layers().remove(markerLayer);
        mMap.layers().remove(polygonPath);
        pathLayer=null;
        markerLayer=null;
        polygonPath=null;
        mMap.updateMap(true);
        caijioverlayType=-1;
    }
}
