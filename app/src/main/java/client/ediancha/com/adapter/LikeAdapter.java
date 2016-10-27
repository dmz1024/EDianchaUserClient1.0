package client.ediancha.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.activity.TeaDescActivity;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.DescBaseActivity;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.entity.Like;
import client.ediancha.com.interfaces.OnResultListener;
import client.ediancha.com.processor.CollectionUtil;
import client.ediancha.com.util.GlideUtil;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class LikeAdapter extends SingleBaseAdapter<Like.Data> {
    private String type;

    public LikeAdapter(Context ctx, List<Like.Data> list) {
        super(ctx, list);
    }

    public LikeAdapter(Context ctx, List<Like.Data> list, String type) {
        super(ctx, list);
        this.type = type;
    }

    public LikeAdapter(List<Like.Data> list) {
        super(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LikeEventViewHolder(View.inflate(ctx, R.layout.item_like_event, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        LikeEventViewHolder mHolder = (LikeEventViewHolder) holder;
        Like.Data data = list.get(position);
        GlideUtil.GlideErrAndOc(ctx, data.images, mHolder.iv_img);
        mHolder.tv_title.setText(data.name);
    }

    public class LikeEventViewHolder extends BaseViewHolder {
        public ImageView iv_img;
        public TextView tv_title;
        public ImageView iv_like;


        public LikeEventViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            iv_like = (ImageView) itemView.findViewById(R.id.iv_like);
            iv_like.setOnClickListener(this);
        }

        @Override
        protected void itemOnclick(final int layoutPosition) {
            CollectionUtil.getInstance().setCollectType("取消收藏").setOnResultListener(new OnResultListener() {
                @Override
                public void resultOk() {
                    remove(layoutPosition);
                }

                @Override
                public void resultFaile() {

                }
            }).collection(ctx, list.get(layoutPosition).dataid, type);

        }

        @Override
        protected void onClick(int layoutPosition) {
            Intent intent;
            if (TextUtils.equals("4", type) || TextUtils.equals("1", type)) {
                intent = new Intent(ctx, TeaDescActivity.class);
            } else {
                intent = new Intent(ctx, DescBaseActivity.class);
            }
            intent.putExtra("title", list.get(layoutPosition).name);
            intent.putExtra("id", list.get(layoutPosition).dataid);
            intent.putExtra("type", Integer.parseInt(type));
            ctx.startActivity(intent);
        }
    }
}
