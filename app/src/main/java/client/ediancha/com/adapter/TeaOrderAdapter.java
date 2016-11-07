package client.ediancha.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import client.ediancha.com.activity.ShowLogisticsActivity;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.TeaOrder;
import client.ediancha.com.interfaces.OnResultListener;
import client.ediancha.com.myview.Color2Text;
import client.ediancha.com.myview.TextImage;
import client.ediancha.com.processor.OrderUtil;

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
     * @param holder
     * @param position
     */

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        TeaOrder.Data data = list.get(position);
        TeaOrderViewHolder mHolder = (TeaOrderViewHolder) holder;
        mHolder.tv_state.setText(getStatu(data.status, mHolder.bt_left, mHolder.bt_right, data.is_comment == 0, data.is_return == 0));
        mHolder.tv_total_price.setTextNotChange("￥" + data.total);
        if (map.containsKey(position)) {
            map.put(position, false);
        } else {
            map.put(position, true);
        }
        mHolder.tv_store_name.setText(data.store_name);
        mHolder.tv_sn.setText("订单编号：" + data.order_no);
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

    private String getStatu(int status, Button left, Button right, boolean pingjia, boolean back) {
        left.setVisibility(View.GONE);
        right.setVisibility(View.GONE);
        switch (status) {
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
                right.setText("确认收货");
                left.setVisibility(View.VISIBLE);
                left.setText("查看物流");
                return "已发货";
            case 4:
                if (pingjia) {
                    right.setVisibility(View.VISIBLE);
                    right.setText("评价");

                }
                return "已完成";
            case 7:
                if (pingjia) {
                    right.setVisibility(View.VISIBLE);
                    right.setText("评价");
                    if (back) {
                        left.setVisibility(View.VISIBLE);
                        left.setText("退货");
                    }
                } else {
                    if (back) {
                        right.setVisibility(View.VISIBLE);
                        right.setText("退货");
                    }
                }
                return "已完成";
            case 5:
                return "已取消";
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
        public TextView tv_sn;
        public TextImage tv_store_name;

        public TeaOrderViewHolder(View itemView) {
            super(itemView);
            rv_shop = (RecyclerView) itemView.findViewById(R.id.rv_shop);
            tv_total_price = (Color2Text) itemView.findViewById(R.id.tv_total_price);
            bt_right = (Button) itemView.findViewById(R.id.bt_right);
            bt_left = (Button) itemView.findViewById(R.id.bt_left);
            bt_left = (Button) itemView.findViewById(R.id.bt_left);
            tv_state = (TextView) itemView.findViewById(R.id.tv_state);
            tv_sn = (TextView) itemView.findViewById(R.id.tv_sn);
            view_content = itemView.findViewById(R.id.view_content);
            tv_store_name = (TextImage) itemView.findViewById(R.id.tv_store_name);
            view_content.setOnClickListener(this);
            bt_left.setOnClickListener(this);
            bt_right.setOnClickListener(this);

        }

        @Override
        protected void onClick(int layoutPosition) {
            Intent intent = new Intent(ctx, OrderDescActivity.class);
            intent.putExtra("data", list.get(layoutPosition));
            ctx.startActivity(intent);
        }

        @Override
        protected void itemOnclick(int id, int layoutPosition) {
            switch (id) {
                case R.id.view_content:
                    onClick(layoutPosition);
                    break;
                case R.id.bt_left:
                    left(layoutPosition);
                    break;
                case R.id.bt_right:
                    right(layoutPosition);
                    break;
            }
        }
    }


    private void right(int layoutPosition) {
        int status = list.get(layoutPosition).status;
        switch (status) {
            case 1:
                pay(layoutPosition);
                break;
            case 3:
                receive(layoutPosition);
                break;
            case 4:
                if (list.get(layoutPosition).is_comment == 0) {
                    pingjia(layoutPosition);
                }
                break;
            case 7:
                if (list.get(layoutPosition).is_comment == 0) {
                    pingjia(layoutPosition);
                } else {
                    back(layoutPosition);
                }
                break;
        }
    }

    /**
     * 退货
     *
     * @param layoutPosition
     */
    protected void back(int layoutPosition) {

    }

    /**
     * 确认收货
     *
     * @param layoutPosition
     */
    private void receive(final int layoutPosition) {
        OrderUtil.getInstance().setContext(ctx).setOnResultListener(new OnResultListener() {
            @Override
            public void resultOk() {
                remove(layoutPosition);
            }

            @Override
            public void resultFaile() {

            }
        }).receive("YDC" + list.get(layoutPosition).order_no);
    }

    protected void pay(int layoutPosition) {

    }

    private void left(final int layoutPosition) {
        int status = list.get(layoutPosition).status;
        switch (status) {
            case 1:
                OrderUtil.getInstance().setContext(ctx).setOnResultListener(new OnResultListener() {
                    @Override
                    public void resultOk() {
                        remove(layoutPosition);
                    }

                    @Override
                    public void resultFaile() {

                    }
                }).cancle(list.get(layoutPosition).order_id, "1");
                break;

            case 3:
                Intent intent = new Intent(ctx, ShowLogisticsActivity.class);
                intent.putExtra("id", list.get(layoutPosition).order_no);
                ctx.startActivity(intent);
                break;
            case 4:
            case 7:
                Log.d("退货", "true");
                back(layoutPosition);
                break;
        }
    }

    protected void pingjia(int layoutPosition) {

    }


    public void ChangeRemove(int layoutPosition) {
        remove(layoutPosition);
    }


}
