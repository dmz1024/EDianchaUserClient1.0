package client.ediancha.com.adapter;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.entity.LikeEvent;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class LikeEventAdapter extends SingleBaseAdapter<LikeEvent.Data> {

    public LikeEventAdapter(Context ctx, List<LikeEvent.Data> list) {
        super(ctx, list);
    }

    public LikeEventAdapter(List<LikeEvent.Data> list) {
        super(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LikeEventViewHolder(View.inflate(ctx, R.layout.item_like_event, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        LikeEventViewHolder mHolder = (LikeEventViewHolder) holder;
        LikeEvent.Data data = list.get(position);
        Glide.with(ctx).load(data.images.large).into(mHolder.iv_img);
        mHolder.tv_title.setText(data.title);
    }

    public static class LikeEventViewHolder extends BaseViewHolder {
        public ImageView iv_img;
        public TextView tv_title;
        public FloatingActionButton bt_like;


        public LikeEventViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            bt_like = (FloatingActionButton) itemView.findViewById(R.id.bt_like);
        }

        @Override
        protected boolean isCanOnclick() {
            return false;
        }
    }
}
