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

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaSpaceTypeContentAdapter extends SingleBaseAdapter<String> {

    public TeaSpaceTypeContentAdapter(Context ctx, List<String> list) {
        super(ctx, list);
    }

    public TeaSpaceTypeContentAdapter(List<String> list) {
        super(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TeaOrderShopViewHolder(View.inflate(ctx, R.layout.item_tea_space_type_content, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        TeaOrderShopViewHolder mHolder = (TeaOrderShopViewHolder) holder;
        String title = list.get(position);
        mHolder.tv_content.setText(title);
    }

    public static class TeaOrderShopViewHolder extends BaseViewHolder {
        public TextView tv_content;

        public TeaOrderShopViewHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
        }

        @Override
        protected void onClick(int layoutPosition) {
            Log.d("茶", layoutPosition + "");
        }
    }
}
