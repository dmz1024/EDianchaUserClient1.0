package client.ediancha.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.entity.TeaDesc;
import client.ediancha.com.entity.TeaFilter;
import client.ediancha.com.fragment.DatePickerFragment;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class ChooseBuyCarContentAdapter extends SingleBaseAdapter<TeaDesc.Value> {
    private int currentPosition = 0;
    private int fatherPosition;

    public ChooseBuyCarContentAdapter(int position, Context ctx, List<TeaDesc.Value> list) {
        super(ctx, list);
        this.fatherPosition = position;

    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TeaOrderShopViewHolder(View.inflate(ctx, R.layout.item_framelayout_text, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        TeaOrderShopViewHolder mHolder = (TeaOrderShopViewHolder) holder;
        TeaDesc.Value value = list.get(position);
        mHolder.tv_content.setText(value.value);
        if (currentPosition == position) {
            mHolder.tv_content.setTextColor(ctx.getResources().getColor(R.color.colorfff));
            mHolder.tv_content.setBackgroundResource(R.drawable.rectangle_51b338_radius_5);
        } else {
            mHolder.tv_content.setTextColor(ctx.getResources().getColor(R.color.color333));
            mHolder.tv_content.setBackgroundResource(R.drawable.rectangle_999_radius_5);
        }

    }

    public class TeaOrderShopViewHolder extends BaseViewHolder {
        public TextView tv_content;

        public TeaOrderShopViewHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
        }

        @Override
        protected void onClick(final int layoutPosition) {
            if (layoutPosition == currentPosition) {
                return;
            } else {
                currentPosition = layoutPosition;
            }
            select(fatherPosition, currentPosition);
            notifyDataSetChanged();
        }
    }

    protected void select(int fatherPosition, int currentPosition) {

    }

}
