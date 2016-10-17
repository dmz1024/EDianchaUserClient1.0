package client.ediancha.com.fragment;

import android.support.v7.widget.RecyclerView;

import java.util.List;
import java.util.Map;

import client.ediancha.com.adapter.TeaEventFilterAdapter;
import client.ediancha.com.adapter.TeaPackageAdapter;
import client.ediancha.com.base.ListNetWorkBaseFragment;
import client.ediancha.com.entity.TeaEventFilter;
import client.ediancha.com.entity.TeaPackage;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaPackageFragment extends ListNetWorkBaseFragment<TeaPackage.Data, TeaPackage> {

    @Override
    protected RecyclerView.Adapter getAdapter(List<TeaPackage.Data> totalList) {
        return new TeaPackageAdapter(getContext(), totalList);
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
    protected Class<TeaPackage> getTClass() {
        return TeaPackage.class;
    }

}
