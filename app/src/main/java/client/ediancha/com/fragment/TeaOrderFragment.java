package client.ediancha.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import java.util.List;
import java.util.Map;

import client.ediancha.com.adapter.TeaOrderAdapter;
import client.ediancha.com.base.ListNetWorkBaseFragment;
import client.ediancha.com.entity.TeaOrder;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaOrderFragment extends ListNetWorkBaseFragment<TeaOrder.Data, TeaOrder> {
    public String type = "";

    public static TeaOrderFragment getInstance(String type) {
        TeaOrderFragment teaOrderFragment = new TeaOrderFragment();
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
    protected RecyclerView.Adapter getAdapter(List<TeaOrder.Data> totalList) {
        return new TeaOrderAdapter(getContext(), totalList);
    }

    @Override
    protected String getUrl() {
        return "top250";
    }

    @Override
    protected Map<String, String> getMap() {
//        map.put("start", "1");
//        map.put("date", "2016-10-11");
        return map;
    }

    @Override
    protected void initMap() {
        map.put("start", (page * 10) + "");
        map.put("count", 10 + "");
//        map.put("date", "2016-10-11");
    }

    @Override
    protected Class<TeaOrder> getTClass() {
        return TeaOrder.class;
    }

    @Override
    protected boolean isOnlyInitOne() {
        return false;
    }

    @Override
    protected boolean isCanFirstInitData() {
        return TextUtils.isEmpty(type);
    }
}
