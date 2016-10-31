package client.ediancha.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.activity.OrderDescActivity;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.TeaOrder;
import client.ediancha.com.myview.Color2Text;
import client.ediancha.com.myview.TextImage;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaOrderAdapter extends SingleBaseAdapter<TeaOrder.Data> {
    Map<Integer, Boolean> map = new HashMap<>();

    public TeaOrderAdapter(Context ctx, List<TeaOrder.Data> list) {
        super(ctx, list);
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TeaOrderViewHolder(View.inflate(ctx, R.layout.item_tea_order, null));
    }

    /**
     * 1、待付款 2、待发货 3、待收货 4、已完成
     *
     * @param holder
     * @param position
     */

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        TeaOrder.Data data = list.get(position);
        TeaOrderViewHolder mHolder = (TeaOrderViewHolder) holder;
//        mHolder.tv_name.setText(data.title);
        mHolder.tv_state.setText(getStatu(data.status, mHolder.bt_left, mHolder.bt_right));
        mHolder.tv_total_price.setTextNotChange("￥" + data.total);
        if (map.containsKey(position)) {
            map.put(position, false);
        } else {
            map.put(position, true);
        }
        creatShopView(mHolder.rv_shop, data.order_product_list, map.get(position));
    }

    private void creatShopView(RecyclerView rv_shop, List<TeaOrder.OrderProduct> casts, boolean show) {
        LinearLayoutManager manager = new LinearLayoutManager(ctx);
        TeaOrderShopAdapter adapter = new TeaOrderShopAdapter(ctx, casts);
        if (show) {
            rv_shop.addItemDecoration(new ItemDecoration(ctx, LinearLayout.VERTICAL, 1, "#ebebeb"));
        }

        rv_shop.setLayoutManager(manager);
        rv_shop.setAdapter(adapter);
    }

    private String getStatu(int status, Button left, Button right) {
        left.setVisibility(View.GONE);
        right.setVisibility(View.GONE);
        switch (status) {
            case 0:
                return "临时订单";
            case 1:
                left.setVisibility(View.VISIBLE);
                right.setVisibility(View.VISIBLE);
                right.setText("付款");
                left.setText("取消");
                return "未支付";
            case 2:
                return "未发货";
            case 3:
                right.setVisibility(View.VISIBLE);
                right.setText("查看物流");
                return "已发货";
            case 4:
                return "已完成";
            case 5:
                return "已取消";
            case 6:
                return "退款中";
        }
        return "";
    }


    public class TeaOrderViewHolder extends BaseViewHolder {
        public RecyclerView rv_shop;
        public Color2Text tv_total_price;
        public Button bt_right;
        public Button bt_left;
        public TextView tv_state;
        public View view_content;

        public TeaOrderViewHolder(View itemView) {
            super(itemView);
            rv_shop = (RecyclerView) itemView.findViewById(R.id.rv_shop);
            tv_total_price = (Color2Text) itemView.findViewById(R.id.tv_total_price);
            bt_right = (Button) itemView.findViewById(R.id.bt_right);
            bt_left = (Button) itemView.findViewById(R.id.bt_left);
            bt_left = (Button) itemView.findViewById(R.id.bt_left);
            tv_state = (TextView) itemView.findViewById(R.id.tv_state);
            view_content = itemView.findViewById(R.id.view_content);
            view_content.setOnClickListener(this);

        }

        @Override
        protected void onClick(int layoutPosition) {
            Intent intent = new Intent(ctx, OrderDescActivity.class);
            intent.putExtra("data", list.get(layoutPosition));
            ctx.startActivity(intent);
        }

        @Override
        protected void itemOnclick(int layoutPosition) {
            onClick(layoutPosition);
        }
    }


}
