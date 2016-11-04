package client.ediancha.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
import client.ediancha.com.entity.BuyCar;
import client.ediancha.com.interfaces.OnResultListener;
import client.ediancha.com.myview.AddAndSub;
import client.ediancha.com.myview.PopMessageTips;
import client.ediancha.com.myview.TextImage;
import client.ediancha.com.processor.BuyCarUtil;
import client.ediancha.com.util.GlideUtil;
import client.ediancha.com.util.MyToast;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class BuyCarShopAdapter extends SingleBaseAdapter<BuyCar.Shop> {
    private boolean isDelete;

    public BuyCarShopAdapter(Context ctx, List<BuyCar.Shop> list, boolean isDelete) {
        super(ctx, list);
        this.isDelete = isDelete;

    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ApplyOrderViewHolder(View.inflate(ctx, R.layout.item_buy_car_item, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        final ApplyOrderViewHolder mHolder = (ApplyOrderViewHolder) holder;
        final BuyCar.Shop data = list.get(position);
        GlideUtil.GlideErrAndOc(ctx, data.image, mHolder.iv_img);
        mHolder.tv_name.setText(data.name);
        mHolder.add_sub.setVisibility(isDelete ? View.GONE : View.VISIBLE);
        mHolder.tv_delete.setVisibility(isDelete ? View.VISIBLE : View.GONE);
        mHolder.tv_count.setText("数量：" + data.pro_num);
        mHolder.tv_price.setText("￥" + data.price);
        mHolder.view_choose.setBackgroundResource(data.isChoose ? R.mipmap.icon_shopcart_checked : R.mipmap.icon_shopcart_check);
        mHolder.add_sub.setCount(1, data.quantity).setCount(data.pro_num).setOnAddAndSubListener(new AddAndSub.OnAddAndSubListener() {
            @Override
            public void add() {
                BuyCarUtil.getInstance().setContext(ctx).setOnResultListener(new OnResultListener() {
                    @Override
                    public void resultOk() {
                        list.get(position).pro_num = mHolder.add_sub.getCurrent();
                        change();
                    }

                    @Override
                    public void resultFaile() {
                        mHolder.add_sub.setCount(mHolder.add_sub.getCurrent() - 1);
                        mHolder.tv_count.setText("数量：" + list.get(position).pro_num);
                    }
                }).updateCount(data.pigcms_id, data.sku_id, data.product_id, mHolder.add_sub.getCurrent() + "");
            }

            @Override
            public void sub() {
                BuyCarUtil.getInstance().setContext(ctx).setOnResultListener(new OnResultListener() {
                    @Override
                    public void resultOk() {
                        list.get(position).pro_num = mHolder.add_sub.getCurrent();
                        change();
                    }

                    @Override
                    public void resultFaile() {
                        mHolder.add_sub.setCount(mHolder.add_sub.getCurrent() + 1);
                        mHolder.tv_count.setText("数量：" + list.get(position).pro_num);
                    }
                }).updateCount(data.pigcms_id, data.sku_id, data.product_id, mHolder.add_sub.getCurrent() + "");
            }

            @Override
            public void content(int count) {
                mHolder.tv_count.setText("数量：" + count);
            }
        });
    }

    public class ApplyOrderViewHolder extends BaseViewHolder {
        public ImageView iv_img;
        public TextView tv_name;
        public TextView tv_count;
        public TextView tv_price;
        public TextView tv_old_price;
        public TextView tv_delete;
        public AddAndSub add_sub;
        public View view_choose;

        public ApplyOrderViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_count = (TextView) itemView.findViewById(R.id.tv_count);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_old_price = (TextView) itemView.findViewById(R.id.tv_old_price);
            tv_delete = (TextView) itemView.findViewById(R.id.tv_delete);
            add_sub = (AddAndSub) itemView.findViewById(R.id.add_sub);
            view_choose = itemView.findViewById(R.id.view_choose);
            tv_delete.setOnClickListener(this);
            view_choose.setOnClickListener(this);
        }

        @Override
        protected void itemOnclick(int id, int layoutPosition) {
            switch (id) {
                case R.id.tv_delete:
                    delete(layoutPosition);
                    break;
                case R.id.view_choose:
                    choose(layoutPosition);
                    break;
            }
        }
    }

    private void choose(int layoutPosition) {
        list.get(layoutPosition).isChoose = !list.get(layoutPosition).isChoose;
        select();
    }


    protected void select() {

    }


    private void delete(final int layoutPosition) {
        BuyCarUtil.getInstance().setContext(ctx).setOnResultListener(new OnResultListener() {
            @Override
            public void resultOk() {
                remove(layoutPosition);
                delete();
            }

            @Override
            public void resultFaile() {

            }
        }).deleteBuyCar(list.get(layoutPosition).pigcms_id);
    }


    protected void delete() {

    }

    protected void change(){}

}
