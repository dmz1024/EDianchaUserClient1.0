package client.ediancha.com.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.activity.SetActivity;
import client.ediancha.com.adapter.MyCenterIconAdapter;
import client.ediancha.com.base.NetworkBaseFragment;
import client.ediancha.com.base.SingleNetWorkBaseFragment;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.MyCenterIcon;
import client.ediancha.com.entity.MyCenterInfo;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 16/10/11.
 */

public class MyCenterFragment extends SingleNetWorkBaseFragment<MyCenterInfo> {
    private TextView tv_name;
    @Override
    protected String getUrl() {
        return "top250";
    }

    @Override
    protected Map<String, String> getMap() {
        map.put("start", "0");
        map.put("count", "10");
        return map;
    }


    @Override
    protected ShowCurrentViewENUM getDefaultView() {
        return ShowCurrentViewENUM.VIEW_HAVE_DATA;
    }

    @Override
    protected Class<MyCenterInfo> getTClass() {
        return MyCenterInfo.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = inflateView(R.layout.fragment_my_center);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_center);
        ImageView iv_bg = (ImageView) view.findViewById(R.id.iv_bg);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_bg.getLayoutParams();
        layoutParams.height = Util.getWidth() / 2 + 40;
        iv_bg.setLayoutParams(layoutParams);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.addItemDecoration(new ItemDecoration(getContext(), LinearLayout.HORIZONTAL, 1, "#e1e1e1"));
        MyCenterIconAdapter adapter = new MyCenterIconAdapter(getContext(), getMyCenterIconData());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        View view_set = view.findViewById(R.id.view_set);
        view_set.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.view_set:
                Util.skip(getActivity(), SetActivity.class);
                break;
        }
    }


    private List<MyCenterIcon> getMyCenterIconData() {
        MyCenterIcon m1 = new MyCenterIcon("茶品订单", 0, R.mipmap.center_tea_order, "client.ediancha.com.activity.TeaOrderActivity");
        MyCenterIcon m2 = new MyCenterIcon("预约订单", 150, R.mipmap.center_appointment_order, "client.ediancha.com.activity.AppointmentActivity");
        MyCenterIcon m3 = new MyCenterIcon("报名", 0, R.mipmap.center_apply, "client.ediancha.com.activity.ApplyOrderActivity");
        MyCenterIcon m4 = new MyCenterIcon("喜欢", 0, R.mipmap.center_like, "client.ediancha.com.activity.LikeActivity");
        MyCenterIcon m5 = new MyCenterIcon("购物车", 10, R.mipmap.center_buy_car, "client.ediancha.com.activity.MainActivity");
        MyCenterIcon m6 = new MyCenterIcon("收货地址", 0, R.mipmap.center_address, "client.ediancha.com.activity.MainActivity");
        List<MyCenterIcon> list = new ArrayList<>();
        list.add(m1);
        list.add(m2);
        list.add(m3);
        list.add(m4);
        list.add(m5);
        list.add(m6);
        return list;
    }

    @Override
    protected void writeData(MyCenterInfo t) {
        tv_name.setText(t.count + "");
    }


}
