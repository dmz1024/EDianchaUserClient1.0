package client.ediancha.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.ediancha.com.adapter.TeaProductAdapter;
import client.ediancha.com.entity.TeaFilter;
import client.ediancha.com.interfaces.OnTeaEventMapListener;
import client.ediancha.com.processor.ShareUtil;
import client.ediancha.com.base.ListNetWorkBaseFragment;
import client.ediancha.com.entity.TeaProduct;
import client.ediancha.com.interfaces.ShareInfoInterface;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaProductFragment extends ListNetWorkBaseFragment<TeaProduct.Data, TeaProduct> {

    private Map<String, String> filterMap = new HashMap<>();

    @Override
    protected RecyclerView.Adapter getAdapter(List<TeaProduct.Data> totalList) {
        return new TeaProductAdapter(getContext(), totalList);
    }


    private boolean isSearch;


    public static TeaProductFragment getInstance(boolean isSearch) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isSearch", isSearch);
        TeaProductFragment teaProductFragment = new TeaProductFragment();
        teaProductFragment.setArguments(bundle);
        return teaProductFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            isSearch = bundle.getBoolean("isSearch", false);
        }
    }

    @Override
    protected int getPage() {
        return isSearch? 1 : 0;
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

        map.putAll(filterMap);
        return map;
    }

    @Override
    protected boolean isCanFirstInitData() {
        return false;
    }

    public void setFilterMap(List<TeaFilter.Cat> catList) {
        filterMap.clear();
        map.clear();
        for (int i = 0; i < catList.size(); i++) {
            filterMap.put(catList.get(i).key, catList.get(i).value);
        }

        onRefresh();
    }

    @Override
    protected Class<TeaProduct> getTClass() {
        return TeaProduct.class;
    }

    public void setCurrentPosition(int position) {
        if (totalList.size() > 0) {
            if (currentType == RequestType.LOAD_NEW || currentType == RequestType.LOAD_fILTER) {
                layoutManager.scrollToPosition(position);
            }
        }
    }
}
