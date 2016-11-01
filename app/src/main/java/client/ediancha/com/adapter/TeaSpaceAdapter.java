package client.ediancha.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.DescBaseActivity;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.TeaSpace;
import client.ediancha.com.myview.TextImage;
import client.ediancha.com.util.GlideUtil;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaSpaceAdapter extends SingleBaseAdapter<TeaSpace.Data> {
    private static final int VIEW_SHOW_AD = 2;
    private static final int VIEW_SHOW_CONTENT = 1;
    private static final int VIEW_SHOW_TYPE = 3;

    public TeaSpaceAdapter(Context ctx, List<TeaSpace.Data> list) {
        super(ctx, list);
    }

    public TeaSpaceAdapter(List<TeaSpace.Data> list) {
        super(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_SHOW_CONTENT) {
            return new TeaSpaceViewHolder(View.inflate(ctx, R.layout.item_tea_space_content, null));
        } else if (viewType == VIEW_SHOW_AD) {
            return new TeaSpaceAdViewHolder(View.inflate(ctx, R.layout.item_ad, null));
        }
        return new TeaSpaceTypeViewHolder(View.inflate(ctx, R.layout.item_tea_space_type, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        if (holder instanceof TeaSpaceViewHolder) {
            TeaSpaceViewHolder mHolder = (TeaSpaceViewHolder) holder;
            TeaSpace.Data2 data = list.get(position).data2;
            GlideUtil.GlideErrAndOc(ctx, data.images, mHolder.iv_img);
            mHolder.tv_title.setText(data.name);
            if (TextUtils.isEmpty(data.juli)) {
                mHolder.tv_pos.setText("地址：" + data.address);
            } else {
                mHolder.tv_pos.setText("距离：" + data.juli + "km");
            }

            mHolder.tv_price.setText("人均￥" + data.price + "/人");

        } else if (holder instanceof TeaSpaceAdViewHolder) {
            TeaSpaceAdViewHolder mHolder = (TeaSpaceAdViewHolder) holder;
            List<TeaSpace.Data1> datas = list.get(position).data1;
            mHolder.rv_ad.setPlayDelay(2500);
            mHolder.rv_ad.setAnimationDurtion(500);
            List<String> urls = new ArrayList<>();
            for (int i = 0; i < datas.size(); i++) {
                urls.add(datas.get(i).pic);
            }
            mHolder.rv_ad.setAdapter(new AdNormalAdapter(ctx, mHolder.rv_ad, urls));
            mHolder.rv_ad.setHintView(new ColorPointHintView(ctx, ctx.getResources().getColor(R.color.color51b338), Color.WHITE));
        } else {
//            if (!map.containsKey(position)) {
//                map.put(position, false);
//            }
//
//
//            TeaSpaceTypeViewHolder mHolder = (TeaSpaceTypeViewHolder) holder;
//            GridLayoutManager manager = new GridLayoutManager(ctx, 4);
//            List<String> types = new ArrayList<>();
//            types.add("棋牌室");
//            types.add("WIFI");
//            types.add("停车位");
//            types.add("投影");
//            types.add("棋牌室");
//            types.add("WIFI");
//            types.add("停车位");
//            types.add("投影");
//
//            TeaSpaceTypeContentAdapter mAdapter = new TeaSpaceTypeContentAdapter(ctx, types);
//            mHolder.rv_type.setLayoutManager(manager);
//            mHolder.rv_type.setAdapter(mAdapter);
//            if (!map.get(position)) {
//                map.put(position, true);
//                mHolder.rv_type.addItemDecoration(new ItemDecoration(ctx, LinearLayout.HORIZONTAL, 15, "#fbfbfb"));
//            }
        }


    }


    @Override
    public int getItemViewType(int position) {
        int r = list.get(position).r;
        if (r == 2) {
            return VIEW_SHOW_CONTENT;
        } else if (r == 1) {
            return VIEW_SHOW_AD;
        } else if (r == 3) {
            return VIEW_SHOW_TYPE;
        }

        return VIEW_SHOW_CONTENT;
    }

    public class TeaSpaceViewHolder extends BaseViewHolder {
        public ImageView iv_img;
        public TextView tv_title;
        public TextView tv_pos;
        public TextView tv_price;

        public TeaSpaceViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_pos = (TextView) itemView.findViewById(R.id.tv_pos);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_img.getLayoutParams();
            layoutParams.height = (int) (Util.getWidth() / 1.75);
            iv_img.setLayoutParams(layoutParams);
        }

        @Override
        protected void onClick(int layoutPosition) {
            Intent intent = new Intent(ctx, DescBaseActivity.class);
            intent.putExtra("title", list.get(layoutPosition).data2.name);
            intent.putExtra("id", list.get(layoutPosition).data2.pigcms_id);
            intent.putExtra("type", 2);
            ctx.startActivity(intent);
        }
    }


    public static class TeaSpaceTypeViewHolder extends BaseViewHolder {
        public RecyclerView rv_type;


        public TeaSpaceTypeViewHolder(View itemView) {
            super(itemView);
            rv_type = (RecyclerView) itemView.findViewById(R.id.rv_content);
        }

        @Override
        protected boolean isCanOnclick() {
            return false;
        }
    }

    public static class TeaSpaceAdViewHolder extends BaseViewHolder {
        public RollPagerView rv_ad;

        public TeaSpaceAdViewHolder(View itemView) {
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
