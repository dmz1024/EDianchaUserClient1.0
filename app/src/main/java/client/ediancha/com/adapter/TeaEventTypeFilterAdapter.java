package client.ediancha.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.activity.TeaEventFilterResultActivity;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.entity.TeaEvent;
import client.ediancha.com.entity.TeaFilter;
import client.ediancha.com.util.GlideUtil;

/**
 * Created by dengmingzhi on 16/10/11.
 */

public class TeaEventTypeFilterAdapter extends SingleBaseAdapter<TeaEvent.Data1> {
    private boolean isHaveMore;
    private boolean isNowMore;
    private boolean isCanMore = true;

    public TeaEventTypeFilterAdapter(Context ctx, List<TeaEvent.Data1> list) {
        super(ctx, list);
    }

    public TeaEventTypeFilterAdapter(List<TeaEvent.Data1> list) {
        super(list);
    }

    @Override
    public TeaEventTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TeaEventTypeViewHolder(View.inflate(ctx, R.layout.item_tea_event_type_filter, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        TeaEventTypeViewHolder mHolder = ((TeaEventTypeViewHolder) holder);
        TeaEvent.Data1 data = list.get(position);
//        R.mipmap.center_tea_order
        if (isHaveMore && !isNowMore) {
            if (position < 7) {
                GlideUtil.GlideErrAndOc(ctx, data.cat_pic, mHolder.iv_icon);
                mHolder.tv_title.setText(data.cat_name);
            } else {
                Glide.with(ctx).load(R.mipmap.icon_more).into(mHolder.iv_icon);
                mHolder.tv_title.setText("更多");
            }
        } else {
            Glide.with(ctx).load(data.cat_pic).into(mHolder.iv_icon);
            mHolder.tv_title.setText(data.cat_name);
        }


    }


    @Override
    public int getItemCount() {
        int count = list.size();
        if (count > 8 && isCanMore) {
            isHaveMore = true;
            return 8;
        }
        return list.size();
    }

    public class TeaEventTypeViewHolder extends BaseViewHolder {
        public ImageView iv_icon;
        public TextView tv_title;

        public TeaEventTypeViewHolder(View itemView) {
            super(itemView);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);

        }

        @Override
        protected void onClick(int layoutPosition) {
            if (isHaveMore && !isNowMore && layoutPosition == 7) {
                isNowMore = true;
                isHaveMore = false;
                isCanMore = false;
                notifyDataSetChanged();
            } else {
                List<TeaFilter.Cat> cats = new ArrayList<>();
                TeaFilter.Cat cat = new TeaFilter.Cat();
                cat.key = "cat_id";
                cat.name = list.get(layoutPosition).cat_name;
                cat.value = list.get(layoutPosition).cat_id;
                cats.add(cat);
                String listJson = new Gson().toJson(cats);
                Intent intent = new Intent(ctx, TeaEventFilterResultActivity.class);
                intent.putExtra("data", listJson);
                intent.putExtra("type", 0);
                ctx.startActivity(intent);
            }
        }
    }
}
