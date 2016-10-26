package client.ediancha.com.activity;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import client.ediancha.com.R;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.fragment.AddressFragment;

/**
 * Created by dengmingzhi on 2016/10/25.
 */

public class AddressActivity extends ToolBarActivity {
    private AddressFragment addressFragment;

    @Override
    protected String getToolBarTitle() {
        return getIntent().getBooleanExtra("chooseLink", false) ? "选择收货人" : "地址管理";
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fg_base, addressFragment = AddressFragment.getInstance(getIntent().getBooleanExtra("chooseLink", false))).commit();
    }

    @Override
    protected int getRid() {
        return R.layout.base_framelayout;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_add) {
            Intent intent = new Intent(this, AddressManagerActivity.class);
            startActivityForResult(intent, Constant.ADDRESS_ADD);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_address, menu);
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.ADDRESS_ADD && data != null) {
            addressFragment.onRefresh();
        }
    }
}
