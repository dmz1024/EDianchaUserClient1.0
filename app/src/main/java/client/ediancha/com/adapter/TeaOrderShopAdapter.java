package client.ediancha.com.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.entity.TeaOrder;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaOrderShopAdapter extends SingleBaseAdapter<TeaOrder.Casts> {

    public TeaOrderShopAdapter(Context ctx, List<TeaOrder.Casts> list) {
        super(ctx, list);
    }

    public TeaOrderShopAdapter(List<TeaOrder.Casts> list) {
        super(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TeaOrderShopViewHolder(View.inflate(ctx, R.layout.item_tea_order_shop, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        TeaOrderShopViewHolder mHolder = (TeaOrderShopViewHolder) holder;
        TeaOrder.Casts cast = list.get(position);
        Glide.with(ctx).load(Constant.IMAGE).into(mHolder.iv_img);
        mHolder.tv_name.setText(cast.name);
        mHolder.tv_count.setText("X" + cast.id);
        mHolder.tv_now_price.setText("￥" + cast.id);
    }

    public static class TeaOrderShopViewHolder extends BaseViewHolder {
        public ImageView iv_img;
        public TextView tv_name;
        public TextView tv_now_price;
        public TextView tv_count;

        public TeaOrderShopViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_now_price = (TextView) itemView.findViewById(R.id.tv_now_price);
            tv_count = (TextView) itemView.findViewById(R.id.tv_count);
        }

        @Override
        protected boolean isCanOnclick() {
            return false;
        }
    }
}
