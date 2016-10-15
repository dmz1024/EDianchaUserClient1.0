package client.ediancha.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import java.util.List;
import java.util.Map;

import client.ediancha.com.adapter.LikeEventAdapter;
import client.ediancha.com.adapter.TeaEventAdapter;
import client.ediancha.com.base.ListNetWorkBaseFragment;
import client.ediancha.com.entity.LikeEvent;
import client.ediancha.com.entity.TeaEvent;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaEventFragment extends ListNetWorkBaseFragment<TeaEvent.Data, TeaEvent> {

    @Override
    protected RecyclerView.Adapter getAdapter(List<TeaEvent.Data> totalList) {
        return new TeaEventAdapter(getContext(), totalList);
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
    protected Class<TeaEvent> getTClass() {
        return TeaEvent.class;
    }



}
