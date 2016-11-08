package client.ediancha.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.adapter.ImageAdapter;
import client.ediancha.com.api.MyRetrofitUtil;
import client.ediancha.com.base.SingleNetWorkBaseFragment;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.entity.BaseEntity;
import client.ediancha.com.entity.WriteBackShopInfo;
import client.ediancha.com.myview.ChooseStringView;
import client.ediancha.com.myview.Color2Text;
import client.ediancha.com.myview.PopEdit;
import client.ediancha.com.util.GlideUtil;
import client.ediancha.com.util.MyToast;

/**
 * Created by dengmingzhi on 2016/11/7.
 */

public class WriteBackShopDescFragment extends SingleNetWorkBaseFragment<WriteBackShopInfo> {
    private TextView tv_state;
    private TextView tv_name;
    private TextView tv_now_price;
    private TextView tv_count;
    private TextView tv_client;
    private TextView tv_business;
    private TextView tv_not;
    private RecyclerView rv_client;
    private LinearLayout ll_client;
    private LinearLayout ll_business;
    private LinearLayout ll_logistics;
    private LinearLayout ll_not;
    private ImageView iv_img;
    private Button bt_submit;
    private Color2Text tv_gs;
    private Color2Text tv_sn;

    private String id;
    private WriteBackShopInfo.Data data;

    public static WriteBackShopDescFragment getInstance(String id) {
        WriteBackShopDescFragment writeBack = new WriteBackShopDescFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        writeBack.setArguments(bundle);
        return writeBack;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getString("id");
    }

    @Override
    protected void writeData(WriteBackShopInfo t) {
        data = t.data;
        ll_business.setVisibility(View.GONE);
        ll_not.setVisibility(View.GONE);
        ll_logistics.setVisibility(View.GONE);
        bt_submit.setVisibility(View.GONE);
        int status = data.status;
        switch (status) {
            case 1:
            case 6:
                break;
            case 3:
//                ll_business.setVisibility(View.VISIBLE);
                ll_logistics.setVisibility(View.VISIBLE);
                bt_submit.setVisibility(View.VISIBLE);
                break;
            case 2:
                ll_business.setVisibility(View.VISIBLE);
                ll_not.setVisibility(View.VISIBLE);
                break;
            case 4:
                ll_business.setVisibility(View.VISIBLE);
                break;
            case 5:
                ll_business.setVisibility(View.VISIBLE);
                break;

        }


        tv_state.setText("申请状态：" + data.status_txt);
        GlideUtil.GlideErrAndOc(getContext(), data.image, iv_img);
        tv_name.setText(data.name);
        tv_now_price.setText("￥" + data.pro_price);
        tv_count.setText("退货数量x" + data.pro_num);
        StringBuffer clientBuffer = new StringBuffer();
        clientBuffer.append("申请时间：").append(data.dateline).append("\n退货类型：").append(data.type_txt)
                .append("\n手机号码：").append(data.phone).append("\n退货理由：").append(data.content);
        tv_client.setText(clientBuffer.toString());

        if (status == 2 || status == 3 || status == 4 || status == 5) {
            StringBuffer businesBuffer = new StringBuffer();
            businesBuffer.append("退款总费用：").append(data.product_money).append("\n收货人姓名：").append(data.address_user)
                    .append("\n收货人电话：").append(data.address_tel).append("\n收货人地址：").append(data.address.province_txt)
                    .append(data.address.county_txt).append(data.address.address);
            tv_business.setText(businesBuffer.toString());
        }

        if (status == 2) {
            StringBuffer notBuffer = new StringBuffer();
            notBuffer.append("不同意时间：").append(data.cancel_dateline).append("\n取消时间：").append(data.user_cancel_dateline)
                    .append("\n不同意原因：").append(data.store_content);
            tv_not.setText(notBuffer.toString());
            bt_submit.setVisibility(View.VISIBLE);
        }

        initImage(data.images);

    }

    private void initImage(List<String> images) {
        GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        ImageAdapter mAdapter = new ImageAdapter(getContext(), images);
        rv_client.setLayoutManager(manager);
        rv_client.setAdapter(mAdapter);

    }

