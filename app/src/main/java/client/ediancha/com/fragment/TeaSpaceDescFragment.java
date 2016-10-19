package client.ediancha.com.fragment;

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
import client.ediancha.com.activity.MoreEvaluateActivity;
import client.ediancha.com.activity.MoreTeaPackageActivity;
import client.ediancha.com.adapter.AdNormalAdapter;
//import client.ediancha.com.adapter.EvaluateAdapter;
import client.ediancha.com.adapter.TeaEventDescEventAdapter;
import client.ediancha.com.adapter.TeaSpaceDescInfoAdapter;
import client.ediancha.com.adapter.TeaSpaceDescRecommendAdapter;
import client.ediancha.com.base.SingleNetWorkBaseFragment;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.TeaEventDesc;
import client.ediancha.com.entity.TeaSpaceDesc;
import client.ediancha.com.interfaces.ScrollViewListener;
import client.ediancha.com.myview.Color2Text;
import client.ediancha.com.myview.RatingBar;
import client.ediancha.com.myview.ScrollChangedScrollView;
import client.ediancha.com.myview.TitleRelativeLayout;
import client.ediancha.com.util.Util;

import static client.ediancha.com.R.id.view;

/**
 * Created by dengmingzhi on 16/10/17.
 */

public class TeaSpaceDescFragment extends SingleNetWorkBaseFragment<TeaSpaceDesc> {
    private RollPagerView rollPagerView;
    private TextView tv_name;
    private RatingBar ratingbar;
    private TextView tv_ratingbar;
    private TextView tv_ping_num;
    private TextView tv_price;
    private TitleRelativeLayout trl_address;
    private TitleRelativeLayout trl_tel;
    private TitleRelativeLayout trl_tea_package;
    private TitleRelativeLayout trl_tea_recommend;
    private TitleRelativeLayout trl_tea_info;
    private TitleRelativeLayout trl_immediately;
    private TitleRelativeLayout trl_other;
    private TitleRelativeLayout trl_evaluate;
    private TextView tv_package_name;
    private TextView tv_package_content;
    private TextView tv_package_price;
    private TextView tv_package_old_price;
    private TextView tv_info;
    private ImageView iv_package;
    private RecyclerView rv_recommend;
    private RecyclerView rv_info;
    private RecyclerView rv_evaluate;
    private TextView tv_introduce_name;
    private TextView tv_introduce_content;
    private TextView tv_event_content;
    private Button bt_more;
    private Toolbar toolbar;
    private TextView tv_title;
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
    protected Class<TeaSpaceDesc> getTClass() {
        return TeaSpaceDesc.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_tea_space_desc, null);
        rollPagerView = (RollPagerView) view.findViewById(R.id.roll_view_pager);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        ratingbar = (RatingBar) view.findViewById(R.id.ratingbar);
        tv_ratingbar = (TextView) view.findViewById(R.id.tv_ratingbar);
        tv_ping_num = (TextView) view.findViewById(R.id.tv_ping_num);
        tv_price = (TextView) view.findViewById(R.id.tv_price);
        trl_address = (TitleRelativeLayout) view.findViewById(R.id.trl_address);
        trl_tea_recommend = (TitleRelativeLayout) view.findViewById(R.id.trl_tea_recommend);
        trl_evaluate = (TitleRelativeLayout) view.findViewById(R.id.trl_evaluate);
        trl_tea_info = (TitleRelativeLayout) view.findViewById(R.id.trl_tea_info);
        trl_other = (TitleRelativeLayout) view.findViewById(R.id.trl_other);
        trl_tel = (TitleRelativeLayout) view.findViewById(R.id.trl_tel);
        trl_tea_package = (TitleRelativeLayout) view.findViewById(R.id.trl_tea_package);
        tv_package_name = (TextView) view.findViewById(R.id.tv_package_name);
        iv_package = (ImageView) view.findViewById(R.id.iv_package);
        tv_package_content = (TextView) view.findViewById(R.id.tv_package_content);
        tv_package_price = (TextView) view.findViewById(R.id.tv_package_price);
        tv_package_old_price = (TextView) view.findViewById(R.id.tv_package_old_price);
        rv_recommend = (RecyclerView) view.findViewById(R.id.rv_recommend);
        rv_info = (RecyclerView) view.findViewById(R.id.rv_info);
        tv_info = (TextView) view.findViewById(R.id.tv_info);
        tv_introduce_name = (TextView) view.findViewById(R.id.tv_introduce_name);
        tv_introduce_content = (TextView) view.findViewById(R.id.tv_introduce_content);
        tv_event_content = (TextView) view.findViewById(R.id.tv_event_content);
        trl_immediately = (TitleRelativeLayout) view.findViewById(R.id.trl_immediately);
        rv_evaluate = (RecyclerView) view.findViewById(R.id.rv_evaluate);
        bt_more = (Button) view.findViewById(R.id.bt_more);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        scrollView = (ScrollChangedScrollView) view.findViewById(R.id.scrollView);


