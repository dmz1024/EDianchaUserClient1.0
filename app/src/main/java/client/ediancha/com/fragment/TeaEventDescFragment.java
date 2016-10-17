package client.ediancha.com.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.TextHintView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.adapter.AdNormalAdapter;
import client.ediancha.com.adapter.TeaEventDescEventAdapter;
import client.ediancha.com.base.SingleNetWorkBaseFragment;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.TeaEventDesc;
import client.ediancha.com.interfaces.ScrollViewListener;
import client.ediancha.com.myview.Color2Text;
import client.ediancha.com.myview.ScrollChangedScrollView;

/**
 * Created by dengmingzhi on 16/10/17.
 */

public class TeaEventDescFragment extends SingleNetWorkBaseFragment<TeaEventDesc> {
    private Color2Text tv_price;

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
    protected Class<TeaEventDesc> getTClass() {
        return TeaEventDesc.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_tea_event_desc, null);
        tv_price = (Color2Text) view.findViewById(R.id.tv_price);
        final RollPagerView rollPagerView = (RollPagerView) view.findViewById(R.id.roll_view_pager);
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





        RecyclerView rv_event = (RecyclerView) view.findViewById(R.id.rv_event);
        TeaEventDesc.Event event1 = new TeaEventDesc.Event();
        event1.title = "这个世界是很神奇的";
        event1.image = Constant.IMAGE;

        TeaEventDesc.Event event2 = new TeaEventDesc.Event();
        event2.title = "这个世界是很神奇的";
        event2.image = Constant.IMAGE;

        List<TeaEventDesc.Event> events = new ArrayList<>();
        events.add(event1);
        events.add(event2);

        LinearLayoutManager manager = new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv_event.addItemDecoration(new ItemDecoration(getContext(), LinearLayout.VERTICAL, 2, "#ebebeb"));
        rv_event.setLayoutManager(manager);
        rv_event.setAdapter(new TeaEventDescEventAdapter(getContext(), events));

        final Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("");

        final TextView tv_title = (TextView) view.findViewById(R.id.tv_title);

        ScrollChangedScrollView scrollView = (ScrollChangedScrollView) view.findViewById(R.id.scrollView);

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
        return view;
    }

    @Override
    protected void writeData(TeaEventDesc t) {
        tv_price.setTextNotChange(t.count + "");
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
