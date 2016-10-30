package client.ediancha.com.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.viewanimator.ViewAnimator;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.TextHintView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.activity.BuyTeaActivity;
import client.ediancha.com.activity.MoreEvaluateActivity;
import client.ediancha.com.activity.PackageOrderActivity;
import client.ediancha.com.adapter.AdNormalAdapter;
import client.ediancha.com.base.WebBaseFragment;
import client.ediancha.com.entity.PackageDesc;
import client.ediancha.com.entity.TeaDesc;
import client.ediancha.com.myview.Color2Text;
import client.ediancha.com.myview.ScrollChangedScrollView;
import client.ediancha.com.myview.TextImage;
import client.ediancha.com.myview.TitleRelativeLayout;
import client.ediancha.com.util.Util;

//import client.ediancha.com.adapter.EvaluateAdapter;

/**
 * Created by dengmingzhi on 16/10/17.
 */

public class TeaPackageDescFragment extends TeaDescBaseFragment<PackageDesc> {
    private RollPagerView rollPagerView;
    private TextView tv_name;
    private TextView tv_count;
    private TextView tv_order;
    private TitleRelativeLayout trl_name;
    private TitleRelativeLayout trl_tel;
    private TextView tv_price;
    private WebBaseFragment webFragment;
    private TextView tv_desc;
    private FrameLayout webLayout;
    private TextView tv_preference_title;
    private TextView tv_preference_content;
    private boolean isShow;
    private PackageDesc data;


    public static TeaPackageDescFragment getInstance(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        TeaPackageDescFragment teaProductDescFragment = new TeaPackageDescFragment();
        teaProductDescFragment.setArguments(bundle);
        return teaProductDescFragment;
    }

    @Override
    protected Map<String, String> getMap() {
        map.put("c", "chaguan");
        map.put("a", "bx_show");
        map.put("id", id);
        return map;
    }

    @Override
    protected Class<PackageDesc> getTClass() {
        return PackageDesc.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_tea_pageage_desc, null);
        rollPagerView = (RollPagerView) view.findViewById(R.id.roll_view_pager);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_price = (TextView) view.findViewById(R.id.tv_price);
        scrollView = (ScrollChangedScrollView) view.findViewById(R.id.scrollView);
        tv_count = (TextView) view.findViewById(R.id.tv_count);
        trl_name = (TitleRelativeLayout) view.findViewById(R.id.trl_name);
        trl_tel = (TitleRelativeLayout) view.findViewById(R.id.trl_tel);
        trl_tel.setOnClickListener(this);
        trl_name.setOnClickListener(this);
        tv_order = (TextView) view.findViewById(R.id.tv_order);
        tv_desc = (TextView) view.findViewById(R.id.tv_desc);
        tv_preference_title = (TextView) view.findViewById(R.id.tv_preference_title);
        tv_preference_content = (TextView) view.findViewById(R.id.tv_preference_content);
        webLayout = (FrameLayout) view.findViewById(R.id.fg_desc);
        webLayout.setOnClickListener(this);
        tv_desc.setOnClickListener(this);
        tv_order.setOnClickListener(this);
        tv_preference_title.setOnClickListener(this);
        tv_preference_content.setOnClickListener(this);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rollPagerView.getLayoutParams();
        layoutParams.height = Util.getWidth();
        rollPagerView.setLayoutParams(layoutParams);

        return view;
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_desc:
                showDesc();
                break;
            case R.id.trl_tel:
                Util.tel(getContext(), data.data.phone1 + data.data.phone2);
                break;
            case R.id.tv_order:
                Intent intent=new Intent(getContext(),PackageOrderActivity.class);
                intent.putExtra("cz_id",data.data.cz_id);
                intent.putExtra("physical_id",data.data.physical_id);
                startActivity(intent);
                break;
        }
    }

    public void hideDesc() {
        ViewAnimator.animate(scrollView).translationY(-Util.getHeight(), 0).andAnimate(webLayout).translationY(0, Util.getHeight()).duration(400).start();
        isShow = false;
    }

    private void showDesc() {
        if (!webFragment.getUserVisibleHint()) {
            webFragment.setUserVisibleHint(true);
        }
        ViewAnimator.animate(scrollView).translationY(0, -Util.getHeight()).andAnimate(webLayout).translationY(Util.getHeight(), 0).duration(400).start();
        isShow = true;
    }
//    cz_id：86

    /**
     * 填充轮播图
     *
     * @param images
     */
    private void fillRollPageView(List<String> images) {
        rollPagerView.setPlayDelay(images.size() > 1 ? 2500 : 0);
        rollPagerView.setAnimationDurtion(500);
        rollPagerView.setAdapter(new AdNormalAdapter(getContext(), rollPagerView, images,true));
        rollPagerView.setHintView(new TextHintView(getContext()));
    }

    @Override
    protected void writeData(PackageDesc t) {
        data = t;
        fillRollPageView(t.data.images);
        fillTabLayout(t.data.conten);
        show(t.data);
        shareInfo.title = t.data.share.name;
        shareInfo.url = t.data.share.url;
        shareInfo.logo = t.data.share.logo;
        shareInfo.content = t.data.share.info;

    }

    /**
     * 填充数据
     *
     * @param data
     */
    private void show(PackageDesc.Data data) {
        tv_name.setText(data.name);
        tv_price.setText(TextUtils.equals("免费", data.price) ? "免费" : data.price+"元/小时");
        tv_count.setText("容纳人数：" + data.renshu+"人");
        tv_preference_content.setText(TextUtils.isEmpty(data.sale) ? "暂无优惠政策" : data.sale);
        trl_name.setTitle(data.store_name);
        trl_tel.setTitle(data.phone1 + "-" + data.phone2);
    }

    private void fillTabLayout(String url) {
        getChildFragmentManager().beginTransaction().replace(R.id.fg_desc, webFragment = WebBaseFragment.getInstance(url, true, false)).commit();
//        webFragment.getWebView().setWebViewScrollListener(new WebViewScrollListener() {
//            @Override
//            public void onScrollChanged(MyWebView webView, int x, int y, int oldx, int oldy) {
//                Log.d("gundong", x + "-" + y);
//            }
//        });
    }


    public boolean getShow() {
        return isShow;
    }


}
