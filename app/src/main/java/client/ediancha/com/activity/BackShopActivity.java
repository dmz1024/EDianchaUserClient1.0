package client.ediancha.com.activity;

import client.ediancha.com.R;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.fragment.BackShopFragment;

/**
 * Created by dengmingzhi on 2016/11/4.
 */

public class BackShopActivity extends ToolBarActivity {
    @Override
    protected String getToolBarTitle() {
        return "退货订单";
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fg_base, new BackShopFragment()).commit();
    }

    @Override
    protected int getRid() {
        return R.layout.base_framelayout;
    }
}
