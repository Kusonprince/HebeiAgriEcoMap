package com.example.hebeiagriecomap.listview.database;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hebeiagriecomap.listview.bean.BaseArea;
import com.example.hebeiagriecomap.listview.bean.BaseIndex;
import com.example.hebeiagriecomap.listview.bean.BaseState;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.R.id.input;

/**
 * Created by yyutter on 2017/3/18.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private final String DB_PATH = android.os.Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/geodata";
    private static final String DB_NAME = "hebei_economic.db";
    SQLiteDatabase db;
    private static final int version = 1; //数据库版本
    private Context mContext;

    public MyDatabaseHelper(Context context) {
        super(context, DB_NAME, null, version);
        mContext = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    //对数据库blob操作
    public byte[] GetDataItem(String ID) {
        db = openDatabase();
        byte [] dbData = null;
        String sql1 = "select data from base_area";
        Cursor cursor1 = db.rawQuery(sql1,null);
        if(cursor1 != null){
            while (cursor1.moveToNext()){
                dbData = cursor1.getBlob(5);
            }
        }
        cursor1.close();
        db.close();
        return dbData;

    }
    public List<BaseArea> getBaseAreaCounty() {
        db = openDatabase();
        String sql1 = "select name from base_area where ptype = '县'";
        Cursor cursor1 = db.rawQuery(sql1, null);
        List<BaseArea> areas1 = new ArrayList<BaseArea>();
        if (cursor1 != null) {
            while (cursor1.moveToNext()) {
                BaseArea baseArea = new BaseArea();
                baseArea.setName(cursor1.getString(0));
                areas1.add(baseArea);
            }
        }
        cursor1.close();
        db.close();
        return areas1;
    }
    public List<BaseArea> getBaseAreaTown() {
        db = openDatabase();
        String sql2 = "select name from base_area where ptype = '乡镇'";
        Cursor cursor2 = db.rawQuery(sql2, null);
        List<BaseArea> areas2 = new ArrayList<BaseArea>();
        if (cursor2 != null) {
            while (cursor2.moveToNext()) {
                BaseArea baseArea = new BaseArea();
                baseArea.setName(cursor2.getString(0));
                areas2.add(baseArea);
            }
        }
        cursor2.close();
        db.close();
        return areas2;
    }
    public List<BaseState> getBaseStateYear() {
        db = openDatabase();
        String sql2 = "select yearcode from base_stat";
        Cursor cursor2 = db.rawQuery(sql2, null);
        List<BaseState> areas2 = new ArrayList<BaseState>();
        if (cursor2 != null) {
            while (cursor2.moveToNext()) {
                BaseState baseState = new BaseState();
                baseState.setYearcode(cursor2.getString(0));
                areas2.add(baseState);
            }
        }
        cursor2.close();
        db.close();
        return areas2;
    }
    public List<BaseIndex> getIndexCounty() {
        db = openDatabase();
        String sql2 = "select name from base_index where ptype = '县域指标'";
        Cursor cursor2 = db.rawQuery(sql2, null);
        List<BaseIndex> index1 = new ArrayList<BaseIndex>();
        if (cursor2 != null) {
            while (cursor2.moveToNext()) {
                BaseIndex baseIndex = new BaseIndex();
                baseIndex.setName(cursor2.getString(0));
                index1.add(baseIndex);
            }
        }
        cursor2.close();
        db.close();
        return index1;
    }
    public List<BaseIndex> getIndexTown() {
        db = openDatabase();
        String sql2 = "select name from base_index where ptype = '乡镇指标'";
        Cursor cursor2 = db.rawQuery(sql2, null);
        List<BaseIndex> index1 = new ArrayList<BaseIndex>();
        if (cursor2 != null) {
            while (cursor2.moveToNext()) {
                BaseIndex baseIndex = new BaseIndex();
                baseIndex.setName(cursor2.getString(0));
                index1.add(baseIndex);
            }
        }
        cursor2.close();
        db.close();
        return index1;
    }


    /*private SQLiteDatabase openDatabase(){
        try {
            String databaseFilename = DB_PATH + "/" + DB_NAME;
            SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(
                    databaseFilename, null);
            return database;
        } catch (Exception e) {
        }
        return null;
    }*/

   private SQLiteDatabase openDatabase() {
       String path = DB_PATH + DB_NAME;
       File gpsPath = new File(path);
       if (!gpsPath.exists()){
           try{
               FileOutputStream outputStream = new FileOutputStream(path);
               InputStream inputStream = mContext.getAssets().open(DB_NAME);
               byte[] buffer = new byte[1024];
               int readBytes = 0;
               while ((readBytes = inputStream.read(buffer)) != -1)
                   outputStream.write(buffer, 0, readBytes);
               inputStream.close();
               outputStream.close();
           }catch (Exception e){
                e.printStackTrace();
           }finally {

           }
       }
       db = SQLiteDatabase.openOrCreateDatabase(gpsPath,null);
       return  db;
   }


}
