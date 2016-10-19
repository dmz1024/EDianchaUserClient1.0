package client.ediancha.com.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.adapter.TeaEventFilterAdapter;
import client.ediancha.com.adapter.TeaEventFilterResultAdapter;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.fragment.TeaEventFragment;
import client.ediancha.com.interfaces.OnTeaEventFilterResultChangeListener;
import client.ediancha.com.interfaces.OnTeaEventMapListener;
import client.ediancha.com.util.Util;

public class TeaEventFilterResultActivity extends ToolBarActivity implements OnTeaEventMapListener, OnTeaEventFilterResultChangeListener {
    private RecyclerView rv_filter;
    private TeaEventFragment teaEventFragment;
    private List<String> list;

    @Override
    protected String getToolBarTitle() {
        return "茶活动";
    }

    @Override
    protected void initView() {
        rv_filter = (RecyclerView) findViewById(R.id.rv_filter);
        teaEventFragment = new TeaEventFragment();
        teaEventFragment.setOnTeaEventMapListener(this);
    }

    @Override
    protected void initData() {

        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("返回结果" + i);
        }
        TeaEventFilterResultAdapter mAdapter = new TeaEventFilterResultAdapter(this, list);
        mAdapter.setOnTeaEventFilterResultChangeListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_filter.addItemDecoration(new ItemDecoration(this, LinearLayout.HORIZONTAL, Util.dp2Px(8), "#fbfbfb"));
        rv_filter.setLayoutManager(manager);
        rv_filter.setAdapter(mAdapter);

        getSupportFragmentManager().beginTransaction().replace(R.id.fg_filter, teaEventFragment).commit();
    }

    @Override
    protected int getRid() {
        return R.layout.activity_tea_event_filter_result;
    }

    @Override
    public Map<String, String> getMap(Map<String, String> map) {
        return map;
    }

    @Override
    public void change(int totalSize, int position) {
        if (totalSize == 0) {
            rv_filter.setVisibility(View.GONE);
        }

        teaEventFragment.setUserVisibleHint(true);

    }
}
