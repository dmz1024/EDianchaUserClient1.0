package client.ediancha.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.ediancha.com.activity.MainActivity;
import client.ediancha.com.adapter.TeaEventAdapter;
import client.ediancha.com.base.ListNetWorkBaseFragment;
import client.ediancha.com.entity.TeaEvent;
import client.ediancha.com.entity.TeaFilter;
import client.ediancha.com.interfaces.OnTeaEventMapListener;
import client.ediancha.com.util.SharedPreferenUtil;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaEventFragment extends ListNetWorkBaseFragment<TeaEvent.Data, TeaEvent> {
    private boolean isScrollEnabled = true;
    private Map<String, String> filterMap = new HashMap<>();
    @Override
    protected RecyclerView.Adapter getAdapter(List<TeaEvent.Data> totalList) {
        return new TeaEventAdapter(getContext(), totalList);
    }

    private boolean isSearch;


    public static TeaEventFragment getInstance(boolean isSearch) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isSearch", isSearch);
        TeaEventFragment teaEventFragment = new TeaEventFragment();
        teaEventFragment.setArguments(bundle);
        return teaEventFragment;
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
    protected String getUrl() {
        return "app.php";
    }

    @Override
    protected Map<String, String> getMap() {
        map.put("c", "chahui");
        map.put("a", "index");
        map.put("type", "1");
        map.putAll(filterMap);
//        if (filterMap.size() == 0 && !isSearch) {
//            map.putAll(new SharedPreferenUtil(getContext(), "location").getData(new String[]{"lat", "long"}));
//        }
        if (isP || filterMap.size() > 0) {
            map.put("search", "1");
        }

        return map;
    }

    @Override
    protected Class<TeaEvent> getTClass() {
        return TeaEvent.class;
    }


    @Override
    protected LinearLayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    public void setFilterMap(List<TeaFilter.Cat> catList) {
        filterMap.clear();
        map.clear();
        for (int i = 0; i < catList.size(); i++) {
            filterMap.put(catList.get(i).key, catList.get(i).value);
        }
        onRefresh();
    }


    public void setScrollEnable(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    protected boolean isCanFirstInitData() {
        return !isSearch;
    }

    public void setCurrentPosition(int position) {
        if (totalList.size() > 0) {
            if (currentType == RequestType.LOAD_NEW || currentType == RequestType.LOAD_fILTER) {
                layoutManager.scrollToPosition(position);
            }
        }
    }

}
