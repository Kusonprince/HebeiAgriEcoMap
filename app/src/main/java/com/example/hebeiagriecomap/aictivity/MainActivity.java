package com.example.hebeiagriecomap.aictivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hebeiagriecomap.PGISEventHandler;
import com.example.hebeiagriecomap.R;
import com.example.hebeiagriecomap.dbhelper.DBHelper;
import com.example.hebeiagriecomap.utils.ToastUtil;

import cn.geobeans.android.MapActivity;
import cn.geobeans.android.MapView;
import cn.geobeans.android.cache.TileCache;
import cn.geobeans.app.location.Compass;
import cn.geobeans.common.GeoPoint;
import cn.geobeans.common.MapPosition;
import cn.geobeans.common.MercatorProjection;
import cn.geobeans.common.Point;
import cn.geobeans.layers.JeoVectorLayer;
import cn.geobeans.layers.tile.bitmap.BitmapTileLayer;
import cn.geobeans.layers.tile.vector.VectorTileLayer;
import cn.geobeans.map.Layers;
import cn.geobeans.map.Map;
import cn.geobeans.map.MapType;
import cn.geobeans.map.ViewController;
import cn.geobeans.osmdroid.overlays.ItemizedOverlayWithBubble;
import cn.geobeans.tiling.TileSource;
import cn.geobeans.tiling.source.bitmap.GBitmapTileFactory;

/**
 *
 */

public class MainActivity extends MapActivity implements View.OnTouchListener{

    VectorTileLayer mBaseLayer;
    TileSource mTileSource;
    private Compass mCompass;
    private PGISEventHandler handler;

    ItemizedOverlayWithBubble poiMarkers;
    private TileCache mCache;
    final static boolean USE_CACHE = true;

    BitmapTileLayer mLayer;

    JeoVectorLayer jeo=null;
    private ViewController mViewport;
    private GestureDetector mGestureDetector;
    private TextView mTapTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGestureDetector = new GestureDetector(new SingleTabListener());
        MapView mMapView = (MapView) findViewById(R.id.main_mapView);
        mMapView.setOnTouchListener(this);
        mMapView.setFocusable(true);
        mMapView.setClickable(true);
        mMapView.setLongClickable(true);

        mTapTextView = (TextView) findViewById(R.id.tap_text);

        registerMapView(mMapView);
        MercatorProjection.setType(MapType.TDMAP);
        mTileSource = GBitmapTileFactory.createBMTileSource("http://t0.tianditu.cn/vec_c/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=vec&STYLE=default&TILEMATRIXSET=c&FORMAT=tiles",1,18);
        if (USE_CACHE)
        {
            //mCache = new TileCache(this, null, "tile.db");
            //mCache = new TileCache(this, null, Environment.getExternalStorageDirectory()+"/geobeansdata/data.db");
            mCache = new TileCache(this, null, Environment.getExternalStorageDirectory()+"/geobeansdata/tdt.db");

            mCache.setCacheSize(512 * (1 << 10));
            mTileSource.setCache(mCache);
        }

        mLayer = new BitmapTileLayer(mMap, mTileSource);
        mMap.layers().add(mLayer);
        TileSource mTileSource2 = GBitmapTileFactory.createBMTileSource("http://t1.tianditu.cn/cva_c/wmts?service=wmts&request=GetTile&version=1.0.0&LAYER=cva&tileMatrixSet=c&format=tiles",3,18);
        BitmapTileLayer mLayer2 = new BitmapTileLayer(mMap, mTileSource2);
        mMap.layers().add(mLayer2);


        MapPosition pos = new MapPosition();
        double centerlon =117.224117;
        double centerlat=39.097668;
        //  mMap.setMapPosition(centerlat,centerlon,1<<14);
        //mMap.setMapPosition( 37.02, 115.36, Math.pow(2, 13));

        mMap.setMapPosition(centerlat,centerlon,1<<9);
        Map m = this.map();
        Layers layers = mMap.layers();

        ImageView comp= (ImageView)findViewById(R.id.main_compass);
        mCompass = new Compass(this, mMap,comp);
        mMap.layers().add(mCompass);

        handler=new PGISEventHandler(m,this);
        mMapView.initGestureHandler(this, handler);
        mMapView.setDoubleTapListener(handler);

        Button themeButton = (Button) findViewById(R.id.map_theme);
        themeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this,ThemeActivity.class);
                startActivity(intent1);
            }
        });
        Button queryButton = (Button) findViewById(R.id.map_query);
        queryButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent2 = new Intent(MainActivity.this, QueryActivity.class);
                startActivity(intent2);
            }
        });
        Button queryButton2 = (Button) findViewById(R.id.list_query);
        queryButton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (DBHelper.getInstance(MainActivity.this).getIsCopyRunning()) {
                    ToastUtil.show(MainActivity.this, "数据正在拉取中，请稍候...");
                    return;
                }
                Intent intent3 = new Intent(MainActivity.this, ListViewActivity.class);
                startActivity(intent3);
            }
        });

    }

    public boolean onTouch(View v, MotionEvent event) {

        return mGestureDetector.onTouchEvent(event);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    protected void onPause()
    {
        super.onPause();
        mCompass.pause();
    }

    protected void onResume()
    {
        super.onResume();
        mCompass.resume();
    }

    /* @Override
     public boolean onItemLongPress(int arg0, Object arg1) {
         // TODO Auto-generated method stub
         return false;
     }

     @Override
     public boolean onItemSingleTapUp(int arg0, Object obj)
     {
         Toast toast =null;
         if(obj instanceof MarkerItem)
         {
             MarkerItem item=(MarkerItem)obj;
             toast=Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT);
         }
         else if(obj instanceof GPathLayer)
         {
             toast=Toast.makeText(this, "line is clicked", Toast.LENGTH_SHORT);
         }
         else if(obj instanceof GPolygonLayer)
         {
             toast=Toast.makeText(this, "polygon is clicked", Toast.LENGTH_SHORT);
         }
         toast.show();
         return false;
     }*/
    private int mMapMode = 0;

    public void toggleLocation(View V)
    {
        if(mMapMode==0)
        {
            mMapMode=1;
            mMap.viewport().setTilt(45);
        }
        else
        {
            mMapMode=0;
            mMap.viewport().setTilt(0);
        }
        mMap.updateMap(true);
    }

    /**
     * Created by yyutter on 2017/3/27.
     */
    private class SingleTabListener extends GestureDetector.SimpleOnGestureListener {

        public boolean onSingleTapUp(MotionEvent e) {
            int x = (int) e.getX();
            int y = (int) e.getY();

            mViewport = mMap.viewport();
            GeoPoint gp=mViewport.fromScreenPoint(x, y);
            Log.i("MyGesture", "onSingleTapUp");
            String PATH = Environment.getExternalStorageDirectory().getPath() + "/geodata/hebei_county2.json";

            //获取整个坐标的方法Toast.makeText(MainActivity.this, "坐标：" + gp, Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, "经度：" + gp.getLatitude() + ",纬度： " + gp.getLongitude() + "保定市", Toast.LENGTH_SHORT).show();

            //Gson gson = new Gson();

            return true;
        }

        public boolean isInPolygon(Point point, Point[] points, int n) {
            int nCross = 0;
            for (int i = 0; i < n; i++) {
                Point p1 = points[i];
                Point p2 = points[(i + 1) % n];//取余
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
        }

    }

}
