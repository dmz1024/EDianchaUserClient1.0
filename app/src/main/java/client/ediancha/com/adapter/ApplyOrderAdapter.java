package client.ediancha.com.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.entity.ApplyOrder;
import client.ediancha.com.entity.TeaOrder;
import client.ediancha.com.myview.TextImage;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class ApplyOrderAdapter extends SingleBaseAdapter<ApplyOrder.Data> {

    public ApplyOrderAdapter(Context ctx, List<ApplyOrder.Data> list) {
        super(ctx, list);
    }

    public ApplyOrderAdapter(List<ApplyOrder.Data> list) {
        super(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ApplyOrderViewHolder(View.inflate(ctx, R.layout.item_apply_order, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        ApplyOrderViewHolder mHolder = (ApplyOrderViewHolder) holder;
        ApplyOrder.Data data = list.get(position);
        Glide.with(ctx).load(data.images.large).into(mHolder.iv_img);
        mHolder.tv_name.setText(data.title);
    }

    public static class ApplyOrderViewHolder extends BaseViewHolder {
        public ImageView iv_img;
        public TextView tv_name;
        public TextView tv_time;
        public TextView tv_state;
        public TextView tv_info;
        public TextView tv_price;
        public Button bt_right;
        public TextImage tv_remaining_time;


        public ApplyOrderViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_state = (TextView) itemView.findViewById(R.id.tv_state);
            tv_info = (TextView) itemView.findViewById(R.id.tv_info);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            bt_right = (Button) itemView.findViewById(R.id.bt_right);
            tv_remaining_time = (TextImage) itemView.findViewById(R.id.tv_remaining_time);
        }

    }
}
