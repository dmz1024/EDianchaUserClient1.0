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
import client.ediancha.com.fragment.ApplyOrderFragment;

/**
 * 茶品订单
 */
public class ApplyOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea_order);
        TabLayout layout = (TabLayout) findViewById(R.id.tablayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        final String[] titles = {"待审核", "待参加", "待评价", "已完成", "已关闭"};

        final List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(ApplyOrderFragment.getInstance("0"));
        fragmentList.add(ApplyOrderFragment.getInstance("1"));
        fragmentList.add(ApplyOrderFragment.getInstance("2"));
        fragmentList.add(ApplyOrderFragment.getInstance("3"));
        fragmentList.add(ApplyOrderFragment.getInstance("4"));

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
