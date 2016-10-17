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
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;
import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.activity.TeaSpaceDescActivity;
import client.ediancha.com.activity.TestActivity;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.TeaEvent;
import client.ediancha.com.entity.TeaSpace;
import client.ediancha.com.myview.GlideCircleTransform;
import client.ediancha.com.myview.TextImage;
import client.ediancha.com.util.Util;
import retrofit2.http.Url;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaSpaceAdapter extends SingleBaseAdapter<TeaSpace.Data> {
    private static final int VIEW_SHOW_AD = 0;
    private static final int VIEW_SHOW_CONTENT = 1;
    private static final int VIEW_SHOW_TYPE = 2;

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
            TeaSpace.Data data = list.get(position);
            Glide.with(ctx).load(data.images.large).into(mHolder.iv_img);
            mHolder.tv_title.setText(data.title);
        } else if (holder instanceof TeaSpaceAdViewHolder) {
            TeaSpaceAdViewHolder mHolder = (TeaSpaceAdViewHolder) holder;
            //设置播放时间间隔
            mHolder.rv_ad.setPlayDelay(2500);
            //设置透明度
            mHolder.rv_ad.setAnimationDurtion(500);
            //设置适配器
            List<String> urls = new ArrayList<>();
            urls.add("https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p480747492.jpg");
            urls.add("https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p511118051.jpg");
            urls.add("https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p1910813120.jpg");
            urls.add("https://img1.doubanio.com/view/movie_poster_cover/lpst/public/p510876377.jpg");
            mHolder.rv_ad.setAdapter(new AdNormalAdapter(ctx, mHolder.rv_ad, urls));

            //设置指示器（顺序依次）
            //自定义指示器图片
            //设置圆点指示器颜色
            //设置文字指示器
            //隐藏指示器
            //mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
            mHolder.rv_ad.setHintView(new ColorPointHintView(ctx, ctx.getResources().getColor(R.color.color51b338), Color.WHITE));

            //mRollViewPager.setHintView(new TextHintView(this));
            //mRollViewPager.setHintView(null);
        } else {

            TeaSpaceTypeViewHolder mHolder = (TeaSpaceTypeViewHolder) holder;
            GridLayoutManager manager = new GridLayoutManager(ctx, 4);
            List<String> types = new ArrayList<>();
            types.add("棋牌室");
            types.add("WIFI");
            types.add("停车位");
            types.add("投影");
            types.add("棋牌室");
            types.add("WIFI");
            types.add("停车位");
            types.add("投影");

            TeaSpaceTypeContentAdapter mAdapter = new TeaSpaceTypeContentAdapter(ctx, types);
            mHolder.rv_type.setLayoutManager(manager);
            mHolder.rv_type.setAdapter(mAdapter);
            mHolder.rv_type.addItemDecoration(new ItemDecoration(ctx, LinearLayout.HORIZONTAL, 15, "#fbfbfb"));
        }


    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_SHOW_AD;
        } else if (position == 5) {
            return VIEW_SHOW_TYPE;
        }
        return VIEW_SHOW_CONTENT;
    }

    public  class TeaSpaceViewHolder extends BaseViewHolder {
        public ImageView iv_img;
        public TextView tv_state;
        public TextView tv_title;
        public TextImage tv_count;
        public TextImage tv_pos;

        public TeaSpaceViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_state = (TextView) itemView.findViewById(R.id.tv_state);
            tv_count = (TextImage) itemView.findViewById(R.id.tv_count);
            tv_pos = (TextImage) itemView.findViewById(R.id.tv_pos);
        }

        @Override
        protected void onClick(int layoutPosition) {
            Util.skip(((Activity) ctx), TeaSpaceDescActivity.class);
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
        }

        @Override
        protected boolean isCanOnclick() {
            return false;
        }
    }


}
