package com.example.hebeiagriecomap.listview.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hebeiagriecomap.listview.bean.BaseArea;
import com.example.hebeiagriecomap.dbhelper.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyutter on 2017/3/23.
 */

public class BaseAreaDao {
    private DBHelper myDatabaseHelper;
    public BaseAreaDao(Context context){
        myDatabaseHelper = new DBHelper(context);
    }

    private final String DATABASE_PATH = android.os.Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/geodata/hebei_economic.db";


    SQLiteDatabase db;
    public List<BaseArea> getBaseAreaList(){
        List<BaseArea> baseAreaList = new ArrayList<BaseArea>();
        try{
            db = openDatabase();
            String sql1 = "select id,code,name,ptype,pstate from base_area";
            Cursor cursor = db.rawQuery(sql1, null);
            if(null != cursor){
                while(cursor.moveToNext()){
                    String id = cursor.getString(cursor.getColumnIndex("id"));
                    String code = cursor.getString(cursor.getColumnIndex("code"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String ptype = cursor.getString(cursor.getColumnIndex("ptype"));
                    String pstate = cursor.getString(cursor.getColumnIndex("pstate"));
                    //byte[] bt = cursor.getBlob(cursor.getColumnIndex("blob"));
                    BaseArea bean = new BaseArea();
                    bean.setId(id);
                    bean.setCode(code);
                    bean.setName(name);
                    bean.setPtype(ptype);
                    bean.setPstate(pstate);
                    //bean.setData(bt);
                    baseAreaList.add(bean);
                }
            }
            cursor.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(null != db){
                db.close();
            }
        }
        return baseAreaList;
    }
    private SQLiteDatabase openDatabase() {
        try {
            // 获得dictionary.db文件的绝对路径
            String databaseFilename =DATABASE_PATH ;
            SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(
                    databaseFilename, null);
            return database;
        } catch (Exception e) {
        }
        return null;
    }

}
