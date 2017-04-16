package com.example.hebeiagriecomap.dbhelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hebeiagriecomap.listview.bean.BaseArea;
import com.example.hebeiagriecomap.listview.bean.BaseIndex;
import com.example.hebeiagriecomap.listview.bean.BaseState;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private static volatile DBManager instance;

    private SQLiteDatabase db;

    public static DBManager getInstance() {
        if (instance == null) {
            synchronized (DBManager.class) {
                if (instance == null)
                    instance = new DBManager();
            }
        }
        return instance;
    }

    /**
     * 获取数据库操作对象
     * @param context
     * @return
     */
    private SQLiteDatabase getDb(Context context) {
        File dataFile = new File(DBHelper.DB_PATH + DBHelper.DB_NAME);
        if (!dataFile.exists()) {
            DBHelper.getInstance(context).copyLocalDBtoSD();
            return null;
        }
        return SQLiteDatabase.openOrCreateDatabase(dataFile,null);
    }

    /**
     * 获取所有ptype为"乡镇指标"的数据，不知道有啥用（原来的代码，暂留）
     * @param context
     * @return
     */
    public List<BaseIndex> getIndexTown(Context context) {
        db = getDb(context);
        String sql2 = "select name from base_index where ptype = '乡镇指标'";
        Cursor cursor2 = db.rawQuery(sql2, null);
        List<BaseIndex> index1 = new ArrayList<>();
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

    /**
     * 获取所有ptype为"县域指标"的数据，不知道有啥用（原来的代码，暂留）
     * @param context
     * @return
     */
    public List<BaseIndex> getIndexCounty(Context context) {
        db = getDb(context);
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

    /**
     * 相当于返回了整张base_stat表的数据（不知道要干嘛的）
     * @param context
     * @return
     */
    public List<BaseState> getBaseStateYear(Context context) {
        db = getDb(context);
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

    /**
     *  old code
     * @param context
     * @return
     */
    public List<BaseArea> getBaseAreaTown(Context context) {
        db = getDb(context);
        String sql2 = "select name from base_area where ptype = '乡镇'";
        Cursor cursor2 = db.rawQuery(sql2, null);
        List<BaseArea> areas2 = new ArrayList<>();
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

    /**
     * old code
     * @param context
     * @return
     */
    public List<BaseArea> getBaseAreaCounty(Context context) {
        db = getDb(context);
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

    //对数据库blob操作
    public byte[] GetDataItem(Context context) {
        db = getDb(context);
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

}
