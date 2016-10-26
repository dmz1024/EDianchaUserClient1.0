package client.ediancha.com.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.entity.TeaFilter;
import client.ediancha.com.fragment.MyCenterFragment;
import client.ediancha.com.fragment.TeaFilterFragment;
import client.ediancha.com.fragment.TeaOrderFragment;
import client.ediancha.com.util.MyToast;

public class TeaEventFilterActivity extends ToolBarActivity {

    private TextView tv_left;
    private TextView tv_right;
    private int position;
    private TeaFilterFragment teaFilterFragment;

    @Override
    protected String getToolBarTitle() {
        switch (position) {
            case 0:
                return "茶·活动筛选";
            case 1:
                return "茶·空间筛选";
            case 2:
                return "茶·产品筛选";
        }
        return null;
    }

    @Override
    protected void initView() {
        position = getIntent().getIntExtra("position", 0);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_left.setOnClickListener(this);
        tv_right.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        switch (position) {
            case 0:
                getSupportFragmentManager().beginTransaction().add(R.id.fg_content, teaFilterFragment = TeaFilterFragment.getInstance("chahui")).commit();
                break;
            case 1:
                getSupportFragmentManager().beginTransaction().add(R.id.fg_content, teaFilterFragment = TeaFilterFragment.getInstance("chaguan")).commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().add(R.id.fg_content, teaFilterFragment = TeaFilterFragment.getInstance("chanpin")).commit();
                break;
        }
    }


    @Override
    protected int getRid() {
        return R.layout.activity_tea_event_filter;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                left();
                break;
            case R.id.tv_right:
                right();
                break;
        }
    }

    private void right() {
        Map<String, TeaFilter.Cat> map = teaFilterFragment.getFillterMap();

        if (map.size() == 0) {
            MyToast.showToast("请选择至少一个筛选条件");
            return;
        }
        List<TeaFilter.Cat> list = new ArrayList<>();

        for (String key : map.keySet()) {
            TeaFilter.Cat cat = map.get(key);
            cat.key = key;
            list.add(cat);
        }

        String listJson = new Gson().toJson(list);
        Intent intent = new Intent(this, TeaEventFilterResultActivity.class);
        intent.putExtra("data", listJson);
        intent.putExtra("type", position);
        startActivity(intent);
    }

    private void left() {
        teaFilterFragment.clearFilter();
    }
}
