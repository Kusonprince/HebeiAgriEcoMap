package com.example.hebeiagriecomap.listview.basic;

import java.io.Serializable;

/**
 * @author oywf on 2016/6/28.
 */
public class ItemBean implements Serializable {

    private String name;
    private int id;
    private String storageId;
    private String storageName;

    /**
     * 用户是否已存在，0 是存在。1是不存在
     */
    private int exist = 1;
    public String getStorageId(){
        return storageId;
    }
    public void setStorageId(String storageId){
        this.storageId = storageId;
    }
    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExist() {
        return exist;
    }

    public void setExist(int exist) {
        this.exist = exist;
    }


    @Override
    public String toString() {
        return "ItemBean{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ",storageId=" + storageId+
                ",storageName=" + storageName+
                ", exist=" + exist +
                '}';
    }
}
