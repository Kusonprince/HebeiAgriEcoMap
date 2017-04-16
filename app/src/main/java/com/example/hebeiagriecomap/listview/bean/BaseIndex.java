package com.example.hebeiagriecomap.listview.bean;

/**
 * Created by yyutter on 2017/3/19.
 */

public class BaseIndex {
    public String id;
    public String code;
    public String name;
    public String puint;
    public String ptype;
    public String rcstate;

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
    public String getPuint(){
        return puint;
    }
    public void setPuint(String puint){
        this.puint = puint;
    }
    public String getPtype(){
        return ptype;
    }
    public void setPtype(String ptype){
        this.ptype = ptype;
    }
    public String getRcstate(){
        return rcstate;
    }
    public void setRcstate(String rcstate){
        this.rcstate = rcstate;
    }
}
