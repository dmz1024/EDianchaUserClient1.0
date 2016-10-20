package client.ediancha.com.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.entity.TeaEventFilter;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaEventFilterContentAdapter extends SingleBaseAdapter<TeaEventFilter.Casts> {

    public TeaEventFilterContentAdapter(Context ctx, List<TeaEventFilter.Casts> list) {
        super(ctx, list);
    }

    public TeaEventFilterContentAdapter(List<TeaEventFilter.Casts> list) {
        super(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TeaOrderShopViewHolder(View.inflate(ctx, R.layout.item_tea_event_filter_content, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        TeaOrderShopViewHolder mHolder = (TeaOrderShopViewHolder) holder;
        TeaEventFilter.Casts cast = list.get(position);
        mHolder.tv_content.setText(cast.name);
    }

    public static class TeaOrderShopViewHolder extends BaseViewHolder {
        public TextView tv_content;

        public TeaOrderShopViewHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView;
        }

        @Override
        protected void onClick(int layoutPosition) {
            Log.d("点击",layoutPosition+"");
        }
    }
}
