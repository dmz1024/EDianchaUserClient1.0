package client.ediancha.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import java.util.List;
import java.util.Map;

import client.ediancha.com.adapter.ApplyOrderAdapter;
import client.ediancha.com.base.ListNetWorkBaseFragment;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.entity.ApplyOrder;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class ApplyOrderFragment extends ListNetWorkBaseFragment<ApplyOrder.Data, ApplyOrder> {
    public String type = "";

    public static ApplyOrderFragment getInstance(String type) {
        ApplyOrderFragment teaOrderFragment = new ApplyOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        teaOrderFragment.setArguments(bundle);
        return teaOrderFragment;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getString("type");
    }

    @Override
    protected RecyclerView.Adapter getAdapter(List<ApplyOrder.Data> totalList) {
        return new ApplyOrderAdapter(getContext(), totalList);
    }

    @Override
    protected String getUrl() {
        return "app.php";
    }

    @Override
    protected Map<String, String> getMap() {
        map.put("c", "myorder");
        map.put("a", "baoming_orders");
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);
        map.put("type", type);
        return map;
    }

    @Override
    protected Class<ApplyOrder> getTClass() {
        return ApplyOrder.class;
    }


    @Override
    protected boolean isCanFirstInitData() {
        return TextUtils.equals(type, "3");
    }



}
