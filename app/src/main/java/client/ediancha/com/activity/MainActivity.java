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
import client.ediancha.com.fragment.AppointmentFragment;
import client.ediancha.com.fragment.TeaEventFilterFragment;
import client.ediancha.com.fragment.TeaEventFragment;
import client.ediancha.com.fragment.TeaProductFragment;
import client.ediancha.com.fragment.TeaSpaceFragment;
import client.ediancha.com.util.Util;

/**
 * 茶品订单
 */
public class MainActivity extends AppCompatActivity {
    private TextView tv_filter;
    private ViewPager viewPager;
    private LinearLayout ll_tab;
    private LinearLayout ll_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll_tab = (LinearLayout) findViewById(R.id.ll_tab);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
//        mCollapsingToolbarLayout.setTitle("");


        tv_filter = (TextView) findViewById(R.id.tv_filter);

        tv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TeaEventFilterActivity.class);
                intent.putExtra("position", viewPager.getCurrentItem());
                startActivity(intent);
            }
        });

        tv_filter.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Util.skip(MainActivity.this, MyCenterActivity.class);
                return true;
            }
        });

        final TabLayout layout = (TabLayout) findViewById(R.id.tablayout);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
//
        final String[] titles = {"茶·活动", "茶·空间", "茶·产品"};

        TeaEventFragment a = new TeaEventFragment();
        a.setA(new RecycleScrollViewListener() {

            @Override
            public void isUp() {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll_tab.getLayoutParams();
                if (ll_tab.getBottom() > 0) {
                    layoutParams.topMargin = layoutParams.topMargin - 5;

                    ll_tab.setLayoutParams(layoutParams);

                }


            }

            @Override
            public void isDown() {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll_tab.getLayoutParams();
                if (ll_tab.getTop() != 0) {
                    layoutParams.topMargin = layoutParams.topMargin + 5;
                    ll_tab.setLayoutParams(layoutParams);
                }
            }

            @Override
            public void dy(int y) {

            }
        });

        final List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(a);
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
}
