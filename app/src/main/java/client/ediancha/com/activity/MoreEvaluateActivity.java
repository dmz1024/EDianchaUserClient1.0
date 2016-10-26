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
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.fragment.EvaluateFragment;
//import client.ediancha.com.fragment.EvaluateFragment;


public class MoreEvaluateActivity extends ToolBarActivity {

    @Override
    protected String getToolBarTitle() {
        return "评价列表";
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fg_base, EvaluateFragment.getInstance(getIntent().getStringExtra("id"), getIntent().getStringExtra("type"))).commit();
    }

    @Override
    protected int getRid() {
        return R.layout.base_framelayout;
    }
}
