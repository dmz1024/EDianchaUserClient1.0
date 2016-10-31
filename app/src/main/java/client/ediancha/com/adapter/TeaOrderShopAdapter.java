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
import client.ediancha.com.util.GlideUtil;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaOrderShopAdapter extends SingleBaseAdapter<TeaOrder.OrderProduct> {

    public TeaOrderShopAdapter(Context ctx, List<TeaOrder.OrderProduct> list) {
        super(ctx, list);
    }

    public TeaOrderShopAdapter(List<TeaOrder.OrderProduct> list) {
        super(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TeaOrderShopViewHolder(View.inflate(ctx, R.layout.item_tea_order_shop, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        TeaOrderShopViewHolder mHolder = (TeaOrderShopViewHolder) holder;
        TeaOrder.OrderProduct product = list.get(position);
        GlideUtil.GlideErrAndOc(ctx, product.image, mHolder.iv_img);
        mHolder.tv_name.setText(product.name);
        mHolder.tv_now_price.setText("￥" + product.pro_price);
        mHolder.tv_count.setText("数量x" + product.pro_num);
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
