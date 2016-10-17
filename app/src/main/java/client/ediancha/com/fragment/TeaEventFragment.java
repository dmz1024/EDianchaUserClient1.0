package client.ediancha.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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
    private boolean isScrollEnabled = true;

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


    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                Log.d("滑动", isScrollEnabled + "");
//                if (d != null) {
//                    if (isScrollEnabled) {
//                        d.isUp();
//                    } else {
//                        d.isDown();
//                    }
//                }
                return isScrollEnabled && super.canScrollVertically();
            }
        };
    }


    public void setScrollEnable(boolean flag) {
        this.isScrollEnabled = flag;
    }
}
