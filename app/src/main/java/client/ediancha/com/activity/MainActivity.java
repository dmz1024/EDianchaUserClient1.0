package client.ediancha.com.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.RecycleScrollViewListener;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.fragment.AppointmentFragment;
import client.ediancha.com.fragment.TeaEventFilterFragment;
import client.ediancha.com.fragment.TeaEventFragment;
import client.ediancha.com.fragment.TeaProductFragment;
import client.ediancha.com.fragment.TeaSpaceFragment;
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
