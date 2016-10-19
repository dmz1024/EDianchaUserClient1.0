package client.ediancha.com.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.TextHintView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.adapter.AdNormalAdapter;
import client.ediancha.com.adapter.EvaluateAdapter;
import client.ediancha.com.adapter.TeaSpaceDescInfoAdapter;
import client.ediancha.com.adapter.TeaSpaceDescRecommendAdapter;
import client.ediancha.com.base.SingleNetWorkBaseFragment;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.TeaDesc;
import client.ediancha.com.entity.TeaEventDesc;
import client.ediancha.com.entity.TeaSpaceDesc;
import client.ediancha.com.interfaces.ScrollViewListener;
import client.ediancha.com.myview.RatingBar;
import client.ediancha.com.myview.ScrollChangedScrollView;
import client.ediancha.com.myview.TextImage;
import client.ediancha.com.myview.TitleRelativeLayout;

/**
 * Created by dengmingzhi on 16/10/17.
 */

public class TeaDescFragment extends SingleNetWorkBaseFragment<TeaDesc> {
    private RollPagerView rollPagerView;
    private Toolbar toolbar;
    private TextView tv_name;
    private TextView tv_count_info;
    private TitleRelativeLayout trl_name;
    private TitleRelativeLayout trl_tel;
    private TabLayout tablayout;
    private TextImage tv_buy_car;
    private TextView tv_add_buy_car;
    private TextView tv_buy;
    private ViewPager vp_content;
    private ScrollChangedScrollView scrollView;

    @Override
    protected String getUrl() {
        return "top250";
    }

    @Override
    protected Map<String, String> getMap() {
        map.put("start", "0");
        map.put("count", "10");
        return map;
    }


    @Override
    protected ShowCurrentViewENUM getDefaultView() {
        return ShowCurrentViewENUM.VIEW_IS_LOADING;
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
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        scrollView = (ScrollChangedScrollView) view.findViewById(R.id.scrollView);
        tv_count_info = (TextView) view.findViewById(R.id.tv_count_info);
        trl_name = (TitleRelativeLayout) view.findViewById(R.id.trl_name);
        trl_tel = (TitleRelativeLayout) view.findViewById(R.id.trl_tel);
        tablayout = (TabLayout) view.findViewById(R.id.tablayout);
        tv_buy_car = (TextImage) view.findViewById(R.id.tv_buy_car);
        tv_add_buy_car = (TextView) view.findViewById(R.id.tv_add_buy_car);
        tv_buy = (TextView) view.findViewById(R.id.tv_buy);
        vp_content = (ViewPager) view.findViewById(R.id.vp_content);
        return view;
    }


    /**
     * 设置toolBar效果
     */
    private void initToolBar() {
        toolbar.setTitle("");
        scrollView.setScrollViewListener(new ScrollViewListener() {
            int rollPagerView_height = -1;

            @Override
            public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {
                if (rollPagerView_height == -1) {
                    rollPagerView_height = rollPagerView.getHeight();
                }

                if (y >= rollPagerView_height) {
                    toolbar.setAlpha(1f);
                    tv_name.setAlpha(1f);
                } else {
                    toolbar.setAlpha(0f);
                    tv_name.setAlpha(0f);

                }
            }
        });

    }


    /**
     * 填充轮播图
     */
    private void fillRollPageView() {
        rollPagerView.setPlayDelay(2500);
        rollPagerView.setAnimationDurtion(500);
        List<String> urls = new ArrayList<>();
        urls.add("https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p480747492.jpg");
        urls.add("https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p511118051.jpg");
        urls.add("https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p1910813120.jpg");
        urls.add("https://img1.doubanio.com/view/movie_poster_cover/lpst/public/p510876377.jpg");
        rollPagerView.setAdapter(new AdNormalAdapter(getContext(), rollPagerView, urls));
        rollPagerView.setHintView(new TextHintView(getContext()));
    }

    @Override
    protected void writeData(TeaDesc t) {
        initToolBar();
        fillRollPageView();
        fillTabLayout();

    }

    private void fillTabLayout() {
        final String[] titles = {"图文详情", "用户评价"};
        final List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(EvaluateFragment.getInstance(false,false));
        fragmentList.add(EvaluateFragment.getInstance(false,false));
        vp_content.setOffscreenPageLimit(fragmentList.size());
        vp_content.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });

        tablayout.setupWithViewPager(vp_content);


    }


    @Override
    protected boolean getCanRefresh() {
        return false;
    }


    @Override
    public boolean isCanRefresh() {
        return false;
    }
}