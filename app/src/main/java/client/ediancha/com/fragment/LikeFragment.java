package client.ediancha.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import java.util.List;
import java.util.Map;

import client.ediancha.com.adapter.LikeAdapter;
import client.ediancha.com.base.ListNetWorkBaseFragment;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.entity.Like;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class LikeFragment extends ListNetWorkBaseFragment<Like.Data, Like> {
    private String type;

    public static LikeFragment getInstance(String type) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        LikeFragment likeFragment = new LikeFragment();
        likeFragment.setArguments(bundle);
        return likeFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isHavePage = false;
        type = getArguments().getString("type");
    }

    @Override
    protected RecyclerView.Adapter getAdapter(List<Like.Data> totalList) {
        return new LikeAdapter(getContext(), totalList, type);
    }

    @Override
    protected String getUrl() {
        return "app.php";
    }

    @Override
    protected Map<String, String> getMap() {
        map.put("c", "home");
        map.put("a", "collect");
        map.put("type", type);
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);
        return map;
    }

    @Override
    protected Class<Like> getTClass() {
        return Like.class;
    }

    @Override
    protected boolean isCanFirstInitData() {
        return TextUtils.equals("3", type);
    }


}
