package client.ediancha.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.entity.TeaFilter;
import client.ediancha.com.fragment.DatePickerFragment;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaEventFilterContentAdapter extends SingleBaseAdapter<TeaFilter.Cat> {
    private int currentPosition = -1;
    private int fatherPosition;

    public TeaEventFilterContentAdapter(Context ctx, List<TeaFilter.Cat> list) {
        super(ctx, list);
    }

    public TeaEventFilterContentAdapter(int position, Context ctx, List<TeaFilter.Cat> list) {
        super(ctx, list);
        this.fatherPosition = position;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TeaOrderShopViewHolder(View.inflate(ctx, R.layout.item_tea_event_filter_content, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        TeaOrderShopViewHolder mHolder = (TeaOrderShopViewHolder) holder;
        TeaFilter.Cat cast = list.get(position);
        mHolder.tv_content.setText(cast.name);
        if (currentPosition == position) {
            mHolder.tv_content.setTextColor(ctx.getResources().getColor(R.color.color61c12c));
        } else {
            mHolder.tv_content.setTextColor(ctx.getResources().getColor(R.color.color333));
        }
    }

    public class TeaOrderShopViewHolder extends BaseViewHolder {
        public TextView tv_content;

        public TeaOrderShopViewHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView;
        }

        @Override
        protected void onClick(final int layoutPosition) {

            if (TextUtils.equals("timefadkjhfhdafjhd", list.get(layoutPosition).key)) {
                if (layoutPosition == currentPosition) {
                    currentPosition = -1;
                    list.get(layoutPosition).name = "设置时间";
                    list.get(layoutPosition).value = "设置时间";
                    select(fatherPosition, currentPosition, list.get(layoutPosition).key);
                    notifyDataSetChanged();
                } else {
                    DatePickerFragment pickerFragment = DatePickerFragment.getInstance("");
                    pickerFragment.setDateListener(new DatePickerFragment.DateListener() {
                        @Override
                        public void date(int year, int month, int day) {
                            list.get(layoutPosition).name = year + "-" + month + "-" + day;
                            list.get(layoutPosition).value = year + "-" + month + "-" + day;
                            currentPosition = layoutPosition;
                            select(fatherPosition, currentPosition, list.get(layoutPosition).key);
                            notifyDataSetChanged();
                        }
                    });
                    pickerFragment.show(((Activity) ctx).getFragmentManager(), "date");


                }

            } else {
                if (layoutPosition == currentPosition) {
                    currentPosition = -1;
                } else {
                    currentPosition = layoutPosition;
                }
                select(fatherPosition, currentPosition, list.get(layoutPosition).key);
                notifyDataSetChanged();
            }
        }
    }

    protected void select(int fatherPosition, int currentPosition, String key) {

    }

}
