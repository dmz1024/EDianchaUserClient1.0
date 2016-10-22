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
import client.ediancha.com.entity.TeaSpaceDesc;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaSpaceDescRecommendAdapter extends SingleBaseAdapter<TeaSpaceDesc.BaoXiang> {

    public TeaSpaceDescRecommendAdapter(Context ctx, List<TeaSpaceDesc.BaoXiang> list) {
        super(ctx, list);
    }

    public TeaSpaceDescRecommendAdapter(List<TeaSpaceDesc.BaoXiang> list) {
        super(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TeaSpaceDescRecommendViewHolder(View.inflate(ctx, R.layout.item_tea_space_desc_recommend, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        TeaSpaceDescRecommendViewHolder mHolder = (TeaSpaceDescRecommendViewHolder) holder;
        TeaSpaceDesc.BaoXiang data = list.get(position);
        Glide.with(ctx).load(data.images).into(mHolder.iv_img);
        mHolder.tv_title.setText(data.name);

    }

    public static class TeaSpaceDescRecommendViewHolder extends BaseViewHolder {
        public ImageView iv_img;
        public TextView tv_title;


        public TeaSpaceDescRecommendViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
        }

    }
}
