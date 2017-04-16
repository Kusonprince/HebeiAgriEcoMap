package com.example.hebeiagriecomap.listview.bean;

import java.sql.Blob;

/**
 * Created by yyutter on 2017/3/19.
 */

public class BaseArea {

    public String id;
    public String code;
    public String name;
    public String ptype;
    public String pstate;
    public Blob data;

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getCode(){
        return code;
    }
    public void setCode(String code){
        this.code = code;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getPtype(){
        return ptype;
    }
    public void setPtype(String ptype){
        this.ptype = ptype;
    }
    public String getPstate(){
        return pstate;
    }
    public void setPstate(String pstate){
        this.pstate = pstate;
    }
    public Blob getData(){
        return data;
    }
    public void setData(Blob data){
        this.data = data;
    }
   /* @Override
    public String toString() {
        return "[id=" + id + ", name=" + name + ",code=" + code + "ptype=" + ptype +
                "pstate=" + pstate + "]";
    }*/
}
