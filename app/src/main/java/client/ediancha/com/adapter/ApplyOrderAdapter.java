package client.ediancha.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.activity.LoginActivity;
import client.ediancha.com.api.MyRetrofitUtil;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.entity.ApplyOrder;
import client.ediancha.com.entity.BaseEntity;
import client.ediancha.com.myview.PopMessageTips;
import client.ediancha.com.myview.TextImage;
import client.ediancha.com.util.GlideUtil;
import client.ediancha.com.util.MyToast;
import client.ediancha.com.util.Util;

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

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        ApplyOrderViewHolder mHolder = (ApplyOrderViewHolder) holder;
        ApplyOrder.Data data = list.get(position);
        GlideUtil.GlideErrAndOc(ctx, data.images, mHolder.iv_img);
        mHolder.tv_time.setText("活动时间：" + data.sttime + "--" + data.endtime);
        switch (data.status) {
            case 1:
                mHolder.tv_state.setText("待审核");
                break;
            case 2:
                mHolder.tv_state.setText("未通过");
                break;
            case 3:
                mHolder.tv_state.setText("已通过");
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
            Map<String, String> map = new HashMap<>();
            map.put("c", "myorder");
            map.put("a", "cancel");
            map.put("uid", UserInfo.uid);
            map.put("token", UserInfo.token);
            map.put("dataid", list.get(layoutPosition).id);
            map.put("type", "3");
            MyRetrofitUtil.getInstance().post("app.php", map, BaseEntity.class, new MyRetrofitUtil.OnRequestListener<BaseEntity>() {
                @Override
                public void noNetwork() {

                }

                @Override
                public void serverErr() {

                }

                @Override
                public void haveData(BaseEntity baseEntity) {

                    if (baseEntity.result == 0) {
                        MyToast.showToast("取消成功");
                        remove(layoutPosition);
                    } else {
                        if (baseEntity.msg.contains("token")) {
                            new PopMessageTips("账号信息", "账号信息已过期,请重新登录!", "去登录", "再看看") {
                                @Override
                                protected void right() {
                                    super.right();
                                    Util.skip(((Activity) ctx), LoginActivity.class);
                                }
                            }.showView(ctx);
                        } else {
                            MyToast.showToast(baseEntity.msg);
                        }

                    }

                }

                @Override
                public void onCompleted() {

                }

                @Override
                public void resultNo0(String s) {

                }

                @Override
                public void start() {

                }
            }, "正在取消订单...", ctx);
        }
    }
}
