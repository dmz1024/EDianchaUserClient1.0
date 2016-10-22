package client.ediancha.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import client.ediancha.com.adapter.TeaSpaceDescInfoAdapter;
import client.ediancha.com.adapter.TeaSpaceDescRecommendAdapter;
import client.ediancha.com.processor.ShareUtil;
import client.ediancha.com.base.SingleNetWorkBaseFragment;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.entity.TeaSpaceDesc;
import client.ediancha.com.interfaces.ScrollViewListener;
import client.ediancha.com.interfaces.ShareInfoInterface;
import client.ediancha.com.myview.RatingBar;
import client.ediancha.com.myview.ScrollChangedScrollView;
import client.ediancha.com.myview.TitleRelativeLayout;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 16/10/17.
 */

public class TeaSpaceDescFragment extends TeaDescBaseFragment<TeaSpaceDesc> {

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

    public static TeaSpaceDescFragment getInstance(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        TeaSpaceDescFragment teaSpaceDescFragment = new TeaSpaceDescFragment();
        teaSpaceDescFragment.setArguments(bundle);
        return teaSpaceDescFragment;
    }


    @Override
    protected Map<String, String> getMap() {
        map.put("c", "chaguan");
        map.put("a", "show");
        map.put("pigcms_id", id);
        return map;
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
        scrollView.setScrollViewListener(scrollViewListener);
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
     * 填充评价
     *
     * @param comment
     */
    private void fillEvaluate(List<TeaSpaceDesc.Comment> comment) {
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
    private void fillRollPageView(List<String> images) {
        rollPagerView.setPlayDelay(images.size() > 1 ? 2500 : 0);
        rollPagerView.setAnimationDurtion(500);
        rollPagerView.setAdapter(new AdNormalAdapter(getContext(), rollPagerView, images));
        rollPagerView.setHintView(new TextHintView(getContext()));
    }

    @Override
    protected void writeData(TeaSpaceDesc t) {


        fillRollPageView(t.data.show.images);
        fillEvaluate(t.data.comment);
        fillRecommend(t.data.bx);
        fillRvInfo(t.data.show);
        Glide.with(getContext()).load(Constant.IMAGE).into(iv_package);

    }

    /**
     * 填充商户信息
     *
     * @param show
     */
    private void fillRvInfo(TeaSpaceDesc.Show show) {
        shareInfo.content = show.content.replaceAll("&nbsp;", "\n");
        shareInfo.logo = show.images.get(0);
        shareInfo.url = show.url;
        shareInfo.title = show.name;


        tv_name.setText(show.name);
//        ratingbar.INDEX = show.commentscore;
//        ratingbar.invalidate();
        tv_ratingbar.setText(show.commentscore + ".0");
        tv_ping_num.setText(show.commentcount + "条");


        if (TextUtils.equals("免费", show.price)) {
            tv_price.setText("免费");
        } else {
            tv_price.setText("￥" + show.price + "/人");
        }

        trl_address.setTitle(show.address);
        trl_tel.setTitle(show.phone1 + "-" + show.phone2);
        tv_info.setText("商户名称：" + show.storename + "\n\n营业时间：" + show.business_hours);

        tv_introduce_content.setText(show.shortdesc == null ? "暂未添加介绍,可联系客服询问" : show.shortdesc);
        tv_event_content.setText("活动介绍：" + show.content == null ? "暂未添加介绍,可联系客服询问" : show.content);
        trl_evaluate.setTitle("用户评价(" + show.commentcount + ")");


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
     *
     * @param bx
     */
    private void fillRecommend(List<TeaSpaceDesc.BaoXiang> bx) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayout.HORIZONTAL);
        rv_recommend.setLayoutManager(manager);
        rv_recommend.setAdapter(new TeaSpaceDescRecommendAdapter(getContext(), bx));
    }


}
