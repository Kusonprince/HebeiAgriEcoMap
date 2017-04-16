package com.example.hebeiagriecomap.listview.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hebeiagriecomap.listview.bean.BaseIndex;
import com.example.hebeiagriecomap.listview.database.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyutter on 2017/3/23.
 */

public class BaseIndexDao {

    private MyDatabaseHelper myDatabaseHelper;

    public BaseIndexDao(Context context) {
        myDatabaseHelper = new MyDatabaseHelper(context);
    }

    /**
     * 返回所有的Base_area用户信息
     */
    public List<BaseIndex> find_Area() {
        String sql2= "select code,name,puint,ptype,rcstate from hebei_economic.db";
        List<BaseIndex> indexs = new ArrayList<BaseIndex>();
        SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql2, null);
        while (cursor.moveToNext()) {
            BaseIndex baseIndex = new BaseIndex();
            baseIndex.setCode(cursor.getString(0));
            baseIndex.setName(cursor.getString(1));
            baseIndex.setPuint(cursor.getString(2));
            baseIndex.setPtype(cursor.getString(3));
            baseIndex.setRcstate(cursor.getString(4));
            indexs.add(baseIndex);
        }
        cursor.close();
        db.close();
        return indexs;
    }
}
