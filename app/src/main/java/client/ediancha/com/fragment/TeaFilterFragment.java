package client.ediancha.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
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
    protected RecyclerView.Adapter getAdapter(final List<TeaFilter.Data> totalList) {
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


                if (TextUtils.equals("chanpin", c) && fatherPosition == 0 && currentPosition != -1) {
                    if (totalList.size() == 4) {
                        totalList.remove(2);
                        totalList.remove(2);
                    } else if (totalList.size() == 3) {
                        totalList.remove(2);
                    }

                    TeaFilter.Data data1 = new TeaFilter.Data();
                    TeaFilter.Data data2 = new TeaFilter.Data();

                    data1.data = new ArrayList<>();
                    data2.data = new ArrayList<>();
                    data1.name = "品牌";
                    data2.name = "用途";
                    TeaFilter.Cat cat = totalList.get(fatherPosition).data.get(currentPosition);
                    data1.key = cat.key1;
                    data2.key = cat.key2;
                    List<TeaFilter.Cat1> brand = cat.brand;
                    List<TeaFilter.Cat1> yt = cat.yt;
                    fillterMap.remove(data1.key);
                    fillterMap.remove(data2.key);
                    if (brand != null && brand.size() > 0) {
                        for (int i = 0; i < brand.size(); i++) {
                            TeaFilter.Cat cat1 = new TeaFilter.Cat();
                            cat1.name = brand.get(i).name;
                            cat1.value = brand.get(i).value;
                            data1.data.add(cat1);
                        }
                        totalList.add(data1);
                        notifyItemChanged(2);
                    }

                    if (yt != null && yt.size() > 0) {
                        for (int i = 0; i < yt.size(); i++) {
                            TeaFilter.Cat cat2 = new TeaFilter.Cat();
                            cat2.name = yt.get(i).name;
                            cat2.value = yt.get(i).value;
                            data2.data.add(cat2);
                        }
                        totalList.add(data2);
                        notifyItemChanged(3);
                    }


                } else if (TextUtils.equals("chanpin", c) && fatherPosition == 0 && currentPosition == -1) {
                    if (totalList.size() == 4) {
                        fillterMap.remove("prop1");
                        fillterMap.remove("prop2");
                        totalList.remove(2);
                        totalList.remove(2);
                    } else if (totalList.size() == 3) {
                        fillterMap.remove(totalList.get(2).key);
                        totalList.remove(2);
                    }


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
        if (mAdapter != null && fillterMap != null) {
            mAdapter.notifyDataSetChanged();
            fillterMap.clear();
        }

    }
}
