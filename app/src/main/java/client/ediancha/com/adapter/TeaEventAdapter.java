package client.ediancha.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.activity.TeaEventDescActivity;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.entity.TeaEvent;
import client.ediancha.com.myview.GlideCircleTransform;
import client.ediancha.com.myview.TextImage;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaEventAdapter extends SingleBaseAdapter<TeaEvent.Data> {
    private static final int VIEW_SHOW_TYPE_FILTER = 0;
    private static final int VIEW_SHOW_CONTENT = 1;

    public TeaEventAdapter(Context ctx, List<TeaEvent.Data> list) {
        super(ctx, list);
    }

    public TeaEventAdapter(List<TeaEvent.Data> list) {
        super(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_SHOW_CONTENT) {
            return new TeaEventViewHolder(View.inflate(ctx, R.layout.item_tea_event_content, null));
        }
        return new TeaEventTypeViewHolder(View.inflate(ctx, R.layout.item_recyclerview, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder instanceof TeaEventViewHolder) {
            TeaEventViewHolder mHolder = (TeaEventViewHolder) holder;
            TeaEvent.Data data = list.get(position - 1);
            Glide.with(ctx).load(data.images).into(mHolder.iv_img);
            Glide.with(ctx).load(data.logo).transform(new GlideCircleTransform(ctx)).into(mHolder.iv_logo);
            mHolder.tv_title.setText(data.name);
            mHolder.tv_time.setText(data.sttime + "至" + data.endtime);
            mHolder.tv_price.setText("￥" + data.price);
        } else {
            TeaEventTypeViewHolder mHolder = (TeaEventTypeViewHolder) holder;
            GridLayoutManager manager = new GridLayoutManager(ctx, 4);
            List<String> types = new ArrayList<>();
            for (int i = 0; i < 13; i++) {
                types.add("友谊茶会");
            }
            TeaEventTypeFilterAdapter mAdapter = new TeaEventTypeFilterAdapter(ctx, types);
            mHolder.rv_type.setLayoutManager(manager);
            mHolder.rv_type.setAdapter(mAdapter);
        }


    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_SHOW_TYPE_FILTER;
        }
        return VIEW_SHOW_CONTENT;
    }

    public class TeaEventViewHolder extends BaseViewHolder {
        public ImageView iv_img;
        public ImageView iv_logo;
        public TextView tv_state;
        public TextView tv_content;
        public TextView tv_title;
        public TextView tv_price;
        public TextImage tv_time;

        public TeaEventViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            iv_logo = (ImageView) itemView.findViewById(R.id.iv_logo);
            tv_state = (TextView) itemView.findViewById(R.id.tv_state);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_time = (TextImage) itemView.findViewById(R.id.tv_time);
        }

        @Override
        protected void onClick(int layoutPosition) {
            Intent intent = new Intent(ctx, TeaEventDescActivity.class);
            intent.putExtra("title", list.get(layoutPosition-1).name);
            intent.putExtra("id", list.get(layoutPosition-1).pigcms_id);
            ctx.startActivity(intent);
        }
    }


    public static class TeaEventTypeViewHolder extends BaseViewHolder {
        public RecyclerView rv_type;

        public TeaEventTypeViewHolder(View itemView) {
            super(itemView);
            rv_type = (RecyclerView) itemView.findViewById(R.id.rv_content);
        }

        @Override
        protected boolean isCanOnclick() {
            return false;
        }
    }

}
