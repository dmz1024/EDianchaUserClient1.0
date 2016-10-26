package client.ediancha.com.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.activity.AddressActivity;
import client.ediancha.com.base.SingleNetWorkBaseFragment;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.entity.BaseEntity;
import client.ediancha.com.myview.ChooseStringView;

/**
 * Created by dengmingzhi on 2016/10/26.
 */

public class BuyTeaFragment extends SingleNetWorkBaseFragment<BaseEntity> {
    private TextView tv_link_name;
    private TextView tv_link_address;

    @Override
    protected void writeData(BaseEntity t) {

    }

    @Override
    protected String getUrl() {
        return "app.php";
    }

    @Override
    protected Map<String, String> getMap() {
        map.put("a", "search");
        map.put("c", "chahui");
        return map;
    }

    @Override
    protected Class<BaseEntity> getTClass() {
        return BaseEntity.class;
    }

    @Override
    protected ShowCurrentViewENUM getDefaultView() {
        return ShowCurrentViewENUM.VIEW_HAVE_DATA;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_buy_tea, null);
        Button bt_pay = (Button) view.findViewById(R.id.bt_pay);
        RelativeLayout rl_link = (RelativeLayout) view.findViewById(R.id.rl_link);
        tv_link_name = (TextView) view.findViewById(R.id.tv_link_name);
        tv_link_address = (TextView) view.findViewById(R.id.tv_link_address);
        bt_pay.setOnClickListener(this);
        rl_link.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.bt_pay:
                pay();
                break;
            case R.id.rl_link:
                chooseLink();
                break;
        }
    }

    /**
     * 选择联系人
     */
    private void chooseLink() {
        Intent intent = new Intent(getContext(), AddressActivity.class);
        intent.putExtra("chooseLink", true);
        startActivityForResult(intent, Constant.ADDRESS_CHOOSW);
    }

    private void pay() {
        List<String> list = new ArrayList<>();
        list.add("微信支付");
        list.add("微信他人支付");
        list.add("支付宝支付");
        new ChooseStringView(getContext(), list) {
            @Override
            protected void itemClick(int position) {
                Log.d("dd", "dd");
            }
        }.showAtLocation();
    }

    @Override
    public boolean isCanRefresh() {
        return false;
    }

    @Override
    protected boolean getCanRefresh() {
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.ADDRESS_CHOOSW && data != null) {
            tv_link_name.setText(data.getStringExtra("name"));
            tv_link_address.setText(data.getStringExtra("address"));
        }
    }
}
