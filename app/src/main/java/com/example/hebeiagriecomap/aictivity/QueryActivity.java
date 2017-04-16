package com.example.hebeiagriecomap.aictivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.example.hebeiagriecomap.R;
import com.example.hebeiagriecomap.adapter.SimpleTreeRecyclerAdapter;
import com.example.hebeiagriecomap.adapter.TreeRecyclerAdapter;
import com.example.hebeiagriecomap.listview.basic.Node;
import com.example.hebeiagriecomap.utils.InitDBDataUtil;
import com.example.hebeiagriecomap.utils.ThreadPoolProxyFactory;

import java.util.ArrayList;
import java.util.List;

public class QueryActivity extends BaseActivity {
    private List<Node> mDatas = new ArrayList<>();
    private TreeRecyclerAdapter mAdapter;
    //int num = 0;方便后面添加数据使用
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_demo);

        ListView mListTree = (ListView) findViewById(R.id.lv_tree);
        mListTree.setVisibility(View.GONE);
        RecyclerView mTree = (RecyclerView) findViewById(R.id.recyclerview);
        mTree.setLayoutManager(new LinearLayoutManager(this));
        initData();
        //备注：第一个参数  RecyclerView
        //第二个参数  上下文
        //第三个参数  数据集
        //第四个参数  默认展开层级数 0为不展开
        //第五个参数  展开的图标
        //第六个参数  闭合的图标
        mAdapter = new SimpleTreeRecyclerAdapter(mTree, QueryActivity.this,
                mDatas, 1,R.mipmap.tree_ex,R.mipmap.tree_ec);//从BaseActivity传递mDatas
        mTree.setAdapter(mAdapter);
    }

    private void initData() {
        showProgressDialog("数据正在拉取中，请稍候");
        ThreadPoolProxyFactory.getLongThreadPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
                InitDBDataUtil.initDatas(QueryActivity.this);
                QueryActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgressDialog();
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

}
