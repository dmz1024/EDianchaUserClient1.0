package client.ediancha.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.activity.LoginActivity;
import client.ediancha.com.activity.MyCenterActivity;
import client.ediancha.com.api.MyRetrofitUtil;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.entity.MyCenterIcon;
import client.ediancha.com.util.MyToast;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 16/10/11.
 */

public class MyCenterIconAdapter extends SingleBaseAdapter<MyCenterIcon> {


    public MyCenterIconAdapter(Context ctx, List<MyCenterIcon> list) {
        super(ctx, list);
    }

    public MyCenterIconAdapter(List<MyCenterIcon> list) {
        super(list);
    }

    @Override
    public MyCenterIconViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyCenterIconViewHolder(View.inflate(ctx, R.layout.item_center, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        MyCenterIconViewHolder mHolder = ((MyCenterIconViewHolder) holder);
        MyCenterIcon myCenterIcon = list.get(position);
        Glide.with(ctx).load(myCenterIcon.rid).into(mHolder.iv_icon);
        mHolder.tv_title.setText(myCenterIcon.title);
        if (myCenterIcon.count > 0) {
            mHolder.tv_count.setVisibility(View.VISIBLE);
            if (myCenterIcon.count == 1) {
                mHolder.tv_count.setText(" " + myCenterIcon.count + " ");
            } else {
                mHolder.tv_count.setText(myCenterIcon.count + "");
            }

        } else {
            mHolder.tv_count.setVisibility(View.GONE);
        }
    }


    public class MyCenterIconViewHolder extends BaseViewHolder {
        public ImageView iv_icon;
        public TextView tv_title;
        public TextView tv_count;

        public MyCenterIconViewHolder(View itemView) {
            super(itemView);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_count = (TextView) itemView.findViewById(R.id.tv_count);
        }

        @Override
        protected void onClick(int layoutPosition) {
            if (TextUtils.isEmpty(UserInfo.uid)) {
                MyToast.showToast("请先登录");
                login();
            } else {
                try {
                    Util.skip(((Activity) ctx), Class.forName(list.get(layoutPosition).className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void login(){

    }
}
