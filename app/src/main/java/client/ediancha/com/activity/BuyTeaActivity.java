package client.ediancha.com.activity;

import android.content.Intent;

import client.ediancha.com.R;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.fragment.BuyTeaFragment;
import client.ediancha.com.util.MyToast;

/**
 * Created by dengmingzhi on 2016/10/26.
 */

public class BuyTeaActivity extends ToolBarActivity {
    private BuyTeaFragment buyTeaFragment;

    @Override
    protected String getToolBarTitle() {
        return "立即购买";
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fg_base, buyTeaFragment = BuyTeaFragment.getInstance(getIntent().getStringExtra("id"))).commit();
    }

    @Override
    protected int getRid() {
        return R.layout.base_framelayout;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            int result = intent.getIntExtra("pay_result", -1);
            if (result == 0) {
                MyToast.showToast("支付成功！可去个人中心-茶品订单查看", 4000);
                setResult(2,getIntent());
                finish();
            } else {
                if (buyTeaFragment != null) {
                    buyTeaFragment.setBtpayEnable(true);
                }
            }
        }
    }
}
