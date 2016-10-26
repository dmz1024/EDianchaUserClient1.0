package client.ediancha.com.base;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.fragment.TeaDescBaseFragment;
import client.ediancha.com.fragment.TeaSpacePackageDescFragment;
import client.ediancha.com.processor.ShareUtil;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.fragment.TeaEventDescFragment;
import client.ediancha.com.fragment.TeaProductDescFragment;
import client.ediancha.com.fragment.TeaSpaceDescFragment;
import client.ediancha.com.interfaces.ScrollViewListener;
import client.ediancha.com.processor.CollectionUtil;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 2016/10/21.
 */

public class DescBaseActivity extends ShareBaseActivity implements ScrollViewListener {
    private int type;
    private TeaEventDescFragment teaEventDescFragment;
    private TeaSpaceDescFragment teaSpaceDescFragment;
    private TeaProductDescFragment teaProductDescFragment;
    private TeaSpacePackageDescFragment teaSpacePackageDescFragment;
    private CollectionUtil collectionUtil;

    @Override
    protected ShareUtil.ShareInfo getShareInfo() {
        ShareUtil.ShareInfo shareInfo = null;
        switch (type) {
            case 1:
                shareInfo = teaProductDescFragment.getShareInfo();
                break;
            case 2:
                shareInfo = teaSpaceDescFragment.getShareInfo();
                break;
            case 3:
                shareInfo = teaEventDescFragment.getShareInfo();
                break;
            case 4:
                shareInfo = teaSpacePackageDescFragment.getShareInfo();
                break;
        }
        return shareInfo;
    }

    @Override
    protected boolean isCanClick() {
        switch (type) {
            case 1:
                return teaProductDescFragment.getResult();
            case 2:
                return teaSpaceDescFragment.getResult();
            case 3:
                return teaEventDescFragment.getResult();
            case 4:
                return teaSpacePackageDescFragment.getResult();
        }
        return super.isCanClick();
    }

    @Override
    protected int getRid() {
        return R.layout.base_framelayout;
    }

    @Override
    protected int getTop() {
        return 0;
    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra("type", 1);
        switch (type) {
            case 1:
                teaProductDescFragment = TeaProductDescFragment.getInstance(getIntent().getStringExtra("id"));
                teaProductDescFragment.setScrollViewListener(this);
                break;
            case 2:
                teaSpaceDescFragment = TeaSpaceDescFragment.getInstance(getIntent().getStringExtra("id"));
                teaSpaceDescFragment.setScrollViewListener(this);
                break;
            case 3:
                teaEventDescFragment = TeaEventDescFragment.getInstance(getIntent().getStringExtra("id"));
                teaEventDescFragment.setScrollViewListener(this);
                break;
            case 4:
                teaSpacePackageDescFragment = TeaSpacePackageDescFragment.getInstance(getIntent().getStringExtra("id"));
                teaSpacePackageDescFragment.setScrollViewListener(this);
                break;
        }

    }


    @Override
    protected void handleItem(int position) {
        switch (position) {
            case 0:
                collectionUtil.collection(this, getIntent().getStringExtra("id"), type + "");
                break;
        }
    }

    @Override
    protected void initData() {
        collectionUtil = CollectionUtil.getInstance();
        collectionUtil.isCollection(getIntent().getStringExtra("id"), type + "");

        TeaDescBaseFragment baseFragment = null;
        switch (type) {
            case 1:
                baseFragment = teaProductDescFragment;
                break;
            case 2:
                baseFragment = teaSpaceDescFragment;
                break;
            case 3:
                baseFragment = teaEventDescFragment;
                break;
            case 4:
                baseFragment = teaSpacePackageDescFragment;
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fg_base, baseFragment).commit();
    }

    @Override
    protected List<String> getMore() {
        List<String> mores = new ArrayList<>();
        mores.add(collectionUtil.collectType());
        return mores;
    }

    private int currentColor = 0;

    @Override
    public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {
        String color;
        if (y > oldy) {
            currentColor += 1;
        } else if (y < oldy) {
            if (y < Util.getHeight() / 3) {
                currentColor -= 1;
            }
        }

        if (currentColor > 100) {
            currentColor = 100;
        } else if (currentColor < 0) {
            currentColor = 0;
        }


        if (currentColor < 10) {
            color = "#0" + currentColor + "333333";
        } else if (currentColor == 100) {
            color = "#333333";
        } else {
            color = "#" + currentColor + "333333";
        }

        if (currentColor >= 50) {
            setTitle(getIntent().getStringExtra("title"));
        } else {
            setTitle("");
        }

        if (y <= 10) {
            setTitle("");
            color = "#00333333";
        } else if (y >= Util.getHeight() / 4) {
            color = "#333333";
            setTitle(getIntent().getStringExtra("title"));
        }

        toolbar.setBackgroundColor(Color.parseColor(color));

    }


    @Override
    public void onCreateCustomToolBar(Toolbar toolbar) {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setBackgroundColor(Color.parseColor("#00333333"));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.COLLECTION_REQ) {
            collectionUtil.isCollection(getIntent().getStringExtra("id"), type + "");
        }
    }
}
