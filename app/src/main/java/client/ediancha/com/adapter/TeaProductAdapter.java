package client.ediancha.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.activity.AllListActivity;
import client.ediancha.com.activity.OfficialStoreInfoActivity;
import client.ediancha.com.activity.TeaDescActivity;
import client.ediancha.com.activity.WebViewActivity;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.DescBaseActivity;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.TeaProduct;
import client.ediancha.com.entity.TeaSpace;
import client.ediancha.com.myview.GlideCircleTransform;
import client.ediancha.com.util.GlideUtil;
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
            GlideUtil.GlideErrAndOc(ctx, data.image, mHolder.iv_img);
            mHolder.tv_title.setText(data.name);
            mHolder.tv_old_price.setText("￥" + data.original_price);
            mHolder.tv_price.setText("￥" + data.price);
            mHolder.tv_state.setVisibility(data.is_recommend == 1 ? View.VISIBLE : View.GONE);
            mHolder.tv_recommend_title.setText(data.recommend_title);
        } else if (holder instanceof TeaProduct_4ViewHolder) {
            TeaProduct_4ViewHolder mHolder = (TeaProduct_4ViewHolder) holder;
            TeaProduct.Content data = list.get(position).data4;
            GlideUtil.GlideErrAndOc(ctx, data.image, mHolder.iv_img);
            mHolder.tv_title.setText(data.name);
            mHolder.tv_old_price.setText("￥" + data.original_price);
            mHolder.tv_price.setText("￥" + data.price);
            mHolder.tv_state.setVisibility(data.is_recommend == 1 ? View.VISIBLE : View.GONE);
            mHolder.tv_recommend_title.setText(data.recommend_title);
        } else if (holder instanceof TeaProductAdViewHolder) {
            TeaProductAdViewHolder mHolder = (TeaProductAdViewHolder) holder;
            final List<TeaProduct.Data3> data3 = list.get(position).data3;
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
            mHolder.rv_ad.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(ctx, WebViewActivity.class);
                    intent.putExtra("url", data3.get(position).url);
                    ctx.startActivity(intent);

                }
            });
            mHolder.rv_ad.setAdapter(new AdNormalAdapter(ctx, mHolder.rv_ad, urls));
            mHolder.rv_ad.setHintView(new ColorPointHintView(ctx, ctx.getResources().getColor(R.color.color51b338), Color.WHITE));
        } else {
            if (!map.containsKey(position)) {
                map.put(position, false);
            }
            TeaProductTypeViewHolder mHolder = (TeaProductTypeViewHolder) holder;
            TeaProduct.Data2 data2 = list.get(position).data2;


            LinearLayoutManager manager = new LinearLayoutManager(ctx);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            TeaSpaceTypeContentAdapter mAdapter = new TeaSpaceTypeContentAdapter(ctx, data2.data, data2.name, data2.key);
            mHolder.rv_type.setLayoutManager(manager);
            mHolder.rv_type.setAdapter(mAdapter);
            if (!map.get(position)) {
                map.put(position, true);
                mHolder.rv_type.addItemDecoration(new ItemDecoration(ctx, LinearLayout.HORIZONTAL, 10, "#fbfbfb"));
            }
        }


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
        public TextView tv_recommend_title;

        public TeaProduct_1ViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_state = (TextView) itemView.findViewById(R.id.tv_state);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_old_price = (TextView) itemView.findViewById(R.id.tv_old_price);
            tv_recommend_title = (TextView) itemView.findViewById(R.id.tv_recommend_title);
            tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_img.getLayoutParams();
            layoutParams.height = (int) (Util.getWidth() / 1.75);
            iv_img.setLayoutParams(layoutParams);
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
        public TextView tv_recommend_title;


        public TeaProduct_4ViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_state = (TextView) itemView.findViewById(R.id.tv_state);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_old_price = (TextView) itemView.findViewById(R.id.tv_old_price);
            tv_recommend_title = (TextView) itemView.findViewById(R.id.tv_recommend_title);
            tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_img.getLayoutParams();
            layoutParams.height = (int) (Util.getWidth() / 1.75);
            iv_img.setLayoutParams(layoutParams);
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
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) rv_ad.getLayoutParams();
            layoutParams.height = (int) (Util.getWidth() / 1.75);
            rv_ad.setLayoutParams(layoutParams);
        }

        @Override
        protected boolean isCanOnclick() {
            return false;
        }
    }

}
