package client.ediancha.com.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.activity.AddressActivity;
import client.ediancha.com.adapter.TeaOrderShopAdapter;
import client.ediancha.com.api.MyRetrofitUtil;
import client.ediancha.com.base.SingleNetWorkBaseFragment;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.entity.Address;
import client.ediancha.com.entity.BaseEntity;
import client.ediancha.com.entity.OrderInfo;
import client.ediancha.com.entity.Postage;
import client.ediancha.com.entity.TeaOrder;
import client.ediancha.com.myview.ChooseStringView;
import client.ediancha.com.processor.PayInfoUtil;
import client.ediancha.com.util.MyToast;

/**
 * Created by dengmingzhi on 2016/10/26.
 */

public class BuyTeaFragment extends SingleNetWorkBaseFragment<OrderInfo> {
    private TextView tv_link_name;
    private TextView tv_link_address;
    private String order_no;
    private OrderInfo orderInfo;
    private EditText et_liuyan;
    private TextView tv_price;
    private TextView tv_total_price;
    private TextView tv_link_add;
    private RecyclerView rv_buy;
    private Button bt_pay;

    public static BuyTeaFragment getInstance(String order_no) {
        Bundle bundle = new Bundle();
        bundle.putString("order_no", order_no);
        BuyTeaFragment buyTeaFragment = new BuyTeaFragment();
        buyTeaFragment.setArguments(bundle);
        return buyTeaFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        order_no = getArguments().getString("order_no");
    }

    @Override
    protected void writeData(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
        fillLink(orderInfo.data.data2);
        fillMoney(orderInfo.data.data3);
        fillShop(orderInfo.data.data1);

    }


    private void fillShop(List<TeaOrder.OrderProduct> data1) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        TeaOrderShopAdapter adapter = new TeaOrderShopAdapter(getContext(), data1);
        rv_buy.setLayoutManager(manager);
        rv_buy.setAdapter(adapter);
    }

    /**
     * 填写价格信息
     *
     * @param data3
     */
    private void fillMoney(OrderInfo.Data3 data3) {
        tv_price.setText("￥" + data3.sale_total + "+￥" + data3.postage + "(运费)");
        tv_total_price.setText("共计：￥" + data3.total);
    }

    /**
     * 填写联系人
     *
     * @param data2
     */
    private void fillLink(OrderInfo.Data2 data2) {
        if (TextUtils.equals(data2.msg, "none")) {
            tv_link_add.setVisibility(View.VISIBLE);
        } else {
            tv_link_name.setText("收货人：" + data2.name + "  " + data2.tel);
            tv_link_address.setText("地址：" + data2.province_txt + data2.city_txt + data2.area_txt + data2.address);
        }
    }

    @Override
    protected String getUrl() {
        return "app.php";
    }

    @Override
    protected Map<String, String> getMap() {
        map.put("c", "appcart");
        map.put("a", "order");
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);
        map.put("order_no", order_no);
        return map;
    }

    @Override
    protected Class<OrderInfo> getTClass() {
        return OrderInfo.class;
    }

//    @Override
//    protected ShowCurrentViewENUM getDefaultView() {
//        return ShowCurrentViewENUM.VIEW_IS_LOADING;
//    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_buy_tea, null);

        bt_pay = (Button) view.findViewById(R.id.bt_pay);
        et_liuyan = (EditText) view.findViewById(R.id.et_liuyan);
        RelativeLayout rl_link = (RelativeLayout) view.findViewById(R.id.rl_link);
        tv_link_name = (TextView) view.findViewById(R.id.tv_link_name);
        tv_link_address = (TextView) view.findViewById(R.id.tv_link_address);
        tv_price = (TextView) view.findViewById(R.id.tv_price);
        tv_total_price = (TextView) view.findViewById(R.id.tv_total_price);
        tv_link_add = (TextView) view.findViewById(R.id.tv_link_add);
        rv_buy = (RecyclerView) view.findViewById(R.id.rv_buy);
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
        list.add("支付宝支付");
        new ChooseStringView(getContext(), list) {
            @Override
            protected void itemClick(int position) {
                bt_pay.setText("正在请求支付信息...");
                if (position == 0) {
                    PayInfoUtil.getInstance().setContext(getContext()).setOnPayInfoListener(new PayInfoUtil.OnPayInfoListener() {
                        @Override
                        public void ok() {
                            bt_pay.setText("正在调用支付端...");
                        }

                        @Override
                        public void faile() {
                            setBtpayEnable(true);
                        }
                    }).getWechatPayId(order_no, et_liuyan.getText().toString());

                } else if (position == 1) {
                    PayInfoUtil.getInstance().setContext(getContext()).setOnPayInfoListener(new PayInfoUtil.OnPayInfoListener() {
                        @Override
                        public void ok() {
                            bt_pay.setText("正在调用支付端...");
                        }

                        @Override
                        public void faile() {
                            setBtpayEnable(true);
                        }
                    }).getAliPayId(order_no, et_liuyan.getText().toString());
                }
                setBtpayEnable(false);
            }
        }.showAtLocation();
    }

    public void setBtpayEnable(boolean enable) {
        if (bt_pay != null) {
            bt_pay.setEnabled(enable);
            if (enable) {
                bt_pay.setText("去支付");
            }
        }
    }

    @Override
    public boolean isCanRefresh() {
        return false;
    }

    @Override
    protected boolean getCanRefresh() {
        return false;
    }

    private Address.Data address;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.ADDRESS_CHOOSW && data != null) {
            tv_link_add.setVisibility(View.GONE);
            address = (Address.Data) data.getSerializableExtra("address");
            orderInfo.data.data2.address_id = address.address_id;
            orderInfo.data.data2.address = address.address;
            orderInfo.data.data2.name = address.name;
            orderInfo.data.data2.tel = address.tel;
            orderInfo.data.data2.province = address.province;
            orderInfo.data.data2.city = address.city;
            orderInfo.data.data2.area = address.area;
            orderInfo.data.data2.add_time = address.add_time;
            orderInfo.data.data2.province_txt = address.province_txt;
            orderInfo.data.data2.city_txt = address.city_txt;
            orderInfo.data.data2.area_txt = address.area_txt;
            fillLink(orderInfo.data.data2);
            getPostage(orderInfo.data.data2.address_id);
        }
    }

    /**
     * 获取邮费
     *
     * @param address_id
     */
    private void getPostage(String address_id) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);
        map.put("order_no", order_no);
        map.put("address_id", address_id);
        map.put("address_id", address_id);
        map.put("c", "myorder");
        map.put("a", "postage");

        MyRetrofitUtil.getInstance().post("app.php", map, Postage.class, new MyRetrofitUtil.OnRequestListener<Postage>() {
            @Override
            public void noNetwork() {

            }

            @Override
            public void serverErr() {

            }

            @Override
            public void haveData(Postage pastage) {
                if (pastage.result == 0) {
                    fillMoney(pastage.data3);
                } else {
                    MyToast.showToast("邮费获取失败");
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void resultNo0(String s) {

            }

            @Override
            public void start() {

            }
        }, "正在获取邮费...", getContext());
    }
}
