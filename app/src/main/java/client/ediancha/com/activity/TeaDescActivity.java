package client.ediancha.com.activity;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.ShareBaseActivity;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.fragment.TeaPackageDescFragment;
import client.ediancha.com.fragment.TeaProductDescFragment;
import client.ediancha.com.processor.CollectionUtil;
import client.ediancha.com.processor.ShareUtil;

public class TeaDescActivity extends ShareBaseActivity {
    private TeaProductDescFragment teaProductDescFragment;
    private TeaPackageDescFragment teaPackageDescFragment;
    private CollectionUtil collectionUtil;
    private int type;

    @Override
    protected ShareUtil.ShareInfo getShareInfo() {
        if(type==1){
            return teaProductDescFragment.getShareInfo();
        }
        return teaPackageDescFragment.getShareInfo();
    }

    @Override
    protected boolean isCanClick() {
        if(type==1){
            return teaProductDescFragment.getResult();
        }
        return teaPackageDescFragment.getResult();
    }

    @Override
    protected int getRid() {
        return R.layout.base_framelayout;
    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra("type", 1);
        if(type==1){
            teaProductDescFragment = TeaProductDescFragment.getInstance(getIntent().getStringExtra("id"));
        }else {
            teaPackageDescFragment = TeaPackageDescFragment.getInstance(getIntent().getStringExtra("id"));
        }


    }

    @Override
    protected String getToolBarTitle() {
        return null;
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
        if(type==1){
            getSupportFragmentManager().beginTransaction().replace(R.id.fg_base, teaProductDescFragment).commit();
        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fg_base, teaPackageDescFragment).commit();
        }

    }

    @Override
    protected List<String> getMore() {
        List<String> mores = new ArrayList<>();
        mores.add(collectionUtil.collectType());
        return mores;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.COLLECTION_REQ) {
            collectionUtil.isCollection(getIntent().getStringExtra("id"), type + "");
        }
    }


    @Override
    protected void back() {
        if(type==1){
            if (teaProductDescFragment.getShow()) {
                teaProductDescFragment.hideDesc();
            } else {
                super.back();
            }
        }else {
            if (teaPackageDescFragment.getShow()) {
                teaPackageDescFragment.hideDesc();
            } else {
                super.back();
            }
        }

    }
}
