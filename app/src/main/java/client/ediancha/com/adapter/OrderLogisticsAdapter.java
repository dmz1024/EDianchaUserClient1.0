package client.ediancha.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.DescBaseActivity;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.entity.OrderLogistics;
import client.ediancha.com.entity.OtherStore;
import client.ediancha.com.util.GlideUtil;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class OrderLogisticsAdapter extends SingleBaseAdapter<OrderLogistics.Content> {

    public OrderLogisticsAdapter(Context ctx, List<OrderLogistics.Content> list) {
        super(ctx, list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderLogisticsHolder(View.inflate(ctx, R.layout.item_order_logisitics, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        OrderLogisticsHolder mHolder = (OrderLogisticsHolder) holder;
        OrderLogistics.Content data = list.get(position);
        if (position == 0) {
            mHolder.tv_content.setTextColor(ctx.getResources().getColor(R.color.color51b338));
            mHolder.tv_time.setTextColor(ctx.getResources().getColor(R.color.color51b338));
            Glide.with(ctx).load(R.mipmap.icon_level_checked).into(mHolder.iv_img);

        } else {
            mHolder.tv_content.setTextColor(ctx.getResources().getColor(R.color.color999));
            mHolder.tv_time.setTextColor(ctx.getResources().getColor(R.color.color999));
            Glide.with(ctx).load(R.mipmap.icon_level_check).into(mHolder.iv_img);
        }

        mHolder.tv_content.setText(data.context);
        mHolder.tv_time.setText(data.time);

    }

    public class OrderLogisticsHolder extends BaseViewHolder {
        public TextView tv_content;
        public TextView tv_time;
        public ImageView iv_img;
        public View view_1;

        public OrderLogisticsHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            view_1 = itemView.findViewById(R.id.view_1);
        }
    }
}
