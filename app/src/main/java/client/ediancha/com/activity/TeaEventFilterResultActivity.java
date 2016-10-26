package client.ediancha.com.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.adapter.TeaEventFilterResultAdapter;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.TeaFilter;
import client.ediancha.com.fragment.TeaEventFragment;
import client.ediancha.com.fragment.TeaProductFragment;
import client.ediancha.com.fragment.TeaSpaceFragment;
import client.ediancha.com.interfaces.OnTeaEventFilterResultChangeListener;
import client.ediancha.com.interfaces.OnTeaEventMapListener;
import client.ediancha.com.util.Util;

public class TeaEventFilterResultActivity extends ToolBarActivity implements OnTeaEventFilterResultChangeListener {
    private RecyclerView rv_filter;
    private TeaEventFragment teaEventFragment;
    private TeaProductFragment teaProductFragment;
    private TeaSpaceFragment teaSpaceFragment;
    private List<TeaFilter.Cat> list;
    private int type;

    @Override
    protected String getToolBarTitle() {
        switch (type) {
            case 0:
                return "茶·活动筛选结果";
            case 1:
                return "茶·空间筛选结果";
            case 2:
                return "茶·产品筛选结果";
        }
        return "搜索结果";
    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra("type", 0);
        list = new Gson().fromJson(getIntent().getStringExtra("data"), new TypeToken<List<TeaFilter.Cat>>() {
        }.getType());
        switch (type) {
            case 0:
                teaEventFragment =TeaEventFragment.getInstance(true);
                getSupportFragmentManager().beginTransaction().replace(R.id.fg_filter, teaEventFragment).commit();
                teaEventFragment.setFilterMap(list);
                break;
            case 1:
                teaSpaceFragment = new TeaSpaceFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fg_filter, teaSpaceFragment).commit();
                teaSpaceFragment.setFilterMap(list);
                break;
            case 2:
                teaProductFragment = TeaProductFragment.getInstance(true);
                getSupportFragmentManager().beginTransaction().replace(R.id.fg_filter, teaProductFragment).commit();
                teaProductFragment.setFilterMap(list);
                break;
        }

        rv_filter = (RecyclerView) findViewById(R.id.rv_filter);

    }

    @Override
    protected void initData() {

        TeaEventFilterResultAdapter mAdapter = new TeaEventFilterResultAdapter(this, list);
        mAdapter.setOnTeaEventFilterResultChangeListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_filter.addItemDecoration(new ItemDecoration(this, LinearLayout.HORIZONTAL, Util.dp2Px(8), "#fbfbfb"));
        rv_filter.setLayoutManager(manager);
        rv_filter.setAdapter(mAdapter);

    }

    @Override
    protected int getRid() {
        return R.layout.activity_tea_event_filter_result;
    }


    @Override
    public void change(int totalSize, int position) {
        if (totalSize == 0) {
            rv_filter.setVisibility(View.GONE);
        }

        switch (type) {
            case 0:
                teaEventFragment.setFilterMap(list);
                break;
            case 1:
                teaSpaceFragment.setFilterMap(list);
                break;
            case 2:
                teaProductFragment.setFilterMap(list);
                break;
        }
    }
}
