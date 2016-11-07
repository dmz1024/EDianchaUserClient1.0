package client.ediancha.com.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.adapter.ChooseImageAdapter;
import client.ediancha.com.api.MyRetrofitUtil;
import client.ediancha.com.api.UpImageUtil;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.entity.BaseEntity;
import client.ediancha.com.entity.TeaOrder;
import client.ediancha.com.myview.ChooseStringView;
import client.ediancha.com.myview.Color2Text;
import client.ediancha.com.myview.PopEdit;
import client.ediancha.com.processor.PhotoUtil;
import client.ediancha.com.util.GlideUtil;
import client.ediancha.com.util.MyToast;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 2016/11/7.
 */

public class WriteBackShopActivity extends ToolBarActivity {
    private TextView tv_sn;
    private TextView tv_name;
    private TextView tv_now_price;
    private TextView tv_count;
    private TextView tv_back_count;
    private TextView tv_back_desc;
    private Color2Text tv_back_reason;
    private Color2Text tv_back_tel;
    private RecyclerView rv_image;
    private ImageView iv_img;
    private TeaOrder.OrderProduct data;
    private Button bt_submit;
    private String reason;
    private ArrayList<String> resultUrl;

    @Override
    protected String getToolBarTitle() {
        return "申请退货";
    }

    @Override
    protected void initView() {

        tv_sn = (TextView) findViewById(R.id.tv_sn);
        bt_submit = (Button) findViewById(R.id.bt_submit);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_now_price = (TextView) findViewById(R.id.tv_now_price);
        tv_count = (TextView) findViewById(R.id.tv_count);
        tv_back_count = (TextView) findViewById(R.id.tv_back_count);
        tv_back_desc = (TextView) findViewById(R.id.tv_back_desc);
        tv_back_reason = (Color2Text) findViewById(R.id.tv_back_reason);
        tv_back_tel = (Color2Text) findViewById(R.id.tv_back_tel);
        rv_image = (RecyclerView) findViewById(R.id.rv_image);
        iv_img = (ImageView) findViewById(R.id.iv_img);
        tv_back_desc.setOnClickListener(this);
        tv_back_reason.setOnClickListener(this);
        tv_back_tel.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
        initImage();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back_desc:
                new PopEdit(this, false, TextUtils.equals("请填写退货说明", tv_back_desc.getText().toString()) ? "" : tv_back_desc.getText().toString()) {
                    @Override
                    protected void content(String content) {
                        tv_back_desc.setText(content);
                    }
                }.showAtLocation();
                break;
            case R.id.tv_back_reason:
                reason();
                break;
            case R.id.tv_back_tel:
                new PopEdit(this, false, TextUtils.equals("请填写手机号码", tv_back_tel.getNowText()) ? "" : tv_back_tel.getNowText()) {
                    @Override
                    protected void content(String content) {
                        tv_back_tel.setTextNotChange(content);
                    }
                }.showAtLocation();
                break;
            case R.id.bt_submit:
                submit();
                break;
        }
    }

    private void submit() {
        if (TextUtils.isEmpty(UserInfo.uid)) {
            MyToast.showToast("请先登录!");
            Util.skip(this, LoginActivity.class);
            return;
        }
        if (resultUrl != null && resultUrl.size() > 0) {
            UpImageUtil.getInstance().setContext(this).setOnUpLoadListener(new UpImageUtil.OnUpLoadListener() {
                @Override
                public void urls(List<String> urls) {
                    StringBuffer str = new StringBuffer();
                    for (int i = 0; i < urls.size(); i++) {
                        str.append(urls.get(i));
                        if (i != urls.size() - 1) {
                            str.append(",");
                        }
                    }

                    images = str.toString();
                    add();
                }

                @Override
                public void faile() {

                }
            }).upImage(resultUrl);
        } else {
            add();
        }

    }

    private void add() {
        if (TextUtils.isEmpty(reason)) {
            MyToast.showToast("请选择退货原因");
            return;
        }
        String phone = tv_back_tel.getNowText();
        if (TextUtils.isEmpty(phone)) {
            MyToast.showToast("请填写手机号码");
            return;
        }

        String desc = tv_back_desc.getText().toString();
        if (TextUtils.isEmpty(desc) || TextUtils.equals(desc, "请填写退货说明")) {
            MyToast.showToast("请填写退货说明");
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("c", "myreturn");
        map.put("a", "save");
        map.put("order_no", getIntent().getStringExtra("sn"));
        map.put("pigcms_id", data.pigcms_id);
        map.put("type", reason);
        map.put("phone", phone);
        map.put("content", desc);
        map.put("number", data.pro_num);
        if (!TextUtils.isEmpty(images)) {
            map.put("images", images);
        }

        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);


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
                    MyToast.showToast("申请退货成功");
                    setResult(11, getIntent());
                    finish();
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
        }, "正在提交申请", WriteBackShopActivity.this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (phptoUtil != null) {
            phptoUtil.onActivityResult(requestCode, resultCode, data);
            mAdapter.notifyDataSetChanged();
        }
    }

    private String images;


    /**
     * 退货原因
     */
    private void reason() {
        final List<String> list = new ArrayList<>();
        list.add("买/卖双方协商一致");
        list.add("买错/多买/不想要");
        list.add("商品质量问题");
        list.add("未到货品");
        list.add("其他");
        new ChooseStringView(this, list) {
            @Override
            protected void itemClick(int position) {
                reason = (position + 1) + "";
                tv_back_reason.setTextNotChange(list.get(position));
            }
        }.showAtLocation();
    }

    @Override
    protected void initData() {
        data = (TeaOrder.OrderProduct) getIntent().getSerializableExtra("data");
        tv_sn.setText("订单编号：" + getIntent().getStringExtra("sn"));
        GlideUtil.GlideErrAndOc(this, data.image, iv_img);
        tv_name.setText(data.name);
        tv_now_price.setText(data.pro_price);
        tv_count.setText("数量x" + data.pro_num);
        tv_back_count.setText("退货数量 " + data.pro_num);
    }

    private PhotoUtil phptoUtil;
    private ChooseImageAdapter mAdapter;

    @Override
    protected int getRid() {
        return R.layout.activity_wraite_back_shop;
    }

    private void initImage() {
        resultUrl = new ArrayList<>();
        GridLayoutManager manager = new GridLayoutManager(this, 4);

        mAdapter = new ChooseImageAdapter(this, resultUrl, 4) {
            @Override
            protected void chooseImage() {
                if (phptoUtil == null) {
                    phptoUtil = PhotoUtil.getInstance().setActivity(WriteBackShopActivity.this).setMaxCount(4);
                }
                phptoUtil.setResultUrl(resultUrl);
                phptoUtil.showPhoto();
            }
        };
        rv_image.setLayoutManager(manager);
        rv_image.setAdapter(mAdapter);
    }
}
