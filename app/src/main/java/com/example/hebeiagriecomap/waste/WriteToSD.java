package com.example.hebeiagriecomap.waste;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class WriteToSD {
    private Context context;
    String filePath = android.os.Environment.getExternalStorageDirectory()+"/geodata";
    public WriteToSD(Context context){
        this.context = context;
        if(!isExist()){
            write();
        }
    }
    private void write(){
        InputStream inputStream;
        try {
            inputStream = context.getResources().getAssets().open("hebei_economic.db");
            File file = new File(filePath);
            if(!file.exists()){
                file.mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(filePath + "/hebei_economic.db");
            byte[] buffer = new byte[512];
            int count = 0;
            while((count = inputStream.read(buffer)) > 0){
                fileOutputStream.write(buffer, 0 ,count);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            inputStream.close();
            System.out.println("success");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean isExist(){
        File file = new File(filePath + "/hebei_economic.db");
        if(file.exists()){
            return true;
        }else{
            return false;
        }
    }
}
