package client.ediancha.com.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.entity.ApplyOrder;
import client.ediancha.com.interfaces.OnResultListener;
import client.ediancha.com.processor.OrderUtil;
import client.ediancha.com.util.GlideUtil;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class ApplyOrderAdapter extends SingleBaseAdapter<ApplyOrder.Data> {

    public ApplyOrderAdapter(Context ctx, List<ApplyOrder.Data> list) {
        super(ctx, list);
    }

    public ApplyOrderAdapter(List<ApplyOrder.Data> list) {
        super(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ApplyOrderViewHolder(View.inflate(ctx, R.layout.item_apply_order, null));
    }

    //3 1 2
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        ApplyOrderViewHolder mHolder = (ApplyOrderViewHolder) holder;
        ApplyOrder.Data data = list.get(position);
        GlideUtil.GlideErrAndOc(ctx, data.images, mHolder.iv_img);
        mHolder.tv_time.setText("活动时间：" + data.sttime + "--" + data.endtime);
        mHolder.bt_right.setVisibility(View.GONE);
        switch (data.status) {
            case 1:
                mHolder.bt_right.setVisibility(View.VISIBLE);
                mHolder.tv_state.setText("待审核");
                break;
            case 2:
                mHolder.tv_state.setText("未通过");
                break;
            case 3:
                mHolder.bt_right.setVisibility(View.VISIBLE);
                mHolder.tv_state.setText("已通过");
                break;
            case 4:
                mHolder.tv_state.setText("已过期");
                break;
        }
//        主办方：艺草堂 | 参加人：如果大人
        mHolder.tv_name.setText(data.ch_name);
        mHolder.tv_info.setText("主办发：" + data.store_name + " | 参加人：" + data.name);
    }

    public class ApplyOrderViewHolder extends BaseViewHolder {
        public ImageView iv_img;
        public TextView tv_name;
        public TextView tv_time;
        public TextView tv_state;
        public TextView tv_info;
        public TextView tv_price;
        public Button bt_right;


        public ApplyOrderViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_state = (TextView) itemView.findViewById(R.id.tv_state);
            tv_info = (TextView) itemView.findViewById(R.id.tv_info);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            bt_right = (Button) itemView.findViewById(R.id.bt_right);
            bt_right.setOnClickListener(this);
        }

        @Override
        protected void itemOnclick(final int layoutPosition) {

            OrderUtil.getInstance().setContext(ctx).setOnResultListener(new OnResultListener() {
                @Override
                public void resultOk() {
                    remove(layoutPosition);
                }

                @Override
                public void resultFaile() {

                }
            }).cancle(list.get(layoutPosition).id, "3");
        }
    }
}
