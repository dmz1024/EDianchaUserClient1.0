package client.ediancha.com.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.ediancha.edcbusiness.R;
import com.ediancha.edcbusiness.api.APiHttp;
import com.ediancha.edcbusiness.api.ApiRequest;
import com.ediancha.edcbusiness.entity.BaseLvEntity;
import com.ediancha.edcbusiness.entity.Data;
import com.ediancha.edcbusiness.view.MyToast;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dengmingzhi on 16/6/14.
 */
public abstract class ListDataBaseFragment<T extends BaseLvEntity, D extends Data, A extends RecyclerView.Adapter> extends BaseTestFragment {
    protected RecyclerView rv_base;
    protected ScrollView sv_root;
    protected RelativeLayout rl_load;
    private Button bt_agin;
    private TextView tv_info;
    public List<D> totalList;
    private int page = 1;
    public A mAdapter;
    protected int currentType = 2;
    protected Gson mGson;
    protected Map<String, String> map = new HashMap<>();
    protected String size = "10";
    protected boolean isLoading;
    protected boolean isHaveData = true;
    protected LinearLayoutManager layoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGson = new Gson();
        totalList = new ArrayList<>();
    }

    @Override
    protected void initData() {
        tv_info.setText("加载中...");
        initData(currentType);
    }

    @Override
    public void onRefresh() {
        page = 1;
        currentType = 2;
        isHaveData = true;
        super.onRefresh();
    }


    @Override
    protected void initView(View view) {
        rl_load = (RelativeLayout) view.findViewById(R.id.rl_load);
        rv_base = (RecyclerView) view.findViewById(R.id.rv_base);
        sv_root = (ScrollView) view.findViewById(R.id.sv_root);
        tv_info = (TextView) view.findViewById(R.id.tv_info);
        bt_agin = (Button) view.findViewById(R.id.bt_agin);
        bt_agin.setOnClickListener(this);
        rv_base.setAdapter(mAdapter = getAdapter(getContext(), totalList));
        rv_base.setLayoutManager(layoutManager = getLayoutManager());
        RecyclerView.ItemDecoration itemDecoration;
        if ((itemDecoration = getDividerItemDecoration()) != null) {
            rv_base.addItemDecoration(itemDecoration);
        }

        rv_base.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //用来标记是否正在向最后一个滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isSlidingToLast = dy > 0 ? true : false;

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (swipeRefreshLayout.isRefreshing()) {
                        return;
                    }

                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    //获取最后一个完全显示的ItemPosition
                    int lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = layoutManager.getItemCount();
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast && isHaveData) {
                        if (!isLoading) {
                            isLoading = true;
                            swipeRefreshLayout.setEnabled(false);
                            rl_load.setVisibility(View.VISIBLE);
                            page += 1;
                            currentType = 1;
                            swipeRefreshLayout.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    initData();
                                }
                            }, 300);

                        }
                    }
                }

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_agin:
                tv_info.setVisibility(View.VISIBLE);
                bt_agin.setVisibility(View.GONE);
                initData();
                break;
        }
    }


    @Override
    protected int getViewId() {
        return R.layout.fg_base_lv;
    }


    // 设置是否可以下拉刷新
    protected boolean getRefresh() {
        return false;
    }

    // 设置是否可以上拉加载
    protected boolean getLoad() {
        return true;
    }


    /**
     * 返回适配器对象
     *
     * @param context
     * @param totalList
     * @return
     */
    protected abstract A getAdapter(Context context, List<D> totalList);


    public void initData(final int type) {
        Log.d("page1", page + "");
        map.put("page", page + "");
        map.put("size", size);

        if (getUrl().equals("")) {
            if (type == 2) {
                totalList.clear();
            }else if(type==3){
                showView(4);
                totalList.clear();
            }

            for (int i = 0; i < Integer.parseInt(size); i++) {
                totalList.add((D) new Data());
            }

            mAdapter.notifyDataSetChanged();
            if (totalList.size() > 0) {
                showView(1);
            } else if (totalList.size() == 0) {
                showView(3);
            }
            if (type == 1) {
                isLoading = false;
                swipeRefreshLayout.setEnabled(true);
                rl_load.setVisibility(View.GONE);
            } else {
                setStopRefresh();
            }
        } else {
            ApiRequest<T> api = new ApiRequest<T>(getContext(), getUrl(), getTClass()) {
                @Override
                protected void success(T t) {
                    if (t.result == 0) {
                        if (type == 2 ||type==3) {
                            totalList.clear();
                        }

                        if (t.page_count == t.page_index) {
                            isHaveData = false;
                        }
                        totalList.addAll(t.data);
                        if (totalList.size() > 0) {
                            showView(1);
                        } else if (totalList.size() == 0) {
                            showView(3);
                        }
                        mAdapter.notifyDataSetChanged();
                    } else {
                        MyToast.showToast(t.msg);
                        showView(2);
                    }

                }

                @Override
                protected void onFinish() {
                    if (type == 1) {
                        isLoading = false;
                        swipeRefreshLayout.setEnabled(true);
                        rl_load.setVisibility(View.GONE);
                    } else {
                        layoutManager.scrollToPosition(0);
                        setStopRefresh();

                    }
                }

                @Override
                protected void noNetWork() {
                    onFinish();
                    if (totalList.size() > 0) {
                        MyToast.showToast("网络连接失败!");
                    } else {
                        showView(2);
                    }

                }


                @Override
                protected void onErr() {
                    onFinish();
                    if (totalList.size() > 0) {
                        MyToast.showToast("服务器异常!");
                    } else {
                        showView(2);
                    }

                }

                @Override
                protected Map<String, String> map(Map<String, String> map1) {
                    return getMap(map);
                }
            };


            if (getMethod()) {
                api.post();
            } else {
                api.get();
            }

        }


    }

    /**
     * rl_load中view的显示状态
     *
     * @param type
     */
    protected void showView(int type) {
        rv_base.setVisibility(View.GONE);
        bt_agin.setVisibility(View.GONE);
        tv_info.setVisibility(View.GONE);
        sv_root.setVisibility(View.VISIBLE);
        switch (type) {
            case 1:
                sv_root.setVisibility(View.GONE);
                rv_base.setVisibility(View.VISIBLE);
                break;
            case 2:
                bt_agin.setVisibility(View.VISIBLE);
                tv_info.setText("加载中...");
                break;
            case 3:
                tv_info.setText("什么都木有");
                tv_info.setVisibility(View.VISIBLE);
                break;
            case 4:
                tv_info.setText("加载中...");
                tv_info.setVisibility(View.VISIBLE);
                break;
        }
    }


    protected RecyclerView.ItemDecoration getDividerItemDecoration() {
        return null;
    }


    protected LinearLayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    protected boolean getMethod() {
        return true;
    }

    protected abstract Class<T> getTClass();


    /**
     * 返回http请求地址
     *
     * @return
     */
    protected abstract String getUrl();

    protected abstract Map<String, String> getMap(Map<String, String> map);


}
