package com.example.hebeiagriecomap.listview.dao;

import android.database.sqlite.SQLiteDatabase;

import com.example.hebeiagriecomap.dbhelper.DBHelper;

/**
 * Created by yyutter on 2017/4/5.
 */

public class DBService {

    private DBHelper myDatabaseHelper;
    private SQLiteDatabase db;

    public void query(){
        db = myDatabaseHelper.getReadableDatabase();
    }
}
