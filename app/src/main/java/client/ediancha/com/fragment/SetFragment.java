package client.ediancha.com.fragment;

import android.view.View;
import android.widget.Button;

import client.ediancha.com.R;
import client.ediancha.com.base.BaseFragment;
import client.ediancha.com.myview.TitleRelativeLayout;

/**
 * Created by dengmingzhi on 16/10/11.
 */

public class SetFragment extends BaseFragment {
    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        TitleRelativeLayout trl_head = (TitleRelativeLayout) view.findViewById(R.id.trl_head);
        TitleRelativeLayout trl_nick_name = (TitleRelativeLayout) view.findViewById(R.id.trl_nick_name);
        TitleRelativeLayout trl_tel = (TitleRelativeLayout) view.findViewById(R.id.trl_tel);
        TitleRelativeLayout trl_set_password = (TitleRelativeLayout) view.findViewById(R.id.trl_set_password);
        TitleRelativeLayout trl_feedback = (TitleRelativeLayout) view.findViewById(R.id.trl_feedback);
        TitleRelativeLayout trl_clear_cache = (TitleRelativeLayout) view.findViewById(R.id.trl_clear_cache);
        TitleRelativeLayout trl_about_us = (TitleRelativeLayout) view.findViewById(R.id.trl_about_us);
        TitleRelativeLayout trl_contact_us = (TitleRelativeLayout) view.findViewById(R.id.trl_contact_us);
        Button bt_exit = (Button) view.findViewById(R.id.bt_exit);
        bt_exit.setOnClickListener(this);
        trl_head.setOnClickListener(this);
        trl_nick_name.setOnClickListener(this);
        trl_set_password.setOnClickListener(this);
        trl_feedback.setOnClickListener(this);
        trl_clear_cache.setOnClickListener(this);
        trl_about_us.setOnClickListener(this);
        trl_contact_us.setOnClickListener(this);
    }

    @Override
    protected int getViewId() {
        return R.layout.fragment_set;
    }


    @Override
    protected boolean isCanRefresh() {
        return false;
    }
}
