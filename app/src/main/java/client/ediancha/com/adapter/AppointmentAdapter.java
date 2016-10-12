package client.ediancha.com.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.entity.ApplyOrder;
import client.ediancha.com.entity.Appointment;
import client.ediancha.com.myview.TextImage;

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
        Glide.with(ctx).load(data.images.large).into(mHolder.iv_img);
        mHolder.tv_name.setText(data.title);
    }

    public static class AppointmentViewHolder extends BaseViewHolder {
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
        }

    }
}
