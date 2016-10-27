package client.ediancha.com.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.adapter.OtherStoreAdapter;
import client.ediancha.com.adapter.TeaOtherRecommendAdapter;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.OtherStore;
import client.ediancha.com.entity.TeaSpaceDesc;

/**
 * Created by dengmingzhi on 2016/10/27.
 */

public class OtherStoreActivity extends ToolBarActivity {
    private RecyclerView rv_list;
    @Override
    protected String getToolBarTitle() {
        return "门店列表";
    }

    @Override
    protected void initView() {
        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_list.setLayoutManager(layoutManager);
        rv_list.addItemDecoration(new ItemDecoration(this, LinearLayoutManager.VERTICAL, 2, "#ebebeb"));
        rv_list.setAdapter(new OtherStoreAdapter(this, (List<OtherStore>) getIntent().getSerializableExtra("store")));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getRid() {
        return R.layout.activity_recycleview;
    }
}
