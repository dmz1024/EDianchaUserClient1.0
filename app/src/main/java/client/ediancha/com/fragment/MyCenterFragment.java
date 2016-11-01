package client.ediancha.com.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.activity.LoginActivity;
import client.ediancha.com.activity.SetActivity;
import client.ediancha.com.activity.UserInfoActivity;
import client.ediancha.com.adapter.MyCenterIconAdapter;
import client.ediancha.com.base.SingleNetWorkBaseFragment;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.BaseEntity;
import client.ediancha.com.entity.MyCenterIcon;
import client.ediancha.com.entity.MyCenterInfo;
import client.ediancha.com.myview.GlideCircleTransform;
import client.ediancha.com.myview.PopMessageTips;
import client.ediancha.com.util.MyToast;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 16/10/11.
 */

public class MyCenterFragment extends SingleNetWorkBaseFragment<MyCenterInfo> {
    private TextView tv_name;
    private RecyclerView recyclerView;
    private List<MyCenterIcon> list = new ArrayList<>();
    private ImageView iv_head;
    private boolean isLogin = true;

    @Override
    protected String getUrl() {
        return "app.php";
    }

    @Override
    protected Map<String, String> getMap() {
        map.put("c", "home");
        map.put("a", "index");
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);
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
        iv_head = (ImageView) view.findViewById(R.id.iv_head);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_center);
        ImageView iv_bg = (ImageView) view.findViewById(R.id.iv_bg);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_bg.getLayoutParams();
        layoutParams.height = Util.getWidth() / 2 + 40;
        iv_bg.setLayoutParams(layoutParams);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.addItemDecoration(new ItemDecoration(getContext(), LinearLayout.HORIZONTAL, 1, "#e1e1e1"));
        MyCenterIconAdapter adapter = new MyCenterIconAdapter(getContext(), getMyCenterIconData()){
            @Override
            protected void login() {
                startActivityForResult(new Intent(getContext(), LoginActivity.class), Constant.MY_CENTER_INFO);
            }
        };
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        iv_head.setOnClickListener(this);
        if (TextUtils.isEmpty(UserInfo.uid)) {
            isLogin = false;
            tv_name.setText("未登录");

        }
        Glide.with(getActivity()).load(R.mipmap.icon_head).bitmapTransform(new GlideCircleTransform(getContext())).into(iv_head);

        return view;
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_head:
                if (!result && (!isLogin || TextUtils.equals("账号过期", tv_name.getText()))) {
                    startActivityForResult(new Intent(getContext(), LoginActivity.class), Constant.MY_CENTER_INFO);
                } else if (result) {
                    Util.skip(getActivity(), UserInfoActivity.class);
                }
                break;
        }
    }


    private List<MyCenterIcon> getMyCenterIconData() {
        MyCenterIcon m1 = new MyCenterIcon("茶品订单", 0, R.mipmap.center_tea_order, "client.ediancha.com.activity.TeaOrderActivity");
        MyCenterIcon m2 = new MyCenterIcon("预约订单", 0, R.mipmap.center_appointment_order, "client.ediancha.com.activity.AppointmentActivity");
        MyCenterIcon m3 = new MyCenterIcon("报名", 0, R.mipmap.center_apply, "client.ediancha.com.activity.ApplyOrderActivity");
        MyCenterIcon m4 = new MyCenterIcon("喜欢", 0, R.mipmap.center_like, "client.ediancha.com.activity.LikeActivity");
        MyCenterIcon m5 = new MyCenterIcon("购物车", 0, R.mipmap.center_buy_car, "client.ediancha.com.activity.BuyCarActivity");
        MyCenterIcon m6 = new MyCenterIcon("收货地址", 0, R.mipmap.center_address, "client.ediancha.com.activity.AddressActivity");
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
        tv_name.setText(t.data.nickname);
        Glide.with(getActivity()).load(t.data.avatar).bitmapTransform(new GlideCircleTransform(getContext())).into(iv_head);

    }

    @Override
    protected void resultNot0(String t) {
        super.resultNot0(t);
        BaseEntity baseEntity = new Gson().fromJson(t, BaseEntity.class);
        if (baseEntity.msg.contains("token")) {
            tv_name.setText("账号过期");
            new PopMessageTips("账号信息", "账号信息已过期,请重新登录!", "去登录", "") {
                @Override
                protected void right() {
                    super.right();
                    startActivityForResult(new Intent(getContext(), LoginActivity.class), Constant.MY_CENTER_INFO);
                }
            }.showView(getActivity());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.MY_CENTER_INFO && data != null) {
            getData();
        }
    }

    @Override
    protected boolean isCanFirstInitData() {
        return !TextUtils.isEmpty(UserInfo.uid);
    }


}
