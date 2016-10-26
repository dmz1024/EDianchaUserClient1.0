package client.ediancha.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.BaseActivity;
import client.ediancha.com.entity.TeaFilter;
import client.ediancha.com.myview.TextImage;

/**
 * Created by dengmingzhi on 2016/10/26.
 */

public class SearchActivity extends BaseActivity {
    private TextView tv_cancle;
    private EditText et_search;
    private TextImage tv_event;
    private TextImage tv_space;
    private TextImage tv_tea;
    private int type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        tv_cancle = (TextView) findViewById(R.id.tv_cancle);
        tv_event = (TextImage) findViewById(R.id.tv_event);
        tv_space = (TextImage) findViewById(R.id.tv_space);
        tv_tea = (TextImage) findViewById(R.id.tv_tea);
        et_search = (EditText) findViewById(R.id.et_search);
        tv_cancle.setOnClickListener(this);
        tv_event.setOnClickListener(this);
        tv_space.setOnClickListener(this);
        tv_tea.setOnClickListener(this);

        et_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    search();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 搜索
     */
    private void search() {
        String str = et_search.getText().toString();
        if (TextUtils.isEmpty(str)) {
            return;
        }

        List<TeaFilter.Cat> cats = new ArrayList<>();
        TeaFilter.Cat cat = new TeaFilter.Cat();
        cat.key = "keyword";
        cat.name = str;
        cat.value = str;
        cats.add(cat);
        String listJson = new Gson().toJson(cats);
        Intent intent = new Intent(this, TeaEventFilterResultActivity.class);
        intent.putExtra("data", listJson);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancle:
                finish();
                break;
            case R.id.tv_event:
                type = 0;
                change();
                break;
            case R.id.tv_space:
                type = 1;
                change();
                break;
            case R.id.tv_tea:
                type = 2;
                change();
                break;
        }
    }

    private void change() {
        tv_event.setDrawable(R.mipmap.icon_search_teaactivity);
        tv_space.setDrawable(R.mipmap.icon_search_teahouse);
        tv_tea.setDrawable(R.mipmap.icon_search_teatype);
        switch (type) {
            case 0:
                tv_event.setDrawable(R.mipmap.icon_search_teaactivity_cur);
                break;
            case 1:
                tv_space.setDrawable(R.mipmap.icon_search_teahouse_cur);
                break;
            case 2:
                tv_tea.setDrawable(R.mipmap.icon_search_teatype_cur);
                break;
        }

    }
}
