package client.ediancha.com.activity;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.ShareBaseActivity;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.fragment.TeaProductDescFragment;
import client.ediancha.com.processor.CollectionUtil;
import client.ediancha.com.processor.ShareUtil;

public class TeaDescActivity extends ShareBaseActivity {
    private TeaProductDescFragment teaProductDescFragment;
    private CollectionUtil collectionUtil;

    @Override
    protected ShareUtil.ShareInfo getShareInfo() {
        return teaProductDescFragment.getShareInfo();
    }

    @Override
    protected boolean isCanClick() {
        return teaProductDescFragment.getResult();
    }

    @Override
    protected int getRid() {
        return R.layout.base_framelayout;
    }

    @Override
    protected void initView() {
        teaProductDescFragment = TeaProductDescFragment.getInstance("54");

    }

    @Override
    protected String getToolBarTitle() {
        return getIntent().getStringExtra("title");
    }

    @Override
    protected void handleItem(int position) {
        switch (position) {
            case 0:
                collectionUtil.collection(this, getIntent().getStringExtra("id"), "1");
                break;
        }
    }

    @Override
    protected void initData() {
        collectionUtil = CollectionUtil.getInstance();
        collectionUtil.isCollection(getIntent().getStringExtra("id"), "1");
        getSupportFragmentManager().beginTransaction().replace(R.id.fg_base, teaProductDescFragment).commit();
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
            collectionUtil.isCollection(getIntent().getStringExtra("id"), "1");
        }
    }


    @Override
    protected void back() {
        if (teaProductDescFragment.getShow()) {
            teaProductDescFragment.hideDesc();
        } else {
            super.back();
        }
    }
}
