package client.ediancha.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;
import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.activity.TeaDescActivity;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.TeaProduct;
import client.ediancha.com.entity.TeaSpace;
import client.ediancha.com.myview.GlideCircleTransform;
import client.ediancha.com.myview.TextImage;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaProductAdapter extends SingleBaseAdapter<TeaProduct.Data> {
    private static final int VIEW_SHOW_AD = 0;
    private static final int VIEW_SHOW_CONTENT = 1;
    private static final int VIEW_SHOW_TYPE = 2;

    public TeaProductAdapter(Context ctx, List<TeaProduct.Data> list) {
        super(ctx, list);
    }

    public TeaProductAdapter(List<TeaProduct.Data> list) {
        super(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_SHOW_CONTENT) {
            return new TeaProductViewHolder(View.inflate(ctx, R.layout.item_tea_product_content, null));
        } else if (viewType == VIEW_SHOW_AD) {
            return new TeaProductAdViewHolder(View.inflate(ctx, R.layout.item_tea_product_ad, null));
        }
        return new TeaProductTypeViewHolder(View.inflate(ctx, R.layout.item_tea_space_type, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        if (holder instanceof TeaProductViewHolder) {
            TeaProductViewHolder mHolder = (TeaProductViewHolder) holder;
            TeaProduct.Data data = list.get(position);
            Glide.with(ctx).load(data.images.large).into(mHolder.iv_img);
            mHolder.tv_title.setText(data.title);
        } else if (holder instanceof TeaProductAdViewHolder) {
             TeaProductAdViewHolder mHolder = (TeaProductAdViewHolder) holder;
            Glide.with(ctx).load(Constant.IMAGE).transform(new GlideCircleTransform(ctx)).into(mHolder.iv_img);

        } else {

            TeaProductTypeViewHolder mHolder = (TeaProductTypeViewHolder) holder;
            GridLayoutManager manager = new GridLayoutManager(ctx, 4);
            List<String> types = new ArrayList<>();
            types.add("铁观音");
            types.add("普洱茶");
            types.add("铁观音");
            types.add("铁观音");
            types.add("普洱茶");
            types.add("普洱茶");
            types.add("铁观音");
            types.add("普洱茶");

            TeaSpaceTypeContentAdapter mAdapter = new TeaSpaceTypeContentAdapter(ctx, types);
            mHolder.rv_type.setLayoutManager(manager);
            mHolder.rv_type.setAdapter(mAdapter);
            mHolder.rv_type.addItemDecoration(new ItemDecoration(ctx, LinearLayout.HORIZONTAL, 15, "#fbfbfb"));
        }


    }


    @Override
    public int getItemViewType(int position) {
        if (position == 6) {
            return VIEW_SHOW_AD;
        } else if (position == 3) {
            return VIEW_SHOW_TYPE;
        }
        return VIEW_SHOW_CONTENT;
    }

    public  class TeaProductViewHolder extends BaseViewHolder {
        public ImageView iv_img;
        public TextView tv_state;
        public TextView tv_title;
        public TextView tv_price;
        public RecyclerView rv_type;


        public TeaProductViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_state = (TextView) itemView.findViewById(R.id.tv_state);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            rv_type = (RecyclerView) itemView.findViewById(R.id.rv_type);
        }

        @Override
        protected void onClick(int layoutPosition) {
            Util.skip(((Activity) ctx), TeaDescActivity.class);
        }
    }


    public static class TeaProductTypeViewHolder extends BaseViewHolder {
        public RecyclerView rv_type;


        public TeaProductTypeViewHolder(View itemView) {
            super(itemView);
            rv_type = (RecyclerView) itemView.findViewById(R.id.rv_content);
        }

        @Override
        protected boolean isCanOnclick() {
            return false;
        }
    }

    public static class TeaProductAdViewHolder extends BaseViewHolder {
//        public RollPagerView rv_ad;
        public ImageView iv_img;
        public TeaProductAdViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
        }

    }


}
