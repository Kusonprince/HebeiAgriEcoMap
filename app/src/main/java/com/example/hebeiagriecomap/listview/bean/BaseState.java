package com.example.hebeiagriecomap.listview.bean;

/**
 * Created by yyutter on 2017/3/19.
 */

public class BaseState {

    public String id;
    public String indexcode;
    public String areacode;
    public String yearcode;
    public String statdata;
    public String rcstate;

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getIndexcode(){
        return indexcode;
    }
    public void setIndexcode(String indexcode){
        this.indexcode = indexcode;
    }
    public String getAreacode(){
        return areacode;
    }
    public void setAreacode(String areacode){
        this.areacode = areacode;
    }
    public String getYearcode(){
        return yearcode;
    }
    public void setYearcode(String yearcode){
        this.yearcode = yearcode;
    }
    public String getStatdata(){
        return statdata;
    }
    public void setStatdata(String statdata){
        this.statdata = statdata;
    }
    public String getRcstate(){
        return rcstate;
    }
    public void setRcstate(String rcstate){
        this.rcstate = rcstate;
    }
}
