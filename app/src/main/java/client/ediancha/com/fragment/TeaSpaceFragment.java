package client.ediancha.com.fragment;

import android.support.v7.widget.RecyclerView;

import java.util.List;
import java.util.Map;

import client.ediancha.com.adapter.TeaEventAdapter;
import client.ediancha.com.adapter.TeaSpaceAdapter;
import client.ediancha.com.base.ListNetWorkBaseFragment;
import client.ediancha.com.entity.TeaEvent;
import client.ediancha.com.entity.TeaSpace;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaSpaceFragment extends ListNetWorkBaseFragment<TeaSpace.Data, TeaSpace> {

    @Override
    protected RecyclerView.Adapter getAdapter(List<TeaSpace.Data> totalList) {
        return new TeaSpaceAdapter(getContext(), totalList);
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
    protected Class<TeaSpace> getTClass() {
        return TeaSpace.class;
    }

    @Override
    protected boolean isCanFirstInitData() {
        return false;
    }
}
