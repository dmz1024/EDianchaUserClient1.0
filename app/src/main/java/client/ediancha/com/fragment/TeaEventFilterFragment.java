package client.ediancha.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import java.util.List;
import java.util.Map;

import client.ediancha.com.adapter.TeaEventFilterAdapter;
import client.ediancha.com.adapter.TeaOrderAdapter;
import client.ediancha.com.base.ListNetWorkBaseFragment;
import client.ediancha.com.entity.TeaEventFilter;
import client.ediancha.com.entity.TeaOrder;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaEventFilterFragment extends ListNetWorkBaseFragment<TeaEventFilter.Data, TeaEventFilter> {

    @Override
    protected RecyclerView.Adapter getAdapter(List<TeaEventFilter.Data> totalList) {
        return new TeaEventFilterAdapter(getContext(), totalList);
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
    protected Class<TeaEventFilter> getTClass() {
        return TeaEventFilter.class;
    }

    @Override
    protected boolean getLoadMore() {
        return false;
    }

    @Override
    protected boolean getCanRefresh() {
        return false;
    }
}
