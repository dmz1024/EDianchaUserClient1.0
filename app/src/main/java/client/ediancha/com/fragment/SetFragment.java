package client.ediancha.com.fragment;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.activity.AboutUsActivity;
import client.ediancha.com.activity.LoginActivity;
import client.ediancha.com.activity.UserInfoActivity;
import client.ediancha.com.base.BaseFragment;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.myview.ChooseStringView;
import client.ediancha.com.myview.TitleRelativeLayout;
import client.ediancha.com.util.ImageCatchUtil;
import client.ediancha.com.util.MyToast;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 16/10/11.
 */

public class SetFragment extends BaseFragment {
    private boolean isLogin = true;
    TitleRelativeLayout trl_user_info;

    @Override
    protected void initData() {

    }

    private TitleRelativeLayout trl_clear_cache;

    @Override
    protected void initView(View view) {

        trl_user_info = (TitleRelativeLayout) view.findViewById(R.id.trl_user_info);
        TitleRelativeLayout trl_feedback = (TitleRelativeLayout) view.findViewById(R.id.trl_feedback);
        trl_clear_cache = (TitleRelativeLayout) view.findViewById(R.id.trl_clear_cache);
        TitleRelativeLayout trl_about_us = (TitleRelativeLayout) view.findViewById(R.id.trl_about_us);
        TitleRelativeLayout trl_contact_us = (TitleRelativeLayout) view.findViewById(R.id.trl_contact_us);
        trl_user_info.setOnClickListener(this);
        trl_feedback.setOnClickListener(this);
        trl_clear_cache.setOnClickListener(this);
        trl_about_us.setOnClickListener(this);
        trl_contact_us.setOnClickListener(this);
        trl_clear_cache.setContent(ImageCatchUtil.getInstance().getCacheSize(getContext()));
        trl_clear_cache.setOnClickListener(this);
        trl_about_us.setOnClickListener(this);
        trl_contact_us.setOnClickListener(this);
        if (TextUtils.isEmpty(UserInfo.uid)) {
            trl_user_info.setContent("未登录");
            isLogin = false;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.trl_user_info:
                userInfo();
                break;
            case R.id.trl_clear_cache:
                ImageCatchUtil.getInstance().clearImageAllCache();
                trl_clear_cache.setContent(ImageCatchUtil.getInstance().getCacheSize(getContext()) );
                break;
            case R.id.trl_about_us:
                Util.skip(getActivity(), AboutUsActivity.class);
                break;
            case R.id.trl_contact_us:
                cintact();
                break;
        }
    }

    /**
     * 联系我们
     */
    private void cintact() {
        List<String> list = new ArrayList<>();
        list.add("QQ咨询");
        list.add("电话咨询");
        new ChooseStringView(getContext(), list) {
            @Override
            protected void itemClick(int position) {
                switch (position) {
                    case 0:
                        String url = "mqqwpa://im/chat?chat_type=wpa&uin=3172519274";
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        break;
                    case 1:
                        Uri uri = Uri.parse("tel:01056107150");
                        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                        startActivity(intent);
                        break;
                }
            }
        }.showAtLocation();
    }

    /**
     * 展示用户信息
     */
    private void userInfo() {
        if (!isLogin) {
            MyToast.showToast("请先登录!");
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivityForResult(intent, Constant.USERINFO_REQ);
        } else {
            Util.skip(getActivity(), UserInfoActivity.class);
        }
    }

    @Override
    protected int getViewId() {
        return R.layout.fragment_set;
    }


    @Override
    protected boolean isCanRefresh() {
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.USERINFO_REQ && data != null) {
            trl_user_info.setContent("");
            isLogin = true;
        }
    }
}
