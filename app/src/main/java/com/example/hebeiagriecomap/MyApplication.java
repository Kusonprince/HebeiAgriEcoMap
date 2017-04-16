package com.example.hebeiagriecomap;

import android.app.Application;

import com.example.hebeiagriecomap.dbhelper.DBHelper;

/**
 * Created by Kuson on 17/4/16.
 */

public class MyApplication extends Application {

    private static MyApplication instance;

    public static MyApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //打开app后立刻执行复制数据库到本地
        DBHelper.getInstance(getApplicationContext()).copyLocalDBtoSD();
    }
}
