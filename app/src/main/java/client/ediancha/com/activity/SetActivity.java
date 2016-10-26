package client.ediancha.com.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import client.ediancha.com.R;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.fragment.SetFragment;
import client.ediancha.com.fragment.UserInfoFragment;

public class SetActivity extends ToolBarActivity {

    @Override
    protected String getToolBarTitle() {
        return "设置";
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        getSupportFragmentManager().beginTransaction().add(R.id.fg_content, new SetFragment()).commit();
    }

    @Override
    protected int getRid() {
        return R.layout.activity_my_center;
    }

}
