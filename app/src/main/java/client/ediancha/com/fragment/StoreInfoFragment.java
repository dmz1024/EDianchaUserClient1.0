package client.ediancha.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.util.List;
import java.util.Map;

import client.ediancha.com.adapter.EvaluateAdapter;
import client.ediancha.com.adapter.StoreInfoAdapter;
import client.ediancha.com.base.ListNetWorkBaseFragment;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.Comment;
import client.ediancha.com.entity.StoreInfo;
import client.ediancha.com.entity.TeaSpaceDesc;
import client.ediancha.com.processor.ShareUtil;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class StoreInfoFragment extends ListNetWorkBaseFragment<StoreInfo.Data, StoreInfo> {
    private String storeid;

    public static StoreInfoFragment getInstance(String storeid) {
        StoreInfoFragment storeInfoFragment = new StoreInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("storeid", storeid);
        storeInfoFragment.setArguments(bundle);
        return storeInfoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        storeid = bundle.getString("storeid");
    }

    @Override
    protected RecyclerView.Adapter getAdapter(List<StoreInfo.Data> totalList) {
        return new StoreInfoAdapter(getContext(), totalList);
    }

    @Override
    protected String getUrl() {
        return "app.php";
    }

    @Override
    protected Map<String, String> getMap() {
        map.put("storeid", storeid);
        map.put("type", "1");
        map.put("c", "chaguan");
        map.put("a", "store_show");
        return map;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 2 : 1;
            }
        });

        return gridLayoutManager;
    }

    @Override
    protected Class<StoreInfo> getTClass() {
        return StoreInfo.class;
    }


    public boolean getIsOk() {
        return havaDataView != null;
    }


    public ShareUtil.ShareInfo getShareInfo() {
        ShareUtil.ShareInfo shareInfo = new ShareUtil.ShareInfo();
        StoreInfo.Data2 data = totalList.get(1).data2;
        shareInfo.content = data.info;
        shareInfo.url = data.url;
        shareInfo.title = data.name;
        shareInfo.logo = data.logo;
        return shareInfo;
    }

}
