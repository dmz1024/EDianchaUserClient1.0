package client.ediancha.com.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import client.ediancha.com.myview.PinchImageView;
/**
 * Created by dengmingzhi on 2016/10/27.
 */

public class BannerBigAdapter extends PagerAdapter {
    private List<String> list;
    private Context ctx;
    private List<PinchImageView> views = new ArrayList<>();

    public BannerBigAdapter(Context ctx, List<String> list) {
        this.ctx = ctx;
        this.list = list;
        creatView();
    }

    private void creatView() {
        for (int i = 0; i < list.size(); i++) {
            PinchImageView view = new PinchImageView(ctx);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            Glide.with(ctx).load(list.get(i)).into(view);
            views.add(view);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position), 0);//添加页卡
        return views.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }
}
