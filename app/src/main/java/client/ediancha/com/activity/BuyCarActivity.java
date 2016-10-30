package client.ediancha.com.activity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import client.ediancha.com.R;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.entity.BuyCar;
import client.ediancha.com.fragment.BuyCarFragment;
import client.ediancha.com.myview.Color2Text;
import client.ediancha.com.processor.BuyCarUtil;
import client.ediancha.com.processor.PayUtil;
import client.ediancha.com.util.MyToast;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 2016/10/27.
 */

public class BuyCarActivity extends ToolBarActivity {
    private BuyCarFragment buyCarFragment;
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
        BuyCarUtil.getInstance().setContext(this).setOnPayListener(new BuyCarUtil.OnPayListener() {
            @Override
            public void payId(String id) {
                Log.d("id", id);
                buyCarFragment.clear();
                PayUtil.getInstance().setContext(BuyCarActivity.this).getWechatPayId(id);
            }
        }).pay(buyCarFragment.getPayId());
    }

    @Override
    protected void initData() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fg_base, buyCarFragment = new BuyCarFragment()).commit();
        buyCarFragment.setOnHavePrice(new BuyCarFragment.OnHavePrice() {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_edit:
                isDelete = buyCarFragment.setDelete(isDelete) ? !isDelete : isDelete;
                item.setTitle(isDelete ? "编辑" : "取消");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_buycar, menu);
        return true;
    }
}
