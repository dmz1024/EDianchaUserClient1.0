package client.ediancha.com.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.fragment.LikeEventFragment;
import client.ediancha.com.fragment.TeaOrderFragment;

/**
 * 茶品订单
 */
public class LikeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        TabLayout layout = (TabLayout) findViewById(R.id.tablayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        final String[] titles = {"活动", "茶馆", "茶品"};

        final List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new LikeEventFragment());
        fragmentList.add(new LikeEventFragment());
        fragmentList.add(new LikeEventFragment());

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