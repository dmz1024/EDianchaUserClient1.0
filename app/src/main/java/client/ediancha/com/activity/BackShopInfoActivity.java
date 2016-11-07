package client.ediancha.com.activity;

import client.ediancha.com.R;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.fragment.WriteBackShopDescFragment;

/**
 * Created by dengmingzhi on 2016/11/7.
 */

public class BackShopInfoActivity extends ToolBarActivity {
    @Override
    protected String getToolBarTitle() {
        return "退货详情";
    }

    @Override
    protected void initView() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fg_base, WriteBackShopDescFragment.getInstance(getIntent().getStringExtra("id"))).commit();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getRid() {
        return R.layout.base_framelayout;
    }
}
