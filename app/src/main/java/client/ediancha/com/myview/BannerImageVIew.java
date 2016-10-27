package client.ediancha.com.myview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.adapter.BannerBigAdapter;
import client.ediancha.com.base.PopBaseView;

/**
 * Created by dengmingzhi on 2016/10/27.
 */

public class BannerImageVIew extends PopBaseView {
    private List<String> list;
    private int position;

    public BannerImageVIew(Context ctx) {
        super(ctx);
    }

    public BannerImageVIew(Context ctx, List<String> list, int position) {
        super(ctx);
        this.list = list;
        this.position = position;
    }

    @Override
    protected View getView() {
        View view = View.inflate(ctx, R.layout.pop_banner_image, null);
        final TextView tv_page = (TextView) view.findViewById(R.id.tv_page);
        ViewPager pagerView = (ViewPager) view.findViewById(R.id.view_pager);
        pagerView.setAdapter(new BannerBigAdapter(ctx, list));
        pagerView.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tv_page.setText((position + 1) + "/" + list.size());
            }
        });
        tv_page.setText((position + 1) + "/" + list.size());
        pagerView.setCurrentItem(position, false);
        return view;
    }


    @Override
    protected int getGravity() {
        return Gravity.CENTER;
    }

    @Override
    protected int getAnimation() {
        return R.style.pop_message;
    }
}
