package client.ediancha.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.activity.BackShopActivity;
import client.ediancha.com.activity.BackShopInfoActivity;
import client.ediancha.com.activity.BackShopLogisticsActivity;
import client.ediancha.com.api.MyRetrofitUtil;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.entity.BackShop;
import client.ediancha.com.entity.BaseEntity;
import client.ediancha.com.entity.TeaOrder;
import client.ediancha.com.util.GlideUtil;
import client.ediancha.com.util.MyToast;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class BackShopAdapter extends SingleBaseAdapter<BackShop.Data> {

    public BackShopAdapter(Context ctx, List<BackShop.Data> list) {
        super(ctx, list);
    }

    public BackShopAdapter(Context ctx, List<BackShop.Data> list, boolean isCanChoose) {
        super(ctx, list);
    }

    public BackShopAdapter(List<BackShop.Data> list) {
        super(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BackShopViewHolder(View.inflate(ctx, R.layout.item_back_shop, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        BackShopViewHolder mHolder = (BackShopViewHolder) holder;
        BackShop.Data product = list.get(position);
        GlideUtil.GlideErrAndOc(ctx, product.images, mHolder.iv_img);
        mHolder.tv_sn.setText("订单编号：" + product.order_no);
        mHolder.tv_status.setText(getStatus(product.status, mHolder.tv_right));
        mHolder.tv_name.setText(product.name);
        mHolder.tv_now_price.setText("￥" + product.pro_price);
        mHolder.tv_count.setText("数量x" + product.pro_num);
    }

    private String getStatus(int status, TextView tv_right) {
        tv_right.setVisibility(View.GONE);
        switch (status) {
            case 1:
                tv_right.setVisibility(View.VISIBLE);
                tv_right.setText("取消退货");
                return "申请中";
            //取消申请
            case 2:
                return "审核不通过";
            case 3:
                return "审核通过";
            case 4:
                tv_right.setVisibility(View.VISIBLE);
                tv_right.setText("查看物流");
                return "退货中";
            case 5:
                tv_right.setVisibility(View.VISIBLE);
                tv_right.setText("查看物流");
                return "退货完成";
            case 6:
                return "退货取消";
        }
        return "";
    }


    public class BackShopViewHolder extends BaseViewHolder {
        public ImageView iv_img;
        public TextView tv_sn;
        public TextView tv_name;
        public TextView tv_status;
        public TextView tv_now_price;
        public TextView tv_count;
        public TextView tv_right;

        public BackShopViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_now_price = (TextView) itemView.findViewById(R.id.tv_now_price);
            tv_count = (TextView) itemView.findViewById(R.id.tv_count);
            tv_sn = (TextView) itemView.findViewById(R.id.tv_sn);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);
            tv_right = (TextView) itemView.findViewById(R.id.tv_right);
            tv_right.setOnClickListener(this);
        }

        @Override
        protected void onClick(int layoutPosition) {
            desc(layoutPosition);

        }

        @Override
        protected void itemOnclick(int id, int layoutPosition) {
            BackShop.Data product = list.get(layoutPosition);
            switch (product.status) {
                case 1:
                    cancle(product.id, layoutPosition);
                    break;
                case 4:
                case 5:
                    wuliu(layoutPosition);
                    break;
            }
        }
    }


    private void wuliu(int layoutPosition) {
        BackShop.Data product = list.get(layoutPosition);
        Intent intent = new Intent(ctx, BackShopLogisticsActivity.class);
        intent.putExtra("id", product.id);
        ctx.startActivity(intent);
    }

    protected void desc(int layoutPosition) {

    }

    /**
     * 取消退货
     *
     * @param return_no
     */
    private void cancle(String return_no, final int position) {
        Map<String, String> map = new HashMap<>();
        map.put("c", "myreturn");
        map.put("a", "cancel");
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);
        map.put("return_id", return_no);
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
                    MyToast.showToast("退货取消成功");
                    list.get(position).status = 6;
                    notifyDataSetChanged();
                } else {
                    MyToast.showToast(baseEntity.msg);
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
        }, "正在取消退货", ctx);
    }

}
