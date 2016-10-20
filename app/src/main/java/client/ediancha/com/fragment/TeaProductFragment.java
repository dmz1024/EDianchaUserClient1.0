package client.ediancha.com.fragment;

import android.support.v7.widget.RecyclerView;

import java.util.List;
import java.util.Map;

import client.ediancha.com.adapter.TeaProductAdapter;
import client.ediancha.com.base.ListNetWorkBaseFragment;
import client.ediancha.com.entity.TeaProduct;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaProductFragment extends ListNetWorkBaseFragment<TeaProduct.Data, TeaProduct> {

    @Override
    protected RecyclerView.Adapter getAdapter(List<TeaProduct.Data> totalList) {
        return new TeaProductAdapter(getContext(), totalList);
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
    protected Class<TeaProduct> getTClass() {
        return TeaProduct.class;
    }

    @Override
    protected boolean isCanFirstInitData() {
        return false;
    }
}
