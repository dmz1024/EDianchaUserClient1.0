package client.ediancha.com.activity;

import client.ediancha.com.R;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.fragment.BuyTeaFragment;

/**
 * Created by dengmingzhi on 2016/10/26.
 */

public class BuyTeaActivity extends ToolBarActivity {
    @Override
    protected String getToolBarTitle() {
        return "立即购买";
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fg_base,new BuyTeaFragment()).commit();
    }

    @Override
    protected int getRid() {
        return R.layout.base_framelayout;
    }
}
