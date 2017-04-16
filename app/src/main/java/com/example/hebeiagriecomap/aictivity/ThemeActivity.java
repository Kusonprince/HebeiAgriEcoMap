package com.example.hebeiagriecomap.aictivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hebeiagriecomap.PGISEventHandler;
import com.example.hebeiagriecomap.R;
import com.example.hebeiagriecomap.query.QueryActivity;

import org.jeo.data.Cursor;
import org.jeo.data.VectorDataset;
import org.jeo.data.mem.MemVector;
import org.jeo.data.mem.MemWorkspace;
import org.jeo.feature.Feature;
import org.jeo.feature.Schema;
import org.jeo.feature.SchemaBuilder;
import org.jeo.geojson.GeoJSONReader;
import org.jeo.map.RGB;
import org.jeo.map.Style;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cn.geobeans.android.MapActivity;
import cn.geobeans.android.MapView;
import cn.geobeans.android.cache.TileCache;
import cn.geobeans.android.canvas.AndroidBitmap;
import cn.geobeans.app.location.Compass;
import cn.geobeans.backend.canvas.Bitmap;
import cn.geobeans.common.GeoPoint;
import cn.geobeans.common.MapPosition;
import cn.geobeans.common.MercatorProjection;
import cn.geobeans.common.Point;
import cn.geobeans.layers.GPathLayer;
import cn.geobeans.layers.GPolygonLayer;
import cn.geobeans.layers.ItemizedLayer;
import cn.geobeans.layers.JeoVectorLayer;
import cn.geobeans.layers.MarkerItem;
import cn.geobeans.layers.MarkerSymbol;
import cn.geobeans.layers.OnItemGestureListener;
import cn.geobeans.layers.tile.bitmap.BitmapTileLayer;
import cn.geobeans.layers.tile.vector.VectorTileLayer;
import cn.geobeans.map.Layers;
import cn.geobeans.map.Map;
import cn.geobeans.map.MapType;
import cn.geobeans.map.ViewController;
import cn.geobeans.osmdroid.overlays.ItemizedOverlayWithBubble;
import cn.geobeans.tiling.TileSource;
import cn.geobeans.tiling.source.bitmap.GBitmapTileFactory;
import cn.geobeans.utils.ThemeUtil;

//import cn.geobeans.utils.ThemeUtil;

public class ThemeActivity extends MapActivity implements OnItemGestureListener
{
    MapView mMapView;
    VectorTileLayer mBaseLayer;
    TileSource mTileSource;
    private Compass mCompass;
    private PGISEventHandler handler;

    ItemizedOverlayWithBubble poiMarkers;
    private TileCache mCache;
    final static boolean USE_CACHE = true;

    BitmapTileLayer mLayer;

    JeoVectorLayer jeo=null;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        mMapView = (MapView) findViewById(R.id.theme_mapView);
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

        ImageView comp= (ImageView)findViewById(R.id.compass);
        mCompass = new Compass(this, mMap,comp);
        mMap.layers().add(mCompass);

        handler=new PGISEventHandler(m,this);
        mMapView.initGestureHandler(this, handler);
        mMapView.setDoubleTapListener(handler);


        Button singleThemeBut=(Button)findViewById(R.id.singleTheme);
        singleThemeBut.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mMap.addTask(new Runnable()
                {
                    @Override
                    public void run() {
                        try {
                            String PATH = Environment.getExternalStorageDirectory().getPath()+"/geodata/hebei_county2.json";
                            InputStream is = new FileInputStream(PATH);

                            //VectorDataset data = JeoTest.readGeoJson(is);
                            VectorDataset data = ThemeUtil.readGeoJson(new InputStreamReader(is,"utf-8"));
                            Style style = ThemeUtil.getStyle(1,"#44111111");;
                            jeo=new JeoVectorLayer(mMap, data, style);
                            mMap.layers().add(jeo);
                            mMap.updateMap(true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                //mMap.shoot(QuadActivity.this, "sdcard/ScreenShot.png");
            }
        });

        Button testBut=(Button)findViewById(R.id.randTheme);
        testBut.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v)
            {
                mMap.addTask(new Runnable() {
                    @Override
                    public void run()
                    {
                        String PATH = Environment.getExternalStorageDirectory().getPath()+"/geodata/hebei_county2.json";
                        try
                        {
                            MemVector[] memData=new MemVector[6];
                            MemWorkspace mem = new MemWorkspace();
                            Schema s = new SchemaBuilder("way").schema();
                            for(int i=0;i<6;i++)
                            {
                                memData[i]=new MemVector(s);
                            }

                            GeoJSONReader r = new GeoJSONReader();
                            Cursor<Feature> features=r.features(new File(PATH));
                            for (Feature f : features)
                            {
                                int p=(int)(Math.random()*6);
                                memData[p].add(f);
                            }
                            String[] colors=new String[6];
                            colors[0]="#44ff0000";
                            colors[1]="#4400ff00";
                            colors[2]="#440000ff";
                            colors[3]="#ffff00ff";
                            colors[4]="#4422ff22";
                            colors[5]="#ffffff00";

                            for(int i=0;i<6;i++)
                            {
                                Style style = ThemeUtil.getStyle(1,colors[i]);
                                mMap.layers().add(new JeoVectorLayer(mMap, memData[i], style));
                            }
                            mMap.updateMap(true);
                            Toast.makeText(ThemeActivity.this, "Features：" + features, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                });

            }
        });

        Button testBut3=(Button)findViewById(R.id.gradeTheme);
        testBut3.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v)
            {
                mMap.addTask(new Runnable() {
                    @Override
                    public void run()
                    {
                        try
                        {
                            MemVector[] memData=new MemVector[6];
                            MemWorkspace mem = new MemWorkspace();
                            Schema s = new SchemaBuilder("way").schema();
                            for(int i=0;i<6;i++)
                            {
                                memData[i]=new MemVector(s);
                            }

                            GeoJSONReader r = new GeoJSONReader();
                            String PATH = Environment.getExternalStorageDirectory().getPath()+"/geodata/hebei_county2.json";

                            Cursor<Feature> features=r.features(new File(PATH));

                            for (Feature f : features)
                            {
                                int p=(int)(Math.random()*6);
                                memData[p].add(f);
                            }

                            RGB fromClr=new RGB(50,100,10,33);
                            RGB toClr=new RGB(200,150,150,60);
                            List<RGB> cols = fromClr.interpolate(toClr, 6, org.jeo.util.Interpolate.Method.LINEAR);
                            String[] colors=new String[6];
                            for(int i=0;i<6;i++)
                            {
                                colors[i]=cols.get(i).rgbahex();
                            }

                            for(int i=0;i<6;i++)
                            {
                                Style style = ThemeUtil.getStyle(1,colors[i]);
                                mMap.layers().add(new JeoVectorLayer(mMap, memData[i], style));
                            }

                            //mMap.layers().add(new JeoVectorLayer(mMap, , style));

                            mMap.updateMap(true);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


        Button testCaiMark=(Button)findViewById(R.id.barTheme);
        testCaiMark.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                addBarChart();
            }
        });

        Button testCaiLine=(Button)findViewById(R.id.pieTheme);
        testCaiLine.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v)
            {
                addPieChart();
            }
        });


