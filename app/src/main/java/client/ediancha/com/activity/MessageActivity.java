package client.ediancha.com.activity;

import android.content.Intent;

import client.ediancha.com.R;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.fragment.SystemMessageFragment;
import client.ediancha.com.util.SharedPreferenUtil;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 2016/10/30.
 */

public class MessageActivity extends ToolBarActivity {
    @Override
    protected String getToolBarTitle() {
        return "系统消息";
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fg_base, new SystemMessageFragment()).commit();
    }

    @Override
    protected int getRid() {
        return R.layout.base_framelayout;
    }

}
