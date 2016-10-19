package client.ediancha.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import client.ediancha.com.myview.TitleRelativeLayout;

/**
 * Created by dengmingzhi on 16/10/17.
 */

public class TeaEventDescFragment extends SingleNetWorkBaseFragment<TeaEventDesc> {
    private String id;
    private Color2Text tv_price;
    private RollPagerView rollPagerView;
    private TextView tv_name;
    private TitleRelativeLayout trl_name;
    private TitleRelativeLayout trl_time;
    private TitleRelativeLayout trl_address;
    private TitleRelativeLayout trl_num;
    private TextView tv_event_desc;
    private TextView tv_apply;
    private RecyclerView rv_event;
    private ScrollChangedScrollView scrollView;
    private ScrollViewListener scrollViewListener;

    public static TeaEventDescFragment getInstance(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        TeaEventDescFragment teaEventDescFragment = new TeaEventDescFragment();
        teaEventDescFragment.setArguments(bundle);
        return teaEventDescFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getString("id");
    }

    @Override
    protected String getUrl() {
        return "app.php";
    }

    @Override
    protected Map<String, String> getMap() {
        map.put("pigcms_id", id);
        map.put("c", "chahui");
        map.put("a", "show");
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
        rollPagerView = (RollPagerView) view.findViewById(R.id.roll_view_pager);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        trl_name = (TitleRelativeLayout) view.findViewById(R.id.trl_name);
        trl_time = (TitleRelativeLayout) view.findViewById(R.id.trl_time);
        trl_address = (TitleRelativeLayout) view.findViewById(R.id.trl_address);
        trl_num = (TitleRelativeLayout) view.findViewById(R.id.trl_num);
        tv_event_desc = (TextView) view.findViewById(R.id.tv_event_desc);
        tv_apply = (TextView) view.findViewById(R.id.tv_apply);
        rv_event = (RecyclerView) view.findViewById(R.id.rv_event);
        rollPagerView.setPlayDelay(0);
        rollPagerView.setAnimationDurtion(500);
        rollPagerView.setHintView(new TextHintView(getContext()));
        scrollView = (ScrollChangedScrollView) view.findViewById(R.id.scrollView);
        scrollView.setScrollViewListener(scrollViewListener);
        return view;
    }

    @Override
    protected void writeData(TeaEventDesc t) {
        fillRollPager(t.data.show.images);
        fillEvent(t.data.list);
        fillData(t.data.show);
    }


    /**
     * 详情
     *
     * @param show
     */
    private void fillData(TeaEventDesc.Show show) {
        tv_name.setText(show.name);
        tv_price.setTextNotChange(show.price);
        trl_name.setTitle(show.storename);
        trl_address.setTitle(show.address);
        trl_num.setTitle("已报名人数：" + show.bm + " | 可报名总数：" + show.renshu);
        trl_time.setTitle(show.sttime + " -- " + show.endtime);
        tv_event_desc.setText(show.content);
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
        rollPagerView.setAdapter(new AdNormalAdapter(getContext(), rollPagerView, urls));
    }


    @Override
    protected boolean getCanRefresh() {
        return false;
    }


    @Override
    public boolean isCanRefresh() {
        return false;
    }


    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }
}
