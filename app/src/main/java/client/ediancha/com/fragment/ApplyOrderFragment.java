package client.ediancha.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import java.util.List;
import java.util.Map;

import client.ediancha.com.adapter.ApplyOrderAdapter;
import client.ediancha.com.adapter.TeaOrderAdapter;
import client.ediancha.com.base.ListNetWorkBaseFragment;
import client.ediancha.com.entity.ApplyOrder;
import client.ediancha.com.entity.TeaOrder;

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
        return "top250";
    }

    @Override
    protected Map<String, String> getMap() {
        return map;
    }

    @Override
    protected void initMap() {
        map.put("start", (page * 10) + "");
        map.put("count", 10 + "");
    }

    @Override
    protected Class<ApplyOrder> getTClass() {
        return ApplyOrder.class;
    }

    @Override
    protected boolean isOnlyInitOne() {
        return false;
    }

    @Override
    protected boolean isCanFirstInitData() {
        return TextUtils.equals(type, "0");
    }
}
