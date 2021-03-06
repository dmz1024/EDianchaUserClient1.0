package client.ediancha.com.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;

import java.util.List;

import client.ediancha.com.myview.BannerImageVIew;
import client.ediancha.com.myview.PinchImageView;
import client.ediancha.com.myview.TouchImageView;
import client.ediancha.com.util.GlideUtil;

/**
 * Created by dengmingzhi on 16/10/13.
 */

public class BannerNormalAdapter extends LoopPagerAdapter {
    //    private int[] imgs = {
//            R.mipmap.center_address,
//            R.mipmap.center_apply,
//            R.mipmap.center_appointment_order,
//            R.mipmap.center_buy_car,
//    };
    private List<String> urls;
    private Context ctx;

    public BannerNormalAdapter(Context ctx, RollPagerView viewPager, List<String> urls) {
        super(viewPager);
        this.urls = urls;
        this.ctx = ctx;
    }


    public BannerNormalAdapter(RollPagerView viewPager) {
        super(viewPager);
    }

    @Override
    public View getView(ViewGroup container, final int position) {
        PinchImageView view = new PinchImageView(container.getContext());
        view.setScaleType(ImageView.ScaleType.FIT_CENTER);
        Glide.with(ctx).load(urls.get(position)).into(view);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    @Override
    public int getRealCount() {
        return urls.size();
    }

}
