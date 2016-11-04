package client.ediancha.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.ediancha.com.adapter.TeaSpaceAdapter;
import client.ediancha.com.entity.TeaFilter;
import client.ediancha.com.interfaces.OnTeaEventMapListener;
import client.ediancha.com.processor.ShareUtil;
import client.ediancha.com.base.ListNetWorkBaseFragment;
import client.ediancha.com.entity.TeaSpace;
import client.ediancha.com.interfaces.ShareInfoInterface;
import client.ediancha.com.util.SharedPreferenUtil;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaSpaceFragment extends ListNetWorkBaseFragment<TeaSpace.Data, TeaSpace> {
    private Map<String, String> filterMap = new HashMap<>();
    private boolean isFirstInitData;

    @Override
    protected RecyclerView.Adapter getAdapter(List<TeaSpace.Data> totalList) {
        return new TeaSpaceAdapter(getContext(), totalList);
    }

    public static TeaSpaceFragment getInstance(boolean isFirstInitData) {
        TeaSpaceFragment teaSpaceFragment = new TeaSpaceFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isFirstInitData", isFirstInitData);
        teaSpaceFragment.setArguments(bundle);
        return teaSpaceFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        if(bundle!=null){
            isFirstInitData=bundle.getBoolean("isFirstInitData");
        }

    }

    @Override
    protected String getUrl() {
        return "app.php";
    }

    @Override
    protected Map<String, String> getMap() {
        map.put("c", "chaguan");
        map.put("a", "alist");
        map.putAll(filterMap);
        if (filterMap.size() == 0) {
            map.putAll(new SharedPreferenUtil(getContext(), "location").getData(new String[]{"lat", "long"}));
        }

        if (isP || filterMap.size() > 0) {
            map.put("search", "1");
        }

        return map;
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
    protected Class<TeaSpace> getTClass() {
        return TeaSpace.class;
    }

    @Override
    protected boolean isCanFirstInitData() {
        return isFirstInitData;
    }


    public void setCurrentPosition(int position) {
        if (totalList.size() > 0) {
            if (currentType == RequestType.LOAD_NEW || currentType == RequestType.LOAD_fILTER) {
                layoutManager.scrollToPosition(position);
            }
        }
    }


}
