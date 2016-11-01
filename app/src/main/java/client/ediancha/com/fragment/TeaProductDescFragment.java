package client.ediancha.com.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
import client.ediancha.com.activity.BuyCarActivity;
import client.ediancha.com.activity.BuyTeaActivity;
import client.ediancha.com.activity.MoreEvaluateActivity;
import client.ediancha.com.activity.OfficialStoreInfoActivity;
import client.ediancha.com.adapter.AdNormalAdapter;
//import client.ediancha.com.adapter.EvaluateAdapter;
import client.ediancha.com.adapter.EvaluateAdapter;
import client.ediancha.com.base.WebBaseFragment;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.entity.TeaSpaceDesc;
import client.ediancha.com.interfaces.WebViewScrollListener;
import client.ediancha.com.myview.ChooseBuyCarView;
import client.ediancha.com.myview.Color2Text;
import client.ediancha.com.myview.MyWebView;
import client.ediancha.com.processor.BuyCarUtil;
import client.ediancha.com.processor.ShareUtil;
import client.ediancha.com.base.SingleNetWorkBaseFragment;
import client.ediancha.com.entity.TeaDesc;
import client.ediancha.com.interfaces.ScrollViewListener;
import client.ediancha.com.interfaces.ShareInfoInterface;
import client.ediancha.com.myview.ScrollChangedScrollView;
import client.ediancha.com.myview.TextImage;
import client.ediancha.com.myview.TitleRelativeLayout;
import client.ediancha.com.util.MyToast;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 16/10/17.
 */

public class TeaProductDescFragment extends TeaDescBaseFragment<TeaDesc> {
    private RollPagerView rollPagerView;
    private TextView tv_name;
    private TextView tv_count_info;
    private TitleRelativeLayout trl_name;
    private TitleRelativeLayout trl_tel;
    private TitleRelativeLayout trl_comment;
    private TextImage tv_buy_car;
    private TextView tv_add_buy_car;
    private TextView tv_buy;
    private Color2Text tv_price;
    private WebBaseFragment webFragment;
    private TextView tv_desc;
    private FrameLayout webLayout;
    private boolean isShow;

    private TeaDesc data;

    public static TeaProductDescFragment getInstance(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        TeaProductDescFragment teaProductDescFragment = new TeaProductDescFragment();
        teaProductDescFragment.setArguments(bundle);
        return teaProductDescFragment;
    }

    @Override
    protected Map<String, String> getMap() {
        map.put("c", "chanpin");
        map.put("a", "show");
        map.put("id", id);
        return map;
    }

    @Override
    protected Class<TeaDesc> getTClass() {
        return TeaDesc.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_tea_desc, null);
        rollPagerView = (RollPagerView) view.findViewById(R.id.roll_view_pager);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_price = (Color2Text) view.findViewById(R.id.tv_price);
        scrollView = (ScrollChangedScrollView) view.findViewById(R.id.scrollView);
        tv_count_info = (TextView) view.findViewById(R.id.tv_count_info);
        trl_name = (TitleRelativeLayout) view.findViewById(R.id.trl_name);
        trl_comment = (TitleRelativeLayout) view.findViewById(R.id.trl_comment);
        trl_tel = (TitleRelativeLayout) view.findViewById(R.id.trl_tel);
        trl_tel.setOnClickListener(this);
        tv_buy_car = (TextImage) view.findViewById(R.id.tv_buy_car);
        tv_add_buy_car = (TextView) view.findViewById(R.id.tv_add_buy_car);
        tv_buy = (TextView) view.findViewById(R.id.tv_buy);
        tv_desc = (TextView) view.findViewById(R.id.tv_desc);
        webLayout = (FrameLayout) view.findViewById(R.id.fg_desc);
        webLayout.setOnClickListener(this);
        tv_desc.setOnClickListener(this);
        tv_buy.setOnClickListener(this);
        tv_add_buy_car.setOnClickListener(this);
        tv_buy_car.setOnClickListener(this);
        trl_name.setOnClickListener(this);

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
            case R.id.trl_name:
                Intent intent5 = new Intent(getContext(), OfficialStoreInfoActivity.class);
                intent5.putExtra("title", data.data.store_name);
                intent5.putExtra("id", data.data.store_id);
                startActivity(intent5);
                break;
            case R.id.tv_buy_car:
                if (TextUtils.isEmpty(UserInfo.uid)) {
                    MyToast.showToast("请先登录");
                    return;
                } else {
                    Util.skip(getActivity(), BuyCarActivity.class);
                }
                break;
            case R.id.trl_comment:
                Intent intent = new Intent(getContext(), MoreEvaluateActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("type", "PRODUCT");
                startActivity(intent);
                break;
            case R.id.trl_tel:
                Util.tel(getContext(), data.data.phone1 + data.data.phone2);
                break;
            case R.id.tv_buy:
                buy();
                break;
            case R.id.tv_add_buy_car:
                new ChooseBuyCarView(getContext(), data.data.property, data.data.sku_list) {
                    @Override
                    protected void chooseOk(int count, String sku_id) {
                        BuyCarUtil.getInstance().setContext(getContext()).addBuyCar(count, sku_id, id);
                    }
                }.showAtLocation();
                break;
        }
    }


    /**
     * 直接购买
     */
    private void buy() {
        new ChooseBuyCarView(getContext(), data.data.property, data.data.sku_list) {
            @Override
            protected void chooseOk(int count, String sku_id) {
                BuyCarUtil.getInstance().setContext(getContext()).buy(count, sku_id, id);
            }
        }.showAtLocation();
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

    /**
     * 填充轮播图
     *
     * @param images
     */
    private void fillRollPageView(List<TeaDesc.Image> images) {
        rollPagerView.setPlayDelay(images.size() > 1 ? 2500 : 0);
        rollPagerView.setAnimationDurtion(500);
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            urls.add(images.get(i).image);
        }
        rollPagerView.setAdapter(new AdNormalAdapter(getContext(), rollPagerView, urls, true));
        rollPagerView.setHintView(new TextHintView(getContext()));
    }

    @Override
    protected void writeData(TeaDesc t) {
        data = t;
        fillRollPageView(t.data.images);
        fillTabLayout(t.data.content);
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
    private void show(TeaDesc.Data data) {
        tv_name.setText(data.name);
        tv_price.setTextNotChange(data.price);
        tv_count_info.setText("运费：￥" + data.postage + "       剩余：" + data.quantity + "件");
        trl_name.setTitle(data.store_name);
        trl_tel.setTitle(data.phone1 + "-" + data.phone2);
        int coomentCount;
        if (data.comment != null) {
            coomentCount = data.comment.size();
        } else {
            coomentCount = 0;
        }

        if (coomentCount > 0) {
            trl_comment.setTitle("商品评价(" + coomentCount + ")");
            trl_comment.setOnClickListener(this);
        } else {
            trl_comment.setTitle("该商品暂无评价");
            trl_comment.setContent("");
        }

    }

    private void fillTabLayout(String url) {
        getChildFragmentManager().beginTransaction().replace(R.id.fg_desc, webFragment = WebBaseFragment.getInstance(url, true, false)).commit();
    }


    public boolean getShow() {
        return isShow;
    }


}
