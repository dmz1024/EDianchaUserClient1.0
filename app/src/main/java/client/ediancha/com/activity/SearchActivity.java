package client.ediancha.com.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.api.MyRetrofitUtil;
import client.ediancha.com.base.BaseActivity;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.base.SingleNetWorkBaseFragment;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.History;
import client.ediancha.com.entity.Hot;
import client.ediancha.com.entity.TeaFilter;
import client.ediancha.com.interfaces.SingleTextWatcher;
import client.ediancha.com.myview.TextImage;
import client.ediancha.com.util.SharedPreferenUtil;

/**
 * Created by dengmingzhi on 2016/10/26.
 */

public class SearchActivity extends ToolBarActivity {
    private TextView tv_cancle;
    private EditText et_search;
    private TextImage tv_event;
    private TextImage tv_space;
    private TextImage tv_tea;
    private int type;
    private List<History.Content> list = new ArrayList<>();
    private ListView lv_history;
    private TextView tv_clear;
    private History history;
    private HotAdapter hotAdapter;
    private RecyclerView rv_hot;
    private List<Hot.Data> hot;
    private List<Hot.Cat> hotList = new ArrayList<>();
    private Hot h;

    @Override
    protected String getToolBarTitle() {
        return null;
    }

    /**
     * 历史记录
     */
    private void initHistory() {
        String json = new SharedPreferenUtil(this, "history").getString("history");
        if (TextUtils.isEmpty(json)) {
            json = "{\"data\":[{\"contents\":[]},{\"contents\":[]},{\"contents\":[]}]}";
        }
        history = new Gson().fromJson(json, History.class);
        lv_history.setAdapter(mAdapter = new MyAdapter());
        lv_history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                et_search.setText(list.get(position).content);
                search();
            }
        });
        fillHistory();
        initHot();
    }


    private void initHot() {


        Map<String, String> map = new HashMap<>();
        map.put("c", "auto");
        map.put("a", "search_hot");
        MyRetrofitUtil.getInstance().post("app.php", map, Hot.class, new MyRetrofitUtil.OnRequestListener<Hot>() {
            @Override
            public void noNetwork() {

            }

            @Override
            public void serverErr() {

            }

            @Override
            public void haveData(Hot hot) {

                if (hot.result == 0 && hot.data.size() >= (type + 1)) {
                    SearchActivity.this.hot = hot.data;
                    SearchActivity.this.h = hot;
                    hotList.clear();
                    hotList.addAll(hot.data.get(type).data);
                    hotAdapter = new HotAdapter(SearchActivity.this, hotList);
                    LinearLayoutManager manager = new LinearLayoutManager(SearchActivity.this);
                    manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    rv_hot.addItemDecoration(new ItemDecoration(SearchActivity.this,LinearLayoutManager.HORIZONTAL,10,"#fbfbfb"));
                    rv_hot.setLayoutManager(manager);
                    rv_hot.setAdapter(hotAdapter);
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void resultNo0(String s) {

            }

            @Override
            public void start() {

            }
        });
    }


    private void fillHistory() {
        list.clear();
        list.addAll(history.data.get(type).contents);
        mAdapter.notifyDataSetChanged();
    }

    private MyAdapter mAdapter;


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            int count = list.size();
            tv_clear.setVisibility(count == 0 ? View.GONE : View.VISIBLE);
            return count;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv_content = (TextView) View.inflate(SearchActivity.this, R.layout.item_textview_1, null);
            tv_content.setText(list.get(position).content);
            return tv_content;
        }
    }

    @Override
    protected int getTop() {
        return 0;
    }

    @Override
    public void onCreateCustomToolBar(Toolbar toolbar) {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        initHistory();
    }

    @Override
    protected int getRid() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        tv_cancle = (TextView) findViewById(R.id.tv_cancle);
        rv_hot = (RecyclerView) findViewById(R.id.rv_hot);
        tv_event = (TextImage) findViewById(R.id.tv_event);
        tv_clear = (TextView) findViewById(R.id.tv_clear);
        tv_clear.setOnClickListener(this);
        tv_space = (TextImage) findViewById(R.id.tv_space);
        tv_tea = (TextImage) findViewById(R.id.tv_tea);
        et_search = (EditText) findViewById(R.id.et_search);
        tv_cancle.setOnClickListener(this);
        tv_event.setOnClickListener(this);
        tv_space.setOnClickListener(this);
        tv_tea.setOnClickListener(this);
        lv_history = (ListView) findViewById(R.id.lv_history);
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

        et_search.addTextChangedListener(new SingleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (et_search.getText().toString().length() > 0) {
                    tv_cancle.setText("搜索");
                } else {
                    tv_cancle.setText("取消");
                }
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
        boolean isHave = false;
        exit:
        for (int i = 0; i < list.size(); i++) {
            if (TextUtils.equals(str, list.get(i).content)) {
                isHave = true;
                break exit;
            }
        }

        if (!isHave) {
            History.Content content = new History.Content();
            content.content = str;
            history.data.get(type).contents.add(content);
            fillHistory();
        }


        List<TeaFilter.Cat> cats = new ArrayList<>();
        TeaFilter.Cat cat = new TeaFilter.Cat();
        cat.key = type != 0 ? "keyword" : "tag";
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
    protected void back() {
        new SharedPreferenUtil(this, "history").setData("history", new Gson().toJson(history));
        super.back();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancle:
                if (TextUtils.equals("取消", tv_cancle.getText().toString())) {
                    back();
                } else {
                    search();
                }
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
            case R.id.tv_clear:
                clear();
                break;
        }
    }

    /**
     * 清除记录
     */
    private void clear() {
        history.data.get(type).contents.clear();
        fillHistory();
    }

    private void change() {
        fillHistory();
        if (hot != null && hot.size() >= (type + 1)) {
            hotList.clear();
            hotList.addAll(hot.get(type).data);
            hotAdapter.notifyDataSetChanged();
        }

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


   public class HotAdapter extends SingleBaseAdapter<Hot.Cat> {

        public HotAdapter(Context ctx, List<Hot.Cat> list) {
            super(ctx, list);
        }

        public HotAdapter(List<Hot.Cat> list) {
            super(list);
        }

        @Override
        public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HotViewHolder(View.inflate(SearchActivity.this, R.layout.item_tea_event_filter_content, null));
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int position) {
            ((HotViewHolder) holder).tv_content.setText(list.get(position).name);
        }
       class HotViewHolder extends BaseViewHolder {
           public TextView tv_content;

           public HotViewHolder(View itemView) {
               super(itemView);
               tv_content = (TextView) itemView;
           }

           @Override
           protected void onClick(int layoutPosition) {
               List<TeaFilter.Cat> cats = new ArrayList<>();
               TeaFilter.Cat cat = new TeaFilter.Cat();
               cat.key = h.data.get(type).key;
               cat.name = HotAdapter.this.list.get(layoutPosition).name;
               cat.value = HotAdapter.this.list.get(layoutPosition).name;
               cats.add(cat);
               String listJson = new Gson().toJson(cats);
               Intent intent = new Intent(SearchActivity.this, TeaEventFilterResultActivity.class);
               intent.putExtra("data", listJson);
               intent.putExtra("type", type);
               startActivity(intent);
           }
       }
    }


}