        bt_more.setOnClickListener(this);
        trl_tea_package.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.bt_more:
                Util.skip(getActivity(), MoreEvaluateActivity.class);
                break;
            case R.id.trl_tea_package:
                Util.skip(getActivity(), MoreTeaPackageActivity.class);
                break;
        }
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
                    tv_title.setAlpha(1f);
                } else {
                    toolbar.setAlpha(0f);
                    tv_title.setAlpha(0f);

                }
            }
        });

    }

    /**
     * 填充评价
     */
    private void fillEvaluate() {
//        TeaEventDesc.Event event1 = new TeaEventDesc.Event();
//        event1.image = Constant.IMAGE;
//
//        List<TeaEventDesc.Event> events = new ArrayList<>();
//        events.add(event1);
//        LinearLayoutManager manager = new LinearLayoutManager(getContext()) {
//            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
//        };
//        rv_evaluate.addItemDecoration(new ItemDecoration(getContext(), LinearLayout.VERTICAL, 2, "#ebebeb"));
//        rv_evaluate.setLayoutManager(manager);
//        rv_evaluate.setAdapter(new EvaluateAdapter(getContext(), events));
    }

    /**
     * 填充轮播图
     */
    private void fillRollPageView() {
        //设置播放时间间隔
        rollPagerView.setPlayDelay(2500);
        //设置透明度
        rollPagerView.setAnimationDurtion(500);
        //设置适配器
        List<String> urls = new ArrayList<>();
        urls.add("https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p480747492.jpg");
        urls.add("https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p511118051.jpg");
        urls.add("https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p1910813120.jpg");
        urls.add("https://img1.doubanio.com/view/movie_poster_cover/lpst/public/p510876377.jpg");
        rollPagerView.setAdapter(new AdNormalAdapter(getContext(), rollPagerView, urls));

        //mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
//        rollPagerView.setHintView(new ColorPointHintView(getContext(), getContext().getResources().getColor(R.color.color51b338), Color.WHITE));

        rollPagerView.setHintView(new TextHintView(getContext()));
        //mRollViewPager.setHintView(null);
    }

    @Override
    protected void writeData(TeaSpaceDesc t) {
        fillRollPageView();
        fillEvaluate();
        initToolBar();
        fillRecommend();
        fillRvInfo();
        Glide.with(getContext()).load(Constant.IMAGE).into(iv_package);

    }

    /**
     * 填充商户信息
     */
    private void fillRvInfo() {
        List<TeaSpaceDesc.Event> events = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TeaSpaceDesc.Event event = new TeaSpaceDesc.Event();
            event.title = "停车位";
            event.image = Constant.IMAGE;
            events.add(event);
        }

        GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
        rv_info.setLayoutManager(manager);

        rv_info.setAdapter(new TeaSpaceDescInfoAdapter(getContext(), events));
    }

    /**
     * 填充包厢推荐
     */
    private void fillRecommend() {
        List<TeaSpaceDesc.Event> events = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TeaSpaceDesc.Event event = new TeaSpaceDesc.Event();
            event.title = "这个世";
            event.image = Constant.IMAGE;
            events.add(event);
        }

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayout.HORIZONTAL);
        rv_recommend.setLayoutManager(manager);
        rv_recommend.setAdapter(new TeaSpaceDescRecommendAdapter(getContext(), events));
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
