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
import client.ediancha.com.entity.TeaEventDesc;
import client.ediancha.com.myview.TextImage;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaEventDescEventAdapter extends SingleBaseAdapter<TeaEventDesc.Event> {

    public TeaEventDescEventAdapter(Context ctx, List<TeaEventDesc.Event> list) {
        super(ctx, list);
    }

    public TeaEventDescEventAdapter(List<TeaEventDesc.Event> list) {
        super(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TeaEventDescEventViewHolder(View.inflate(ctx, R.layout.item_tea_event_desc_event, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        TeaEventDescEventViewHolder mHolder = (TeaEventDescEventViewHolder) holder;
        TeaEventDesc.Event data = list.get(position);
        Glide.with(ctx).load(data.image).into(mHolder.iv_img);
        mHolder.tv_title.setText(data.title);

    }

    public static class TeaEventDescEventViewHolder extends BaseViewHolder {
        public ImageView iv_img;
        public TextView tv_title;
        public TextImage tv_name;
        public TextImage tv_time;


        public TeaEventDescEventViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_name = (TextImage) itemView.findViewById(R.id.tv_name);
            tv_time = (TextImage) itemView.findViewById(R.id.tv_time);
        }

    }
}
