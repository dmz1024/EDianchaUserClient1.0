package client.ediancha.com.activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import client.ediancha.com.R;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.fragment.BuyCar1Fragment;
import client.ediancha.com.myview.Color2Text;
import client.ediancha.com.processor.BuyCarUtil;

/**
 * Created by dengmingzhi on 2016/10/27.
 */

public class BuyCarActivity extends ToolBarActivity {
    private BuyCar1Fragment buyCarFragment;
    private boolean isDelete = true;
    private Button bt_pay;
    private Color2Text tv_price;

    @Override
    protected String getToolBarTitle() {
        return "购物车";
    }

    @Override
    protected void initView() {
        bt_pay = (Button) findViewById(R.id.bt_pay);
        tv_price = (Color2Text) findViewById(R.id.tv_price);
        bt_pay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_pay:
                apy();
                break;
        }
    }

    private void apy() {

        buyCarFragment.choosePay();
    }

    @Override
    protected void initData() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fg_base, buyCarFragment = new BuyCar1Fragment()).commit();
        buyCarFragment.setOnHavePrice(new BuyCar1Fragment.OnHavePrice() {
            @Override
            public void price(double price) {
                tv_price.setTextNotChange("￥" + price);
                if (price > 0) {
                    bt_pay.setEnabled(true);
                    bt_pay.setAlpha(1.0f);
                } else {
                    bt_pay.setEnabled(false);
                    bt_pay.setAlpha(0.5f);
                }
            }
        });
    }

    @Override
    protected int getRid() {
        return R.layout.activity_buycar;
    }
}
