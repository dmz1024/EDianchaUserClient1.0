package client.ediancha.com.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.entity.TeaEventDesc;
import client.ediancha.com.entity.TeaSpaceDesc;
import client.ediancha.com.myview.GlideCircleTransform;
import client.ediancha.com.myview.MyRatingBar;
import client.ediancha.com.myview.RatingBar;
import client.ediancha.com.myview.TextImage;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class EvaluateAdapter extends SingleBaseAdapter<TeaSpaceDesc.Comment> {

    public EvaluateAdapter(Context ctx, List<TeaSpaceDesc.Comment> list) {
        super(ctx, list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EvaluateViewHolder(View.inflate(ctx, R.layout.item_evaluate, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        EvaluateViewHolder mHolder = (EvaluateViewHolder) holder;
        TeaSpaceDesc.Comment data = list.get(position);
        Glide.with(ctx).load(data.avatar).transform(new GlideCircleTransform(ctx)).into(mHolder.iv_head);

        List<String> urls = new ArrayList<>();
        for (int i = 0; i < data.attachment_list.size(); i++) {
            urls.add(data.attachment_list.get(i).file);
        }
        GridLayoutManager manage = new GridLayoutManager(ctx, 4);
        mHolder.rv_image.setLayoutManager(manage);
        mHolder.rv_image.setAdapter(new ImageAdapter(ctx, urls));

        mHolder.tv_name.setText(data.nickname);
        mHolder.tv_time.setText(data.date);
        mHolder.tv_ratingbar.setText(data.score + "分");
        mHolder.tv_content.setText(data.content);


    }

    public static class EvaluateViewHolder extends BaseViewHolder {
        public ImageView iv_head;
        public TextView tv_name;
        public TextView tv_time;
        public TextView tv_ratingbar;
        public TextView tv_price;
        public TextView tv_content;
        public RecyclerView rv_image;
        public MyRatingBar ratingbar;


        public EvaluateViewHolder(View itemView) {
            super(itemView);
            iv_head = (ImageView) itemView.findViewById(R.id.iv_head);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_ratingbar = (TextView) itemView.findViewById(R.id.tv_ratingbar);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            rv_image = (RecyclerView) itemView.findViewById(R.id.rv_image);
            ratingbar = (MyRatingBar) itemView.findViewById(R.id.ratingbar);
        }

    }
}
