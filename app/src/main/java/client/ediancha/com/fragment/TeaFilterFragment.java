package client.ediancha.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.ediancha.com.adapter.TeaEventFilterAdapter;
import client.ediancha.com.base.ListNetWorkBaseFragment;
import client.ediancha.com.entity.TeaFilter;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaFilterFragment extends ListNetWorkBaseFragment<TeaFilter.Data, TeaFilter> {
    private String c;
    private Map<String, TeaFilter.Cat> fillterMap = new HashMap<>();

    public static TeaFilterFragment getInstance(String c) {
        Bundle bundle = new Bundle();
        bundle.putString("c", c);
        TeaFilterFragment teaFilterFragment = new TeaFilterFragment();
        teaFilterFragment.setArguments(bundle);
        return teaFilterFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c = getArguments().getString("c");
    }

    @Override
    protected RecyclerView.Adapter getAdapter(List<TeaFilter.Data> totalList) {
        return new TeaEventFilterAdapter(getContext(), totalList) {
            @Override
            protected void select(int fatherPosition, int currentPosition, String k) {
                String name = TeaFilterFragment.this.totalList.get(fatherPosition).name;
                String key = TeaFilterFragment.this.totalList.get(fatherPosition).key;
                if (TextUtils.equals("时间", name)) {
                    if (TextUtils.equals("timefadkjhfhdafjhd", k)) {
                        if (fillterMap.containsKey(key)) {
                            fillterMap.remove(key);
                        }
                        key = "time";
                    } else {
                        if (fillterMap.containsKey("time")) {
                            fillterMap.remove("time");
                        }
                    }
                }
                if (currentPosition == -1) {
                    fillterMap.remove(key);
                } else {
                    fillterMap.put(key, TeaFilterFragment.this.totalList.get(fatherPosition).data.get(currentPosition));
                }


            }
        };
    }

    @Override
    protected String getUrl() {
        return "app.php";
    }

    @Override
    protected Map<String, String> getMap() {
        map.put("a", "search");
        map.put("c", c);
        return map;
    }

    @Override
    protected Class<TeaFilter> getTClass() {
        return TeaFilter.class;
    }

    @Override
    protected boolean getLoadMore() {
        return false;
    }

    @Override
    protected boolean getCanRefresh() {
        return false;
    }

    public Map<String, TeaFilter.Cat> getFillterMap() {
        return fillterMap;
    }

    public void clearFilter() {
        if(mAdapter!=null && fillterMap!=null){
            mAdapter.notifyDataSetChanged();
            fillterMap.clear();
        }

    }
}
