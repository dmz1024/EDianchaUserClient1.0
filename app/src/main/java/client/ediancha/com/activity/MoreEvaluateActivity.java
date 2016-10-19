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
//import client.ediancha.com.fragment.EvaluateFragment;
import client.ediancha.com.fragment.MyCenterFragment;
import client.ediancha.com.fragment.TeaOrderFragment;

public class MoreEvaluateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_evaluate);
        TabLayout layout = (TabLayout) findViewById(R.id.tablayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        final String[] titles = {"评价", "带图评价"};

        final List<Fragment> fragmentList = new ArrayList<>();
//        fragmentList.add(EvaluateFragment.getInstance(false));
//        fragmentList.add(EvaluateFragment.getInstance(true));
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
