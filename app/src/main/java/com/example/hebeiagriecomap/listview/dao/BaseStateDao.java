package com.example.hebeiagriecomap.listview.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hebeiagriecomap.listview.bean.BaseState;
import com.example.hebeiagriecomap.listview.database.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyutter on 2017/3/23.
 */

public class BaseStateDao {
    private MyDatabaseHelper myDatabaseHelper;

    public BaseStateDao(Context context) {
        myDatabaseHelper = new MyDatabaseHelper(context);
    }

    /**
     * 返回所有的Base_area用户信息
     */
    public List<BaseState> find_Area() {
        String sql2= "select id,indexcode,areacode,yearcode,statdata from hebei_economic.db";
        List<BaseState> states = new ArrayList<BaseState>();
        SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql2, null);
        while (cursor.moveToNext()) {
            BaseState baseState = new BaseState();
            baseState.setId(cursor.getString(0));
            baseState.setIndexcode(cursor.getString(1));
            baseState.setAreacode(cursor.getString(2));
            baseState.setYearcode(cursor.getString(3));
            baseState.setStatdata(cursor.getString(4));
            states.add(baseState);
        }
        cursor.close();
        db.close();
        return states;
    }
}
