package client.ediancha.com.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.entity.Test;
import client.ediancha.com.fragment.TeaEventFragment;
import client.ediancha.com.fragment.TeaProductFragment;
import client.ediancha.com.fragment.TeaSpaceFragment;
import client.ediancha.com.util.SharedPreferenUtil;
import client.ediancha.com.util.Util;

/**
 * 茶品订单
 */
public class MainActivity extends ToolBarActivity {
    private TextView tv_filter;
    private ViewPager viewPager;
    private LinearLayout ll_tab;
    private LinearLayout ll_root;
    private TabLayout layout;
    private final String[] titles = {"茶·活动", "茶·空间", "茶·产品"};
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected String getToolBarTitle() {

        Test test = new Gson().fromJson("{\"result\":\"0\",\"data\":[{\"r\":1,\"product_id\":\"104\",\"name\":\"\\u5609\\u53f6\\u6e90\\u67a3\\u9999\\u7816\",\"image\":\"http:\\/\\/www.ediancha.com\\/upload\\/images\\/000\\/000\\/068\\/201609\\/57ea7acc50788.png\",\"price\":\"400.00\",\"original_price\":\"400.00\",\"is_recommend\":\"1\",\"recommend_title\":\"\\u53d1\\u751f\\u7684\\u53d1\\u751f\\u7684\"},{\"name\":\"\\u4e91\\u5357\\u666e\\u6d31\",\"r\":2},{\"name\":\"\\u6b63\\u5c71\\u5c0f\\u79cd\",\"r\":2},{\"name\":\"\\u82f1\\u5fb7\\u7ea2\\u8336\",\"r\":2}],\"msg\":\"\"}", Test.class);
        for (int i = 0; i < test.data.size(); i++) {
            Log.d("name", test.data.get(i).name);
        }

        return "E点茶";
    }

    @Override
    protected void initView() {
        ll_tab = (LinearLayout) findViewById(R.id.ll_tab);
        tv_filter = (TextView) findViewById(R.id.tv_filter);
        tv_filter.setOnClickListener(this);
        layout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_filter:
                Intent intent = new Intent(MainActivity.this, TeaEventFilterActivity.class);
                intent.putExtra("position", viewPager.getCurrentItem());
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void initData() {
        fragmentList.add(new TeaEventFragment());
        fragmentList.add(new TeaSpaceFragment());
        fragmentList.add(new TeaProductFragment());
        viewPager.setOffscreenPageLimit(fragmentList.size());
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });


        layout.setupWithViewPager(viewPager);

       Util.setUserInfo(this);
    }

    @Override
    protected int getRid() {
        return R.layout.activity_main;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void onCreateCustomToolBar(Toolbar toolbar) {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.icon_my);
//        toolbar.setVisibility(View.GONE);
//        toolbar.setBackgroundColor(getResources().getColor(R.color.color00000000));
    }

    @Override
    protected void back() {
        Util.skip(this, MyCenterActivity.class);
    }
}