    @Override
    protected String getUrl() {
        return "app.php";
    }

    @Override
    protected Map<String, String> getMap() {
        map.put("c", "myreturn");
        map.put("a", "detail");
        map.put("id", id);
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);
        return map;
    }

    @Override
    protected Class<WriteBackShopInfo> getTClass() {
        return WriteBackShopInfo.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.activity_write_back_shop_info, null);
        tv_state = (TextView) view.findViewById(R.id.tv_state);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_now_price = (TextView) view.findViewById(R.id.tv_now_price);
        ll_logistics = (LinearLayout) view.findViewById(R.id.ll_logistics);
        tv_count = (TextView) view.findViewById(R.id.tv_count);
        tv_client = (TextView) view.findViewById(R.id.tv_client);
        tv_business = (TextView) view.findViewById(R.id.tv_business);
        tv_gs = (Color2Text) view.findViewById(R.id.tv_gs);
        tv_sn = (Color2Text) view.findViewById(R.id.tv_sn);
        rv_client = (RecyclerView) view.findViewById(R.id.rv_client);
        ll_not = (LinearLayout) view.findViewById(R.id.ll_not);
        tv_not = (TextView) view.findViewById(R.id.tv_not);
        ll_client = (LinearLayout) view.findViewById(R.id.ll_client);
        ll_business = (LinearLayout) view.findViewById(R.id.ll_business);
        iv_img = (ImageView) view.findViewById(R.id.iv_img);
        bt_submit = (Button) view.findViewById(R.id.bt_submit);
        bt_submit.setOnClickListener(this);
        tv_sn.setOnClickListener(this);
        tv_gs.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.bt_submit:
                submit();
                break;
            case R.id.tv_sn:
                sn();
                break;
            case R.id.tv_gs:
                gs();
                break;
        }
    }

    private String code;
    private String no;

    private void gs() {
        List<String> list = new ArrayList<>();
        final int count = data.express.size();
        for (int i = 0; i < count; i++) {
            list.add(data.express.get(count - 1 - i).name);
        }
        new ChooseStringView(getContext(), list) {
            @Override
            protected void itemClick(int position) {
                tv_gs.setTextNotChange(data.express.get(count - 1 - position).name);
                code = data.express.get(count - 1 - position).code;
            }
        }.showAtLocation();
    }

    private void sn() {
        new PopEdit(getContext(), false, TextUtils.equals("请填写快递单号", tv_sn.getNowText()) ? "" : tv_sn.getNowText()) {
            @Override
            protected void content(String content) {
                if (TextUtils.equals("请填写快递单号", content) || TextUtils.isEmpty(content)) {
                    tv_sn.setTextNotChange("请填写快递单号");
                    no = "";
                } else {
                    tv_sn.setTextNotChange(content);
                    no = content;
                }
            }
        }.showAtLocation();
    }

    @Override
    protected boolean getCanRefresh() {
        return false;
    }

    @Override
    public boolean isCanRefresh() {
        return false;
    }

    private void submit() {
        if (TextUtils.isEmpty(code)) {
            MyToast.showToast("请选择快递公司");
            return;
        }

        if (TextUtils.isEmpty(no)) {
            MyToast.showToast("请填写物流单号");
            return;
        }


        Map<String, String> subMap = new HashMap<>();
        subMap.put("uid", UserInfo.uid);
        subMap.put("token", UserInfo.token);
        subMap.put("c", "myreturn");
        subMap.put("a", "express");
        subMap.put("return_id", id);

        MyRetrofitUtil.getInstance().post("app.php", map, BaseEntity.class, new MyRetrofitUtil.OnRequestListener<BaseEntity>() {
            @Override
            public void noNetwork() {

            }

            @Override
            public void serverErr() {

            }

            @Override
            public void haveData(BaseEntity baseEntity) {
                if (baseEntity.result == 0) {
                    MyToast.showToast("物流信息提交成功");
                    getActivity().setResult(11, getActivity().getIntent());
                    getActivity().finish();
                } else {
                    MyToast.showToast(baseEntity.msg);
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
        }, "正在提交物流信息", getContext());

    }
}
