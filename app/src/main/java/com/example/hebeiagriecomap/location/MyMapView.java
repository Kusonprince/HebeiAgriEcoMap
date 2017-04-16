package com.example.hebeiagriecomap.location;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import cn.geobeans.android.MapView;
import cn.geobeans.common.GeoPoint;
import cn.geobeans.map.ViewController;
/**
 * Created by yyutter on 2017/3/12.
 */

public class MyMapView extends MapView{
   private ViewController mViewport;
    public MyMapView(Context context) {
        super(context);
    }
    public MyMapView(Context context, AttributeSet set) {
        super(context, set);
    }
    @Override
    //获取地理坐标
    public boolean onTouchEvent(MotionEvent var1) {
        int x = (int) var1.getX();
        int y = (int) var1.getY();

        mViewport = mMap.viewport();
        GeoPoint gp=mViewport.fromScreenPoint(x, y);
        return super.onTouchEvent(var1);
    }
/*    public boolean isInPolygon(Point point, Point[] points, int n) {
        int nCross = 0;
        for (int i = 0; i < n; i++) {
            Point p1 = points[i];
            Point p2 = points[(i + 1) % n];
            // 求解 y=p.y 与 p1 p2 的交点
            // p1p2 与 y=p0.y平行
            if (p1.y == p2.y)
                continue;
            // 交点在p1p2延长线上
            if (point.y < Math.min(p1.y, p2.y))
                continue;
            // 交点在p1p2延长线上
            if (point.y >= Math.max(p1.y, p2.y))
                continue;
            // 求交点的 X 坐标
            double x = (double) (point.y - p1.y) * (double) (p2.x - p1.x)
                    / (double) (p2.y - p1.y) + p1.x;
            // 只统计单边交点
            if (x > point.x)
                nCross++;
        }
        return (nCross % 2 == 1);
    }*/
}


   /* private String getAddress(Location location) throws IOException {
        Geocoder geocoder = new Geocoder(this);*/
        /*boolean falg = geocoder.isPresent();
        Log.e("xjp", "the falg is " + falg);*/
       /* StringBuilder stringBuilder = new StringBuilder();
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    stringBuilder.append(address.getAddressLine(i)).append("\n");
                }
                stringBuilder.append(address.getCountryName()).append("_");//国家
                stringBuilder.append(address.getFeatureName()).append("_");//周边地址
                stringBuilder.append(address.getLocality()).append("_");//市
                stringBuilder.append(address.getPostalCode()).append("_");
                stringBuilder.append(address.getCountryCode()).append("_");//国家编码
                stringBuilder.append(address.getAdminArea()).append("_");//省份
                stringBuilder.append(address.getSubAdminArea()).append("_");
                stringBuilder.append(address.getThoroughfare()).append("_");//道路
                stringBuilder.append(address.getSubLocality()).append("_");//香洲区
                stringBuilder.append(address.getLatitude()).append("_");//经度
                stringBuilder.append(address.getLongitude());//维度
                System.out.println(stringBuilder.toString());
            }
        } catch (IOException e) {
            Toast.makeText(this, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return stringBuilder.toString();*/


    //接下来处理传递来的地址信息

  /*public void getLocation(){
      LocationManager im = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
  }
*/
    //地图点击监听事件
    //实例化一个地理编码查询对象
    //GeoCoder geoCoder = GeoCoder.newInstance();
    //通过某个经纬度获取该经纬度的地址


 /*   public static void getCityFromLngAndlat(){
        String url2 = "http://t0.tianditu.cn/vec_c/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=vec&STYLE=default&TILEMATRIXSET=c&FORMAT=tiles";
        URL myURL2 = null;
        URLConnection httpsConn2 = null;
        try {
            myURL2 = new URL(url2);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }*/

