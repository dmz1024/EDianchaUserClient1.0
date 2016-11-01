package client.ediancha.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.activity.TeaDescActivity;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.StoreInfo;
import client.ediancha.com.entity.TeaProduct;
import client.ediancha.com.myview.GlideCircleTransform;
import client.ediancha.com.util.GlideUtil;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class StoreInfoAdapter extends SingleBaseAdapter<StoreInfo.Data> {
    private static final int VIEW_SHOW_TYPE_1 = 1;
    private static final int VIEW_SHOW_TYPE_2 = 2;

    public StoreInfoAdapter(Context ctx, List<StoreInfo.Data> list) {
        super(ctx, list);
    }

    public StoreInfoAdapter(List<StoreInfo.Data> list) {
        super(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_SHOW_TYPE_1) {
            return new StoreInfoHeadViewHolder(View.inflate(ctx, R.layout.item_store_info_head, null));
        }
        return new StoreInfoContentViewHolder(View.inflate(ctx, R.layout.item_store_info_content, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        if (holder instanceof StoreInfoContentViewHolder) {
            StoreInfoContentViewHolder mHolder = (StoreInfoContentViewHolder) holder;
            StoreInfo.Data3 data = list.get(position + 1).data3;
            GlideUtil.GlideErrAndOc(ctx, data.image, mHolder.iv_img);
            mHolder.tv_name.setText(data.name);
            mHolder.tv_price.setText("￥" + data.price);
            mHolder.tv_recommend.setVisibility(data.is_recommend == 0 ? View.GONE : View.VISIBLE);
        } else {
            StoreInfoHeadViewHolder mHolder = (StoreInfoHeadViewHolder) holder;
            StoreInfo.Data1 data = list.get(position).data1;
            mHolder.tv_name.setText(data.name);
            if (!TextUtils.isEmpty(data.intro)) {
                mHolder.tv_content.setText(data.intro);
            }
            Glide.with(ctx).load(data.logo).bitmapTransform(new GlideCircleTransform(ctx)).error(R.mipmap.icon_err).placeholder(R.mipmap.icon_err).into(mHolder.iv_img);
        }


    }


    @Override
    public int getItemCount() {
        return list.size() - 1;
    }

    @Override
    public int getItemViewType(int position) {
        int r = list.get(position).r;
        if (r == 1) {
            return VIEW_SHOW_TYPE_1;
        }
        return VIEW_SHOW_TYPE_2;
    }

    public class StoreInfoHeadViewHolder extends BaseViewHolder {
        public ImageView iv_img;
        public TextView tv_name;
        public TextView tv_content;


        public StoreInfoHeadViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);

//            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_img.getLayoutParams();
//            layoutParams.height = (int) (Util.getWidth() / 1.75);
//            iv_img.setLayoutParams(layoutParams);
        }

    }

    public class StoreInfoContentViewHolder extends BaseViewHolder {
        public ImageView iv_img;
        public TextView tv_name;
        public TextView tv_price;
        public TextView tv_recommend;

        public StoreInfoContentViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_recommend = (TextView) itemView.findViewById(R.id.tv_recommend);
//            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_img.getLayoutParams();
//            layoutParams.height = layoutParams.width + 10;
//            iv_img.setLayoutParams(layoutParams);
        }

        @Override
        protected void onClick(int layoutPosition) {
            Intent intent = new Intent(ctx, TeaDescActivity.class);
            intent.putExtra("title", list.get(layoutPosition + 1).data3.name);
            intent.putExtra("id", list.get(layoutPosition + 1).data3.product_id);
            ctx.startActivity(intent);
        }
    }


}
