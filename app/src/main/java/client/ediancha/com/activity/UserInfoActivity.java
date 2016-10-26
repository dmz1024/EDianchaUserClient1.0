package client.ediancha.com.activity;

import client.ediancha.com.R;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.fragment.SetFragment;
import client.ediancha.com.fragment.UserInfoFragment;

/**
 * Created by dengmingzhi on 2016/10/24.
 */

public class UserInfoActivity extends ToolBarActivity {
    @Override
    protected String getToolBarTitle() {
        return "用户信息";
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        getSupportFragmentManager().beginTransaction().add(R.id.fg_content, new UserInfoFragment()).commit();
    }

    @Override
    protected int getRid() {
        return R.layout.activity_my_center;
    }
}
