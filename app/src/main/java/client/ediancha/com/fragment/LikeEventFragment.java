package client.ediancha.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import java.util.List;
import java.util.Map;

import client.ediancha.com.adapter.LikeEventAdapter;
import client.ediancha.com.base.ListNetWorkBaseFragment;
import client.ediancha.com.entity.LikeEvent;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class LikeEventFragment extends ListNetWorkBaseFragment<LikeEvent.Data, LikeEvent> {




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected RecyclerView.Adapter getAdapter(List<LikeEvent.Data> totalList) {
        return new LikeEventAdapter(getContext(), totalList);
    }

    @Override
    protected String getUrl() {
        return "top250";
    }

    @Override
    protected Map<String, String> getMap() {
//        map.put("start", "1");
//        map.put("date", "2016-10-11");
        return map;
    }

    @Override
    protected void initMap() {
        map.put("start", (page * 10) + "");
        map.put("count", 10 + "");
//        map.put("date", "2016-10-11");
    }

    @Override
    protected Class<LikeEvent> getTClass() {
        return LikeEvent.class;
    }


}
