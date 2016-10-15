package client.ediancha.com.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import client.ediancha.com.R;
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


    public TeaOrderAdapter(Context ctx, List<TeaOrder.Data> list) {
        super(ctx, list);
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TeaOrderViewHolder(View.inflate(ctx, R.layout.item_tea_order, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        TeaOrder.Data data = list.get(position);
        TeaOrderViewHolder mHolder = (TeaOrderViewHolder) holder;
        mHolder.tv_name.setText(data.title);
        creatShopView(mHolder.rv_shop, data.casts);
    }

    private void creatShopView(RecyclerView rv_shop, List<TeaOrder.Casts> casts) {
        LinearLayoutManager manager = new LinearLayoutManager(ctx);
        TeaOrderShopAdapter adapter = new TeaOrderShopAdapter(ctx, casts);
        rv_shop.addItemDecoration(new ItemDecoration(ctx, LinearLayout.VERTICAL, 1, "#ebebeb"));
        rv_shop.setLayoutManager(manager);
        rv_shop.setAdapter(adapter);
    }


    public static class TeaOrderViewHolder extends BaseViewHolder {
        public TextImage tv_name;
        public RecyclerView rv_shop;
        public Color2Text tv_total_price;
        public Button bt_right;
        public Button bt_left;
        public TextView tv_state;


        public TeaOrderViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextImage) itemView.findViewById(R.id.tv_name);

            rv_shop = (RecyclerView) itemView.findViewById(R.id.rv_shop);
            tv_total_price = (Color2Text) itemView.findViewById(R.id.tv_total_price);
            bt_right = (Button) itemView.findViewById(R.id.bt_right);
            bt_left = (Button) itemView.findViewById(R.id.bt_left);
            bt_left = (Button) itemView.findViewById(R.id.bt_left);
            tv_state = (TextView) itemView.findViewById(R.id.tv_state);

        }
    }


}
