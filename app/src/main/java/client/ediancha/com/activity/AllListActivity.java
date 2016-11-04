package client.ediancha.com.activity;

import client.ediancha.com.R;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.fragment.TeaEventFragment;
import client.ediancha.com.fragment.TeaProductFragment;
import client.ediancha.com.fragment.TeaSpaceFragment;

/**
 * Created by dengmingzhi on 2016/11/3.
 */

public class AllListActivity extends ToolBarActivity {
    private int type;

    @Override
    protected String getToolBarTitle() {
        if (type == 1) {
            return "茶·活动";
        } else if (type == 2) {
            return "茶·空间";
        }
        return "茶·产品";
    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra("type", 3);
    }

    @Override
    protected void initData() {
        switch (type) {
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.fg_base, new TeaEventFragment()).commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.fg_base,TeaSpaceFragment.getInstance(true)).commit();
                break;
            case 3:
                getSupportFragmentManager().beginTransaction().replace(R.id.fg_base,TeaProductFragment.getIsFirstInitData(true)).commit();
                break;
        }
    }

    @Override
    protected int getRid() {
        return R.layout.base_framelayout;
    }
}
