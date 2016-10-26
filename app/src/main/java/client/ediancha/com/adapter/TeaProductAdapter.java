package client.ediancha.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import client.ediancha.com.base.DescBaseActivity;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.TeaProduct;
import client.ediancha.com.myview.GlideCircleTransform;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaProductAdapter extends SingleBaseAdapter<TeaProduct.Data> {
    private static final int VIEW_SHOW_AD = 3;
    private static final int VIEW_SHOW_CONTENT_1 = 1;
    private static final int VIEW_SHOW_CONTENT_4 = 4;
    private static final int VIEW_SHOW_TYPE = 2;
    private Map<Integer, Boolean> map = new HashMap<>();

    public TeaProductAdapter(Context ctx, List<TeaProduct.Data> list) {
        super(ctx, list);
    }

    public TeaProductAdapter(List<TeaProduct.Data> list) {
        super(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_SHOW_CONTENT_4) {
            return new TeaProduct_4ViewHolder(View.inflate(ctx, R.layout.item_tea_product_content, null));
        } else if (viewType == VIEW_SHOW_CONTENT_1) {
            return new TeaProduct_1ViewHolder(View.inflate(ctx, R.layout.item_tea_product_content, null));
        } else if (viewType == VIEW_SHOW_AD) {
            return new TeaProductAdViewHolder(View.inflate(ctx, R.layout.item_ad, null));
        }
        return new TeaProductTypeViewHolder(View.inflate(ctx, R.layout.item_tea_space_type, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {


        if (holder instanceof TeaProduct_1ViewHolder) {
            TeaProduct_1ViewHolder mHolder = (TeaProduct_1ViewHolder) holder;
            TeaProduct.Content data = list.get(position).data1;
            Glide.with(ctx).load(data.image).into(mHolder.iv_img);
            mHolder.tv_title.setText(data.name);
            mHolder.tv_old_price.setText(data.original_price);
            mHolder.tv_price.setText("￥" + data.price);

        } else if (holder instanceof TeaProduct_4ViewHolder) {
            TeaProduct_4ViewHolder mHolder = (TeaProduct_4ViewHolder) holder;
            TeaProduct.Content data = list.get(position).data4;
            Glide.with(ctx).load(data.image).into(mHolder.iv_img);
            mHolder.tv_title.setText(data.name);
            mHolder.tv_old_price.setText(data.original_price);
            mHolder.tv_price.setText("￥" + data.price);
            mHolder.tv_state.setVisibility(View.GONE);
        } else if (holder instanceof TeaProductAdViewHolder) {
            TeaProductAdViewHolder mHolder = (TeaProductAdViewHolder) holder;
            List<TeaProduct.Data3> data3 = list.get(position).data3;
            if (data3 != null && data3.size() > 1) {
                mHolder.rv_ad.setPlayDelay(2500);
            } else {
                mHolder.rv_ad.setPlayDelay(0);
            }
            mHolder.rv_ad.setAnimationDurtion(500);
            List<String> urls = new ArrayList<>();
            for (int i = 0; i < data3.size(); i++) {
                urls.add(data3.get(i).pic);
            }
            mHolder.rv_ad.setAdapter(new AdNormalAdapter(ctx, mHolder.rv_ad, urls));
            mHolder.rv_ad.setHintView(new ColorPointHintView(ctx, ctx.getResources().getColor(R.color.color51b338), Color.WHITE));
        } else {
            if (!map.containsKey(position)) {
                map.put(position, false);
            }

            TeaProductTypeViewHolder mHolder = (TeaProductTypeViewHolder) holder;
            List<TeaProduct.Data2> data2 = list.get(position).data2;
            List<String> types = new ArrayList<>();
            for (int i = 0; i < data2.size(); i++) {
                types.add(data2.get(i).name);
            }
            GridLayoutManager manager = new GridLayoutManager(ctx, 4);
            TeaSpaceTypeContentAdapter mAdapter = new TeaSpaceTypeContentAdapter(ctx, types);
            mHolder.rv_type.setLayoutManager(manager);
            mHolder.rv_type.setAdapter(mAdapter);
            if (!map.get(position)) {
                map.put(position, true);
                mHolder.rv_type.addItemDecoration(new ItemDecoration(ctx, LinearLayout.HORIZONTAL, 15, "#fbfbfb"));
            }
        }


    }

    @Override
    public int getItemCount() {
        Log.d("size", list.size() + "");
        return super.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        int r = list.get(position).r;
        if (r == 4) {
            return VIEW_SHOW_CONTENT_4;
        } else if (r == 1) {
            return VIEW_SHOW_CONTENT_1;
        } else if (r == 2) {
            return VIEW_SHOW_TYPE;
        }
        return VIEW_SHOW_AD;
    }

    public class TeaProduct_1ViewHolder extends BaseViewHolder {
        public ImageView iv_img;
        public TextView tv_state;
        public TextView tv_title;
        public TextView tv_price;
        public TextView tv_old_price;
        public RecyclerView rv_type;


        public TeaProduct_1ViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_state = (TextView) itemView.findViewById(R.id.tv_state);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_old_price = (TextView) itemView.findViewById(R.id.tv_old_price);
            rv_type = (RecyclerView) itemView.findViewById(R.id.rv_type);
        }

        @Override
        protected void onClick(int layoutPosition) {
            Intent intent = new Intent(ctx, TeaDescActivity.class);
            intent.putExtra("title", list.get(layoutPosition).data1.name);
            intent.putExtra("id", list.get(layoutPosition).data1.product_id);
            ctx.startActivity(intent);
        }
    }

    public class TeaProduct_4ViewHolder extends BaseViewHolder {
        public ImageView iv_img;
        public TextView tv_state;
        public TextView tv_title;
        public TextView tv_price;
        public TextView tv_old_price;
        public RecyclerView rv_type;

        public TeaProduct_4ViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_state = (TextView) itemView.findViewById(R.id.tv_state);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_old_price = (TextView) itemView.findViewById(R.id.tv_old_price);
            rv_type = (RecyclerView) itemView.findViewById(R.id.rv_type);
        }

        @Override
        protected void onClick(int layoutPosition) {
            Intent intent = new Intent(ctx, TeaDescActivity.class);
            intent.putExtra("title", list.get(layoutPosition).data4.name);
            intent.putExtra("id", list.get(layoutPosition).data4.product_id);
            ctx.startActivity(intent);
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
        public RollPagerView rv_ad;

        public TeaProductAdViewHolder(View itemView) {
            super(itemView);
            rv_ad = (RollPagerView) itemView.findViewById(R.id.roll_view_pager);
        }

        @Override
        protected boolean isCanOnclick() {
            return false;
        }
    }

}
