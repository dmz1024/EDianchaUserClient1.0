package client.ediancha.com.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import client.ediancha.com.R;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.fragment.MyCenterFragment;
import client.ediancha.com.util.MyToast;
import client.ediancha.com.util.Util;

public class MyCenterActivity extends ToolBarActivity {
    private MyCenterFragment myCenterFragment;

    @Override
    protected String getToolBarTitle() {
        return null;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        getSupportFragmentManager().beginTransaction().add(R.id.fg_base, myCenterFragment = new MyCenterFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_center, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_email:
                if (TextUtils.isEmpty(UserInfo.uid)) {
                    MyToast.showToast("请先登录");
                    startActivityForResult(new Intent(this, LoginActivity.class), Constant.MY_CENTER_INFO);
                } else {
                    Util.skip(this, MessageActivity.class);
                }

                return true;
            case R.id.item_set:
                Util.skip(this, SetActivity.class);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getRid() {
        return R.layout.base_framelayout;
    }

    @Override
    protected int getTop() {
        return 0;
    }

    @Override
    public void onCreateCustomToolBar(Toolbar toolbar) {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setBackgroundColor(Color.parseColor("#00333333"));
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (myCenterFragment != null && UserInfo.change) {
            myCenterFragment.onRefresh();
        }
    }
}
