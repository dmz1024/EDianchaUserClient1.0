package client.ediancha.com.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import client.ediancha.com.R;
import client.ediancha.com.adapter.TeaOrderShopAdapter;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.TeaOrder;
import client.ediancha.com.myview.Color2Text;

/**
 * Created by dengmingzhi on 2016/10/31.
 */

public class OrderDescActivity extends ToolBarActivity {
    public TextView tv_state;
    public TextView tv_liuyan;
    public TextView tv_price;
    public TextView tv_link_info;
    public TextView tv_price_info;
    public TextView tv_total_price;
    public TextView tv_no;
    public RecyclerView rv_shop;

    @Override
    protected String getToolBarTitle() {
        return "订单详情";
    }

    @Override
    protected void initView() {
        tv_state = (TextView) findViewById(R.id.tv_state);
        tv_liuyan = (TextView) findViewById(R.id.tv_liuyan);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_link_info = (TextView) findViewById(R.id.tv_link_info);
        tv_price_info = (TextView) findViewById(R.id.tv_price_info);
        tv_total_price = (TextView) findViewById(R.id.tv_total_price);
        rv_shop = (RecyclerView) findViewById(R.id.rv_shop);
        tv_no = (TextView) findViewById(R.id.tv_no);
    }

    @Override
    protected void initData() {
        TeaOrder.Data data = (TeaOrder.Data) getIntent().getSerializableExtra("data");
        tv_state.setText(getStatu(data.status));
        LinearLayoutManager manager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        TeaOrderShopAdapter adapter = new TeaOrderShopAdapter(this, data.order_product_list);
        rv_shop.setLayoutManager(manager);
        rv_shop.setAdapter(adapter);

        tv_price.setText("￥" + data.total);
        tv_link_info.setText(data.address_user + "," + data.address_tel + "\n" + data.address.province + data.address.city + data.address.area + data.address.address);
        tv_price_info.setText("￥" + data.sub_total + "￥" + data.postage + "(运费)");
        tv_total_price.setText("共计：￥" + data.total);
        tv_no.setText("订单编号：" + data.order_no + "\n下单时间：" + data.add_time);

        if (TextUtils.isEmpty(data.comment)) {
            tv_liuyan.setText("买家留言：无");
        } else {
            tv_liuyan.setText("买家留言：" + data.comment);
        }
    }

    @Override
    protected int getRid() {
        return R.layout.activity_order_desc;
    }


    private String getStatu(int status) {
        switch (status) {
            case 1:
                return "未支付";
            case 2:
                return "未发货";
            case 3:
                return "已发货";
            case 4:
            case 7:
                return "已完成";
            case 5:
                return "已取消";
        }
        return "";
    }
}
