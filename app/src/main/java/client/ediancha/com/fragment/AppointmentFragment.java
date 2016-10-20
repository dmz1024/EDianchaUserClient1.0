package client.ediancha.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import java.util.List;
import java.util.Map;

import client.ediancha.com.adapter.AppointmentAdapter;
import client.ediancha.com.base.ListNetWorkBaseFragment;
import client.ediancha.com.entity.Appointment;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class AppointmentFragment extends ListNetWorkBaseFragment<Appointment.Data, Appointment> {
    public String type = "";

    public static AppointmentFragment getInstance(String type) {
        AppointmentFragment teaOrderFragment = new AppointmentFragment();
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
    protected RecyclerView.Adapter getAdapter(List<Appointment.Data> totalList) {
        return new AppointmentAdapter(getContext(), totalList);
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
    protected Class<Appointment> getTClass() {
        return Appointment.class;
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
