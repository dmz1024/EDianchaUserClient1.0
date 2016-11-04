package client.ediancha.com.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.viewanimator.ViewAnimator;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.TextHintView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.activity.LoginActivity;
import client.ediancha.com.adapter.AdNormalAdapter;
import client.ediancha.com.adapter.TeaEventDescEventAdapter;
import client.ediancha.com.api.MyRetrofitUtil;
import client.ediancha.com.base.DescBaseActivity;
import client.ediancha.com.base.WebBaseFragment;
import client.ediancha.com.myview.MyWebView;
import client.ediancha.com.processor.ShareUtil;
import client.ediancha.com.base.SingleNetWorkBaseFragment;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.BaseEntity;
import client.ediancha.com.entity.TeaEventDesc;
import client.ediancha.com.interfaces.ScrollViewListener;
import client.ediancha.com.interfaces.ShareInfoInterface;
import client.ediancha.com.myview.Color2Text;
import client.ediancha.com.myview.PopMessageTips;
import client.ediancha.com.myview.PopTeaEventApply;
import client.ediancha.com.myview.ScrollChangedScrollView;
import client.ediancha.com.myview.TitleRelativeLayout;
import client.ediancha.com.processor.TeaEventApplyUtil;
import client.ediancha.com.util.MyToast;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 16/10/17.
 */

public class TeaEventDescFragment extends TeaDescBaseFragment<TeaEventDesc> {
    private Color2Text tv_price;
    private RollPagerView rollPagerView;
    private TextView tv_name;
    private TitleRelativeLayout trl_name;
    private TitleRelativeLayout trl_time;
    private TitleRelativeLayout trl_address;
    private TitleRelativeLayout trl_num;
    private Button bt_apply;
    private RecyclerView rv_event;
    private TeaEventDesc t;
    private MyWebView webView;
    private Color2Text tv_price_apply;

    public static TeaEventDescFragment getInstance(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        TeaEventDescFragment teaEventDescFragment = new TeaEventDescFragment();
        teaEventDescFragment.setArguments(bundle);
        return teaEventDescFragment;
    }


    @Override
    protected Map<String, String> getMap() {
        map.put("pigcms_id", id);
        map.put("c", "chahui");
        map.put("a", "show");
        return map;
    }

    @Override
    protected Class<TeaEventDesc> getTClass() {
        return TeaEventDesc.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_tea_event_desc, null);
        tv_price = (Color2Text) view.findViewById(R.id.tv_price);
        webView = (MyWebView) view.findViewById(R.id.webView);
        rollPagerView = (RollPagerView) view.findViewById(R.id.roll_view_pager);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_price_apply = (Color2Text) view.findViewById(R.id.tv_price_apply);
        trl_name = (TitleRelativeLayout) view.findViewById(R.id.trl_name);
        trl_time = (TitleRelativeLayout) view.findViewById(R.id.trl_time);
        trl_address = (TitleRelativeLayout) view.findViewById(R.id.trl_address);
        trl_num = (TitleRelativeLayout) view.findViewById(R.id.trl_num);
        bt_apply = (Button) view.findViewById(R.id.bt_apply);
        rv_event = (RecyclerView) view.findViewById(R.id.rv_event);
        rollPagerView.setPlayDelay(0);
        rollPagerView.setAnimationDurtion(500);
        rollPagerView.setHintView(new TextHintView(getContext()));
        scrollView = (ScrollChangedScrollView) view.findViewById(R.id.scrollView);
        scrollView.setScrollViewListener(scrollViewListener);
        bt_apply.setOnClickListener(this);
        trl_address.setOnClickListener(this);
        trl_name.setOnClickListener(this);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rollPagerView.getLayoutParams();
        layoutParams.height = (int) (Util.getWidth() / 1.75);
        rollPagerView.setLayoutParams(layoutParams);
        return view;
    }

    @Override
    protected void writeData(TeaEventDesc t) {
        super.writeData(t);
        this.t = t;
        fillRollPager(t.data.show.images);
        fillEvent(t.data.list);
        fillData(t.data.show);
        share(t.data.share);
    }

    private void share(TeaEventDesc.Share share) {
        shareInfo.content = share.info;
        shareInfo.logo = share.logo;
        shareInfo.url = share.url;
        shareInfo.title = share.name;
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.trl_address:
                Util.navigation(getContext(), t.data.show.zlat, t.data.show.zlong, 18, t.data.show.address);
                break;
            case R.id.bt_apply:
                TeaEventApplyUtil.getInstance().apply(getContext(), id);
                break;
            case R.id.trl_name:
                Intent intent = new Intent(getContext(), DescBaseActivity.class);
                intent.putExtra("title", t.data.show.storename);
                intent.putExtra("id", t.data.show.physical_id);
                intent.putExtra("type", 2);
                startActivity(intent);
                break;
        }
    }

    /**
     * 详情
     *
     * @param show
     */
    private void fillData(TeaEventDesc.Show show) {

        webView.loadUrl(t.data.show.content);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        tv_name.setText(show.name);
        if (TextUtils.equals(show.price, "免费")) {
            tv_price.setChange("", show.price);
            tv_price_apply.setChange(show.price, "");

        } else {
            tv_price.setTextNotChange(show.price);
            tv_price_apply.setContentNotChange("￥" + show.price);
        }

        trl_name.setTitle(show.storename);
        trl_address.setTitle(show.address);
        trl_num.setTitle("已报名人数：" + show.bm + " | 可报名总数：" + show.renshu);
        trl_time.setTitle(show.sttime + " -- " + show.endtime);

    }


    /**
     * 相关活动
     *
     * @param list
     */
    private void fillEvent(List<TeaEventDesc.Lists> list) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_event.addItemDecoration(new ItemDecoration(getContext(), LinearLayout.VERTICAL, 2, "#ebebeb"));
        rv_event.setLayoutManager(manager);
        rv_event.setAdapter(new TeaEventDescEventAdapter(getContext(), list));

    }


    /**
     * 轮播图
     *
     * @param images
     */
    private void fillRollPager(String images) {
        List<String> urls = new ArrayList<>();
        urls.add(images);
        rollPagerView.setAdapter(new AdNormalAdapter(getContext(), rollPagerView, urls, true));
    }


    @Override
    public ShareUtil.ShareInfo getShareInfo() {
        return shareInfo;
    }

}
