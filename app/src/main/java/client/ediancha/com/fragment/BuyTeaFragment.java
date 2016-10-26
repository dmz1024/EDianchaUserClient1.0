package client.ediancha.com.fragment;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.base.SingleNetWorkBaseFragment;
import client.ediancha.com.entity.BaseEntity;
import client.ediancha.com.myview.ChooseStringView;

/**
 * Created by dengmingzhi on 2016/10/26.
 */

public class BuyTeaFragment extends SingleNetWorkBaseFragment<BaseEntity> {
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
        bt_pay.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.bt_pay:
                pay();
                break;
        }
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
}
