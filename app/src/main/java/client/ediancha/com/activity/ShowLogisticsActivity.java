package client.ediancha.com.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.adapter.OrderLogisticsAdapter;
import client.ediancha.com.api.MyRetrofitUtil;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.entity.OrderLogistics;
import client.ediancha.com.myview.ChooseStringView;
import client.ediancha.com.myview.TextImage;
import client.ediancha.com.util.MyToast;

/**
 * Created by dengmingzhi on 2016/11/2.
 */

public class ShowLogisticsActivity extends ToolBarActivity {
    private TextView tv_name;
    private TextView tv_sn;
    private TextImage tv_gs;
    private String id;
    private OrderLogistics orderLogistics;
    private RecyclerView rv_logistics;
    private OrderLogisticsAdapter mAdapter;
    private List<String> list = new ArrayList<>();

    @Override
    protected String getToolBarTitle() {
        return "物流信息";
    }

    @Override
    protected void initView() {
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_sn = (TextView) findViewById(R.id.tv_sn);
        tv_gs = (TextImage) findViewById(R.id.tv_gs);
        id = getIntent().getStringExtra("id");
        rv_logistics = (RecyclerView) findViewById(R.id.rv_logistics);
        tv_gs.setOnClickListener(this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mAdapter = new OrderLogisticsAdapter(this, contents);
        rv_logistics.setLayoutManager(manager);
        rv_logistics.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        new ChooseStringView(this, list) {
            @Override
            protected void itemClick(int position) {
                showLogistics(position);
            }
        }.showAtLocation();
    }

    @Override
    protected void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);
        map.put("order_no", "YDC" + id);
        map.put("c", "myorder");
        map.put("a", "kuaidi");
        MyRetrofitUtil.getInstance().post("app.php", map, OrderLogistics.class, new MyRetrofitUtil.OnRequestListener<OrderLogistics>() {
            @Override
            public void noNetwork() {

            }

            @Override
            public void serverErr() {

            }

            @Override
            public void haveData(OrderLogistics orderLogistics) {
                if (orderLogistics.result == 0) {
                    ShowLogisticsActivity.this.orderLogistics = orderLogistics;
                    for (int i = 0; i < orderLogistics.data.size(); i++) {
                        list.add(orderLogistics.data.get(i).name);
                    }
                    showLogistics(0);
                } else {
                    MyToast.showToast(orderLogistics.msg);
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
        }, "正在获取物流信息", ShowLogisticsActivity.this);
    }

    private List<OrderLogistics.Content> contents = new ArrayList<>();

    /**
     * 对应不同商品查看店铺
     *
     * @param i
     */
    private void showLogistics(int i) {
        OrderLogistics.Data data = orderLogistics.data.get(i);
        tv_name.setText("商品名称：" + data.name);
        tv_sn.setText("物流单号：" + data.express_no);
        tv_gs.setText("物流公司：" + data.express_company);
        contents.clear();
        contents.addAll(data.content);
        mAdapter.notifyDataSetChanged();

    }


    @Override
    protected int getRid() {
        return R.layout.activity_show_logistics;
    }
}
