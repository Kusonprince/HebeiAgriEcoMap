package com.example.hebeiagriecomap.listview.dao;

import android.database.sqlite.SQLiteDatabase;

import com.example.hebeiagriecomap.listview.database.MyDatabaseHelper;

/**
 * Created by yyutter on 2017/4/5.
 */

public class DBService {

    private MyDatabaseHelper myDatabaseHelper;
    private SQLiteDatabase db;

    public void query(){
        db = myDatabaseHelper.getReadableDatabase();
    }
}
