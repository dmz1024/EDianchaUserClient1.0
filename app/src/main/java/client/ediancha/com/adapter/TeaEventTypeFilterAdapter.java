package client.ediancha.com.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.util.GlideUtil;

/**
 * Created by dengmingzhi on 16/10/11.
 */

public class TeaEventTypeFilterAdapter extends SingleBaseAdapter<String> {
    private boolean isHaveMore;
    private boolean isNowMore;
    private boolean isCanMore = true;

    public TeaEventTypeFilterAdapter(Context ctx, List<String> list) {
        super(ctx, list);
    }

    public TeaEventTypeFilterAdapter(List<String> list) {
        super(list);
    }

    @Override
    public TeaEventTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TeaEventTypeViewHolder(View.inflate(ctx, R.layout.item_tea_event_type_filter, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        TeaEventTypeViewHolder mHolder = ((TeaEventTypeViewHolder) holder);
        String type = list.get(position);
//        R.mipmap.center_tea_order
        if (isHaveMore && !isNowMore) {
            if (position < 7) {
                GlideUtil.GlideErrAndOc(ctx, R.mipmap.center_address, mHolder.iv_icon);
                mHolder.tv_title.setText(type);
            } else {

                Glide.with(ctx).load(R.mipmap.center_address).into(mHolder.iv_icon);
                mHolder.tv_title.setText("更多");
            }
        } else {
            Glide.with(ctx).load(R.mipmap.center_tea_order).into(mHolder.iv_icon);
            mHolder.tv_title.setText(type);
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
            if (isHaveMore && !isNowMore && layoutPosition==7) {
                isNowMore = true;
                isHaveMore = false;
                isCanMore = false;
                notifyDataSetChanged();
            } else {
                Log.d("点击了", list.get(layoutPosition));
            }
        }
    }
}
