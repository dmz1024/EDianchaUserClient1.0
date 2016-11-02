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
import client.ediancha.com.entity.Appointment;
import client.ediancha.com.interfaces.OnResultListener;
import client.ediancha.com.processor.OrderUtil;
import client.ediancha.com.util.GlideUtil;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class AppointmentAdapter extends SingleBaseAdapter<Appointment.Data> {

    public AppointmentAdapter(Context ctx, List<Appointment.Data> list) {
        super(ctx, list);
    }

    public AppointmentAdapter(List<Appointment.Data> list) {
        super(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppointmentViewHolder(View.inflate(ctx, R.layout.item_appointment, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        AppointmentViewHolder mHolder = (AppointmentViewHolder) holder;
        Appointment.Data data = list.get(position);
        GlideUtil.GlideErrAndOc(ctx, data.images, mHolder.iv_img);
        mHolder.tv_name.setText(data.name);
        mHolder.tv_info.setText("预约信息：" + data.tablename + " | " + data.dd_time + " | " + data.sc + "小时 | " + data.num + "人");
        mHolder.tv_sn.setText("订单号：" + data.orderid);
        mHolder.tv_state.setText(getStatus(data.status, mHolder.bt_right));
        mHolder.tv_time.setText("下单时间：" + data.dateline);
    }

    private String getStatus(int status, Button bt) {
        bt.setVisibility(View.GONE);
        switch (status) {
            case 1:
                bt.setVisibility(View.VISIBLE);
                return "待审核";
            case 2:
                bt.setVisibility(View.VISIBLE);
                return "待消费";
            case 3:
                return "已完成";
            case 4:
                return "已取消";
        }
        return "";
    }

    public class AppointmentViewHolder extends BaseViewHolder {
        public ImageView iv_img;
        public TextView tv_name;
        public TextView tv_time;
        public TextView tv_state;
        public TextView tv_info;
        public TextView tv_tips;
        public TextView tv_sn;
        public Button bt_right;

        public AppointmentViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_state = (TextView) itemView.findViewById(R.id.tv_state);
            tv_info = (TextView) itemView.findViewById(R.id.tv_info);
            tv_tips = (TextView) itemView.findViewById(R.id.tv_tips);
            tv_sn = (TextView) itemView.findViewById(R.id.tv_sn);
            bt_right = (Button) itemView.findViewById(R.id.bt_right);
            bt_right.setOnClickListener(this);
        }


        @Override
        protected void itemOnclick(int id, final int layoutPosition) {
            OrderUtil.getInstance().setContext(ctx).setOnResultListener(new OnResultListener() {
                @Override
                public void resultOk() {
                    remove(layoutPosition);
                }

                @Override
                public void resultFaile() {

                }
            }).cancle(list.get(layoutPosition).order_id, "2");
        }
    }
}
