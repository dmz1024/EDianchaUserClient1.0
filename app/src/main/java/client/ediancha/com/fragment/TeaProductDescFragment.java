package client.ediancha.com.fragment;

import android.app.Activity;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.florent37.viewanimator.ViewAnimator;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.TextHintView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.adapter.AdNormalAdapter;
//import client.ediancha.com.adapter.EvaluateAdapter;
import client.ediancha.com.adapter.EvaluateAdapter;
import client.ediancha.com.base.WebBaseFragment;
import client.ediancha.com.entity.TeaSpaceDesc;
import client.ediancha.com.myview.Color2Text;
import client.ediancha.com.processor.ShareUtil;
import client.ediancha.com.base.SingleNetWorkBaseFragment;
import client.ediancha.com.entity.TeaDesc;
import client.ediancha.com.interfaces.ScrollViewListener;
import client.ediancha.com.interfaces.ShareInfoInterface;
import client.ediancha.com.myview.ScrollChangedScrollView;
import client.ediancha.com.myview.TextImage;
import client.ediancha.com.myview.TitleRelativeLayout;
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
    private TextImage tv_buy_car;
    private TextView tv_add_buy_car;
    private TextView tv_buy;
    private Color2Text tv_price;
    private WebBaseFragment webFragment;
    private TextView tv_desc;
    private FrameLayout webLayout;

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
        scrollView.setScrollViewListener(scrollViewListener);
        tv_count_info = (TextView) view.findViewById(R.id.tv_count_info);
        trl_name = (TitleRelativeLayout) view.findViewById(R.id.trl_name);
        trl_tel = (TitleRelativeLayout) view.findViewById(R.id.trl_tel);
        tv_buy_car = (TextImage) view.findViewById(R.id.tv_buy_car);
        tv_add_buy_car = (TextView) view.findViewById(R.id.tv_add_buy_car);
        tv_buy = (TextView) view.findViewById(R.id.tv_buy);
        tv_desc = (TextView) view.findViewById(R.id.tv_desc);
        webLayout = (FrameLayout) view.findViewById(R.id.fg_desc);
        webLayout.setOnClickListener(this);
        tv_desc.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_desc:
                showDesc();
                break;
             case R.id.fg_desc:
                hideDesc();
                break;
        }
    }

    public void hideDesc() {
        scrollView.setVisibility(View.VISIBLE);
        ViewAnimator.animate(scrollView).translationY(-Util.getHeight(), 0).duration(300).start();
    }

    private void showDesc() {
        webFragment.setUserVisibleHint(true);
        scrollView.setVisibility(View.GONE);
        ViewAnimator.animate(webLayout).translationY(Util.getHeight(), 0).duration(300).start();
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
        rollPagerView.setAdapter(new AdNormalAdapter(getContext(), rollPagerView, urls));
        rollPagerView.setHintView(new TextHintView(getContext()));
    }

    @Override
    protected void writeData(TeaDesc t) {
        fillRollPageView(t.data.images);
        fillTabLayout("https://www.baidu.com");
        show(t.data);

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
    }

    private void fillTabLayout(String url) {
        getChildFragmentManager().beginTransaction().replace(R.id.fg_desc, webFragment = WebBaseFragment.getInstance(url, true, false)).commit();
    }

}
