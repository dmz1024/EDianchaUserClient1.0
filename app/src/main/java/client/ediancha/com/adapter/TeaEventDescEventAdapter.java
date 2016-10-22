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
import client.ediancha.com.entity.TeaEventDesc;
import client.ediancha.com.myview.TextImage;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaEventDescEventAdapter extends SingleBaseAdapter<TeaEventDesc.Lists> {

    public TeaEventDescEventAdapter(Context ctx, List<TeaEventDesc.Lists> list) {
        super(ctx, list);
    }

    public TeaEventDescEventAdapter(List<TeaEventDesc.Lists> list) {
        super(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TeaEventDescEventViewHolder(View.inflate(ctx, R.layout.item_tea_event_desc_event, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        TeaEventDescEventViewHolder mHolder = (TeaEventDescEventViewHolder) holder;
        TeaEventDesc.Lists data = list.get(position);
        Glide.with(ctx).load(data.images).into(mHolder.iv_img);
        mHolder.tv_title.setText(data.name);
        mHolder.tv_content.setText(data.desc);
        mHolder.tv_name.setText(data.storename);
        mHolder.tv_time.setText(data.sttime + " -- " + data.endtime);

    }

    public class TeaEventDescEventViewHolder extends BaseViewHolder {
        public ImageView iv_img;
        public TextView tv_title;
        public TextImage tv_name;
        public TextImage tv_time;
        public TextView tv_content;


        public TeaEventDescEventViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_name = (TextImage) itemView.findViewById(R.id.tv_name);
            tv_time = (TextImage) itemView.findViewById(R.id.tv_time);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
        }

        @Override
        protected void onClick(int layoutPosition) {
            Intent intent = new Intent(ctx, DescBaseActivity.class);
            intent.putExtra("title", list.get(layoutPosition).name);
            intent.putExtra("id", list.get(layoutPosition).pigcms_id);
            intent.putExtra("type", 3);
            ctx.startActivity(intent);

        }
    }
}
