package com.example.hebeiagriecomap.dbhelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by yyutter on 2017/3/18.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static volatile DBHelper instance;
    public static final String DB_PATH = android.os.Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/geodata";
    public static final String DB_NAME = "hebei_economic.db";
    private boolean isCopyRunning = false;  //标记复制数据库到本地sd卡是否正在进行
    private static final int version = 1; //数据库版本
    private Context mContext;

    /**
     * 单例模式获取dbhelper
     * @param context
     * @return
     */
    public static DBHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (DBHelper.class) {
                if (instance == null)
                    instance = new DBHelper(context);
            }
        }
        return instance;
    }

    public DBHelper(Context context) {
        super(context, DB_NAME, null, version);
        mContext = context;

    }

    public boolean getIsCopyRunning() {
        return isCopyRunning;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //操作本地数据库，无需创建数据库

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //数据库更新

    }

    /**
     * 复制assets文件下的本地数据库到sd卡下
     */
   public void copyLocalDBtoSD() {
       String path = DB_PATH + DB_NAME;
       File gpsPath = new File(path);
       if (!gpsPath.exists() && !isCopyRunning){
           try{
               isCopyRunning = true;
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
                isCopyRunning = false;
           }
       }
   }

}
