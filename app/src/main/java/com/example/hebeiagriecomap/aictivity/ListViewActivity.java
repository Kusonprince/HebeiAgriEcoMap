package com.example.hebeiagriecomap.aictivity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hebeiagriecomap.R;
import com.example.hebeiagriecomap.adapter.SimpleTreeAdapter;
import com.example.hebeiagriecomap.adapter.TreeListViewAdapter;
import com.example.hebeiagriecomap.listview.basic.Node;
import com.example.hebeiagriecomap.utils.InitDBDataUtil;
import com.example.hebeiagriecomap.utils.ThreadPoolProxyFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhangke on 2017-1-15.
 */
public class ListViewActivity extends BaseActivity {
    //int num = 0;
    private List<Node> mDatas = new ArrayList<>();
    private TreeListViewAdapter mAdapter;
    private ListView mTree;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_demo);
        RecyclerView mRecyTree = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyTree.setVisibility(View.GONE);
        mTree = (ListView) findViewById(R.id.lv_tree);
        initData();
        //第一个参数  ListView
        //第二个参数  上下文
        //第三个参数  数据集
        //第四个参数  默认展开层级数 0为不展开
        //第五个参数  展开的图标
        //第六个参数  闭合的图标
        mAdapter = new SimpleTreeAdapter(mTree, ListViewActivity.this,
                mDatas, 1,R.mipmap.tree_ex,R.mipmap.tree_ec);

        mTree.setAdapter(mAdapter);//这一行放置在上面，会导致对话框不显示
    }

    private void initData() {
        showProgressDialog("数据正在拉取中，请稍候");
        ThreadPoolProxyFactory.getLongThreadPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
                mDatas = InitDBDataUtil.initDatas(ListViewActivity.this);
                ListViewActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismissProgressDialog();
                        mAdapter.addData(mDatas);
                    }
                });
            }
        });
    }

    public void clickShow(View v){
        StringBuilder sb = new StringBuilder();
        final List<Node> allNodes = mAdapter.getAllNodes();
        for (int i = 0; i < allNodes.size(); i++) {
            if (allNodes.get(i).isChecked()){
                sb.append(allNodes.get(i).getName()+",");
            }
        }
        String strNodesName = sb.toString();
        if (!TextUtils.isEmpty(strNodesName))
            Toast.makeText(this, strNodesName.substring(0, strNodesName.length()-1),Toast.LENGTH_SHORT).show();
    }


}
