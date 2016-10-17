package client.ediancha.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.util.List;
import java.util.Map;

import client.ediancha.com.adapter.EvaluateAdapter;
import client.ediancha.com.adapter.TeaSpaceAdapter;
import client.ediancha.com.base.ListNetWorkBaseFragment;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.TeaEventDesc;
import client.ediancha.com.entity.TeaSpace;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class EvaluateFragment extends ListNetWorkBaseFragment<TeaEventDesc.Event, TeaEventDesc> {
    private boolean canRefresh = true;
    private boolean canLoad = true;
    private boolean haveImage = true;

    public static EvaluateFragment getInstance(boolean canRefresh, boolean canLoad) {
        EvaluateFragment evaluateFragment = new EvaluateFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("canRefresh", canRefresh);
        bundle.putBoolean("canLoad", canLoad);
        evaluateFragment.setArguments(bundle);
        return evaluateFragment;
    }

    public static EvaluateFragment getInstance(boolean haveImage) {
        EvaluateFragment evaluateFragment = new EvaluateFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("haveImage", haveImage);
        evaluateFragment.setArguments(bundle);
        return evaluateFragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            canRefresh = bundle.getBoolean("canRefresh",true);
            canLoad = bundle.getBoolean("canLoad",true);
            haveImage = bundle.getBoolean("haveImage",true);
        }
    }

    @Override
    protected RecyclerView.Adapter getAdapter(List<TeaEventDesc.Event> totalList) {
        return new EvaluateAdapter(getContext(), totalList,haveImage);
    }

    @Override
    protected String getUrl() {
        return "top250";
    }

    @Override
    protected Map<String, String> getMap() {
        return map;
    }

    @Override
    protected void initMap() {
        map.put("start", (page * 10) + "");
        map.put("count", 10 + "");
    }


    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {

        return new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return canRefresh && canLoad;
            }
        };
    }

    @Override
    protected RecyclerView.ItemDecoration getDividerItemDecoration() {
        return new ItemDecoration(getContext(), LinearLayout.VERTICAL, 2, "#ebebeb");
    }

    @Override
    protected Class<TeaEventDesc> getTClass() {
        return TeaEventDesc.class;
    }

    @Override
    protected boolean isCanFirstInitData() {
        return true;
    }


    @Override
    protected boolean getCanRefresh() {
        return canRefresh;
    }

    @Override
    public boolean isCanRefresh() {
        return canRefresh;
    }

    @Override
    protected boolean getLoadMore() {
        return canLoad;
    }
}
