package client.ediancha.com.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.entity.TeaSpaceDesc;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaOtherRecommendAdapter extends SingleBaseAdapter<TeaSpaceDesc.BaoXiang> {

    public TeaOtherRecommendAdapter(Context ctx, List<TeaSpaceDesc.BaoXiang> list) {
        super(ctx, list);
    }

    public TeaOtherRecommendAdapter(List<TeaSpaceDesc.BaoXiang> list) {
        super(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TeaSpaceDescRecommendViewHolder(View.inflate(ctx, R.layout.item_other_tea_package, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        TeaSpaceDescRecommendViewHolder mHolder = (TeaSpaceDescRecommendViewHolder) holder;
        TeaSpaceDesc.BaoXiang data = list.get(position);
        Glide.with(ctx).load(data.images).into(mHolder.iv_img);
        mHolder.tv_title.setText(data.name);
        mHolder.tv_count.setText("可容纳" + data.renshu + "人");
        mHolder.tv_price.setText(TextUtils.equals("免费", data.price) ? "免费" : "￥" + data.price);
    }

    public static class TeaSpaceDescRecommendViewHolder extends BaseViewHolder {
        public ImageView iv_img;
        public TextView tv_title;
        public TextView tv_price;
        public TextView tv_count;


        public TeaSpaceDescRecommendViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_count = (TextView) itemView.findViewById(R.id.tv_count);
        }

    }
}
