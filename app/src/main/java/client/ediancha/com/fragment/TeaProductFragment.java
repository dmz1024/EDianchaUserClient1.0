package client.ediancha.com.fragment;

import android.support.v7.widget.RecyclerView;

import java.util.List;
import java.util.Map;

import client.ediancha.com.adapter.TeaProductAdapter;
import client.ediancha.com.processor.ShareUtil;
import client.ediancha.com.base.ListNetWorkBaseFragment;
import client.ediancha.com.entity.TeaProduct;
import client.ediancha.com.interfaces.ShareInfoInterface;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaProductFragment extends ListNetWorkBaseFragment<TeaProduct.Data, TeaProduct> {


    @Override
    protected RecyclerView.Adapter getAdapter(List<TeaProduct.Data> totalList) {
        return new TeaProductAdapter(getContext(), totalList);
    }

    @Override
    public void onRefresh() {
        page = 0;
        currentPage = 0;
        currentType = RequestType.LOAD_NEW;
        isHaveData = true;
        if (isRefresh) {
            return;
        } else {
            isRefresh = true;
        }
        isFirst = false;
        initData();

    }


    @Override
    protected String getUrl() {
        return "app.php";
    }

    @Override
    protected Map<String, String> getMap() {
        map.put("c", "chanpin");
        if (page > 0) {
            map.put("a", "goods_list");
        } else {
            map.put("a", "index");
        }
        return map;
    }

    @Override
    protected Class<TeaProduct> getTClass() {
        page = 0;
        return TeaProduct.class;
    }

    @Override
    protected boolean isCanFirstInitData() {
        return false;
    }
}
