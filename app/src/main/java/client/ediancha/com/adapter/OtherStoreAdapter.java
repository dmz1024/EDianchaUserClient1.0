package client.ediancha.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.activity.TeaDescActivity;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.DescBaseActivity;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.entity.Like;
import client.ediancha.com.entity.OtherStore;
import client.ediancha.com.interfaces.OnResultListener;
import client.ediancha.com.processor.CollectionUtil;
import client.ediancha.com.util.GlideUtil;

import static client.ediancha.com.R.id.iv_like;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class OtherStoreAdapter extends SingleBaseAdapter<OtherStore> {

    public OtherStoreAdapter(Context ctx, List<OtherStore> list) {
        super(ctx, list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OtherStoreViewHolder(View.inflate(ctx, R.layout.item_other_store, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        OtherStoreViewHolder mHolder = (OtherStoreViewHolder) holder;
        OtherStore data = list.get(position);
        GlideUtil.GlideErrAndOc(ctx, data.images, mHolder.iv_img);
        mHolder.tv_address.setText("地址：" + data.address);
        mHolder.tv_name.setText(data.name);
        mHolder.tv_price.setText("￥" + data.price);
    }

    public class OtherStoreViewHolder extends BaseViewHolder {
        public ImageView iv_img;
        public TextView tv_name;
        public TextView tv_address;
        public TextView tv_price;


        public OtherStoreViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_address = (TextView) itemView.findViewById(R.id.tv_address);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
        }

        @Override
        protected void onClick(int layoutPosition) {
            Intent intent = new Intent(ctx, DescBaseActivity.class);
            intent.putExtra("title", list.get(layoutPosition).name);
            intent.putExtra("id", list.get(layoutPosition).pigcms_id);
            intent.putExtra("type", 2);
            ctx.startActivity(intent);
        }
    }
}
