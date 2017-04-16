package com.example.hebeiagriecomap.aictivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.hebeiagriecomap.listview.basic.Node;
import com.example.hebeiagriecomap.listview.bean.BaseArea;
import com.example.hebeiagriecomap.listview.bean.BaseIndex;
import com.example.hebeiagriecomap.listview.bean.BaseState;
import com.example.hebeiagriecomap.listview.database.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangke on 2017-1-15.
 */
public class BaseActivity extends AppCompatActivity {
    int x = 9;
    int num = 0;
    private MyDatabaseHelper myDatabaseHelper;
    protected List<Node> mDatas = new ArrayList<Node>();//

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDatabaseHelper = new MyDatabaseHelper(this);
        initDatas();
    }

    private void initDatas() {
        List<BaseArea> areas = myDatabaseHelper.getBaseAreaCounty();
        List<BaseArea> areast = myDatabaseHelper.getBaseAreaTown();
        List<BaseState> year = myDatabaseHelper.getBaseStateYear();
        List<BaseIndex> index = myDatabaseHelper.getIndexCounty();
        List<BaseIndex> indext = myDatabaseHelper.getIndexTown();
        mDatas.add(new Node("1", "0", "数据查询操作"));

        mDatas.add(new Node(2 + "", 1 + "", "区位选择"));
        mDatas.add(new Node(3 + "", 1 + "", "年份"));
        mDatas.add(new Node(4 + "", 1 + "", "指标体系"));

        //这里县和乡镇二级，以及县域指标，乡镇指标等可自己命名，也可通过读取base_area中的ptype表名得到，
        mDatas.add(new Node(5 + "", 2 + "", "县"));
        mDatas.add(new Node(6 + "", 2 + "", "乡镇"));

        mDatas.add(new Node(7 + "", 4 + "", "县域指标"));
        mDatas.add(new Node(8 + "", 4 + "", "乡镇指标"));

     for(int i=0;i<areas.size();i++){
            if(areas != null && areas.size()>0) {
                BaseArea[] arr = new BaseArea[areas.size()];
                for (int j = 0; j < areas.size(); j++) {
                    arr[j] = areas.get(j);
                }
            num = x+i;
                mDatas.add(new Node(num+"",5+"",arr[i].getName()));
            }
        }
        for(int i=0;i<areast.size();i++){
            if(areast != null && areast.size()>0) {
                BaseArea[] arrt = new BaseArea[areast.size()];
                for (int j = 0; j < areast.size(); j++) {
                    arrt[j] = areast.get(j);
                }
                num = x+i;
                mDatas.add(new Node(num+"",6+"",arrt[i].getName()));
            }
        }
        for(int i=0;i<year.size();i++){
            if(year != null && year.size()>0) {
                BaseState[] arrt = new BaseState[year.size()];
                for (int j = 0; j < year.size(); j++) {
                    arrt[j] = year.get(j);
                }
                num = i;
                mDatas.add(new Node(num+"",3+"",arrt[i].getYearcode()));
            }
        }
        for(int i=0;i<index.size();i++){
            if(index != null && index.size()>0) {
                BaseIndex[] arrt = new BaseIndex[index.size()];
                for (int j = 0; j < index.size(); j++) {
                    arrt[j] = index.get(j);
                }
                num = i;
                mDatas.add(new Node(num+"",7+"",arrt[i].getName()));
            }
        }
        for(int i=0;i<indext.size();i++){
            if(indext != null && indext.size()>0) {
                BaseIndex[] arrt = new BaseIndex[indext.size()];
                for (int j = 0; j < indext.size(); j++) {
                    arrt[j] = indext.get(j);
                }
                num = i;
                mDatas.add(new Node(num+"",8+"",arrt[i].getName()));
            }
        }
    }
    }



