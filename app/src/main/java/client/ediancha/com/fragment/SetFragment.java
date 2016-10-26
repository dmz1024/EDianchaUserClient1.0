package client.ediancha.com.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import client.ediancha.com.R;
import client.ediancha.com.activity.LoginActivity;
import client.ediancha.com.activity.UserInfoActivity;
import client.ediancha.com.base.BaseFragment;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.myview.TitleRelativeLayout;
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

    @Override
    protected void initView(View view) {

        trl_user_info = (TitleRelativeLayout) view.findViewById(R.id.trl_user_info);
        TitleRelativeLayout trl_feedback = (TitleRelativeLayout) view.findViewById(R.id.trl_feedback);
        TitleRelativeLayout trl_clear_cache = (TitleRelativeLayout) view.findViewById(R.id.trl_clear_cache);
        TitleRelativeLayout trl_about_us = (TitleRelativeLayout) view.findViewById(R.id.trl_about_us);
        TitleRelativeLayout trl_contact_us = (TitleRelativeLayout) view.findViewById(R.id.trl_contact_us);
        trl_user_info.setOnClickListener(this);
        trl_feedback.setOnClickListener(this);
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
        }
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