        Button testCaiPoly=(Button)findViewById(R.id.netTheme);
        testCaiPoly.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                addLineChart();
            }
        });

    }

    private void addPieChart()
    {
        String[] code = new String[] { "Froyo", "Gingerbread",
                "IceCream Sandwich", "Jelly Bean", "KitKat" };

        // Pie Chart Section Value
        double[] distribution = { 0.5, 9.1, 7.8, 45.5, 33.9 };

        // Color of each Pie Chart Sections
        int[] colors = { Color.BLUE, Color.MAGENTA, Color.GREEN, Color.CYAN,
                Color.RED };
        Bitmap bitmap = new AndroidBitmap(ThemeUtil.getPieChart(300, 300,code,distribution,colors));
        //drawableToBitmap(getResources(), R.drawable.marker_poi);
        MarkerSymbol symbol= new MarkerSymbol(bitmap, 0.5f,0.5f);
        ItemizedLayer markerLayer = new ItemizedLayer(mMap, new ArrayList<MarkerItem>(),
                symbol, this);
        mMap.layers().add(markerLayer);
        List<MarkerItem> pts = new ArrayList<MarkerItem>();
        double lat=39.097668;
        double lon=117.224117;
        pts.add(new MarkerItem(lat + "/" + lon, "",  new GeoPoint(lat, lon)));
        markerLayer.addItems(pts);
        mMap.render();
    }
    private void addBarChart()
    {
        String[] chartTitle=new String[]{"Total", "xTitle","yTitle"};
        String[] xTextLabel = new String[] { "Jan", "Feb", "Mar", "Apr", "May",
                "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
        int[] x = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
        int[] income = { 2000, 2500, 2700, 3000, 2800, 3500, 3700, 3800, 0, 0,
                0, 0 };
        int[] expense = { 2200, 2700, 2900, 2800, 2600, 3000, 3300, 3400, 0, 0,
                0, 0 };
        Bitmap bitmap = new AndroidBitmap(ThemeUtil.getBarChart(600, 600,chartTitle,xTextLabel,x,income,expense));
        //drawableToBitmap(getResources(), R.drawable.marker_poi);
        MarkerSymbol symbol= new MarkerSymbol(bitmap, 0.5f,0.5f);
        ItemizedLayer markerLayer = new ItemizedLayer(mMap, new ArrayList<MarkerItem>(),
                symbol, this);
        mMap.layers().add(markerLayer);
        List<MarkerItem> pts = new ArrayList<MarkerItem>();
        double lat=39.127668;
        double lon=117.224117;
        pts.add(new MarkerItem(lat + "/" + lon, "",  new GeoPoint(lat, lon)));
        markerLayer.addItems(pts);
        mMap.render();
    }

    private void addLineChart()
    {
        List<String> values=new ArrayList<String>();
        values.add(""+5.0);
        values.add(""+12.0);
        values.add(""+7.0);
        values.add(""+3.0);
        values.add(""+9.0);
        values.add(""+27.0);
        values.add(""+33.0);
        values.add(""+20.0);

        String[] title=new String[]{"作物产量"};
        int[] clr=new int[]{Color.GREEN};
        Bitmap bitmap = new AndroidBitmap(ThemeUtil.getLineChart(300, 300,title,clr,values,"时间","小麦产量"));
        //drawableToBitmap(getResources(), R.drawable.marker_poi);
        MarkerSymbol symbol= new MarkerSymbol(bitmap, 0.5f,0.5f);
        ItemizedLayer markerLayer = new ItemizedLayer(mMap, new ArrayList<MarkerItem>(),
                symbol, this);
        mMap.layers().add(markerLayer);
        List<MarkerItem> pts = new ArrayList<MarkerItem>();
        double lat=39.197668;
        double lon=117.264117;
        pts.add(new MarkerItem(lat + "/" + lon, "",  new GeoPoint(lat, lon)));
        markerLayer.addItems(pts);
        mMap.render();
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

    @Override
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
    }

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

}


