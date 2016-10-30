package client.ediancha.com.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import client.ediancha.com.R;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.entity.TeaProduct;
import client.ediancha.com.entity.Test;
import client.ediancha.com.fragment.TeaEventFragment;
import client.ediancha.com.fragment.TeaProductFragment;
import client.ediancha.com.fragment.TeaSpaceFragment;
import client.ediancha.com.myview.PopMessageTips;
import client.ediancha.com.processor.PopUpdateApk;
import client.ediancha.com.util.SharedPreferenUtil;
import client.ediancha.com.util.Util;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 茶品订单
 */
public class MainActivity extends ToolBarActivity {
    private TextView tv_filter;
    private ViewPager viewPager;
    private LinearLayout ll_tab;
    private TabLayout layout;
    private final String[] titles = {"茶·活动", "茶·空间", "茶·产品"};
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected String getToolBarTitle() {
        return "E点茶";
    }

    @Override
    protected void initView() {
        setBackgroud(false);
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
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }


    @Override
    protected void initData() {
        if(getIntent().getBooleanExtra("isFromMessage",false)){
            startActivity(new Intent(this,MessageActivity.class));
        }
        initJpush();
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

        Log.d("账号信息", UserInfo.uid + "--" + UserInfo.token);

    }

    private void initJpush() {
        setBieMing("adc");
        JPushInterface.setLatestNotificationNumber(this,3);
        JPushInterface.initCrashHandler(this);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_search) {
            Util.skip(this, SearchActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreateCustomToolBar(Toolbar toolbar) {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.icon_my);
    }

    @Override
    protected void back() {
        Util.skip(this, MyCenterActivity.class);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            finishAll();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private int count=0;
    /**
     * 设置极光推送别名
     */

    private void setBieMing(final String uid) {

        if (count > 10) {
            return;
        }
        count++;
        JPushInterface.setAliasAndTags(getApplicationContext(),
                uid,
                null,
                new TagAliasCallback() {
                    @Override
                    public void gotResult(int code, String alias, Set<String> tags) {
                        String logs;
                        switch (code) {
                            case 0:
                                count = 0;
                                logs = "Set tag and alias success";
                                Log.i("极光", logs);
                                // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                                break;
                            case 6002:
                                ll_tab.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        setBieMing(uid);
                                    }
                                }, 61 * 1000);
                                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                                Log.i("极光", logs);
                                break;
                            default:
                                ll_tab.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        setBieMing(uid);
                                    }
                                }, 61 * 1000);
                                logs = "Failed with errorCode = " + code;
                                Log.e("极光", logs);
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setBackgroud(true);
    }



    private void setBackgroud(boolean back){
        new SharedPreferenUtil(this,"background").setData("isback",back);
    }

}
