package client.ediancha.com.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.api.RetrofitUtil;
import client.ediancha.com.entity.BaseListEntity;
import client.ediancha.com.entity.Data;
import client.ediancha.com.util.MyToast;

import static client.ediancha.com.base.NetworkBaseFragment.ShowCurrentViewENUM.VIEW_HAVE_DATA;
import static client.ediancha.com.base.NetworkBaseFragment.ShowCurrentViewENUM.VIEW_NO_DATA;
import static client.ediancha.com.base.NetworkBaseFragment.ShowCurrentViewENUM.VIEW_SERVER_ERR;

/**
 * Created by dengmingzhi on 16/6/14.
 */
public abstract class ListNetWorkBaseFragment<D extends Data, T extends BaseListEntity<D>> extends NetworkBaseFragment<T> {

    protected int page = 1;
    protected int currentPage = page;
    public RecyclerView.Adapter mAdapter;
    protected RequestType currentType = RequestType.LOAD_NEW;
    protected boolean isLoading;
    protected boolean isHaveData = true;
    protected RecyclerView.LayoutManager layoutManager;


    public List<D> totalList = new ArrayList<>();
    protected Map<String, String> map = new HashMap<>();
    protected RelativeLayout rl_load;
    protected RecyclerView rv_base;

    @Override
    protected void initData() {
        getData();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isLoading) {
            return;
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onRefresh() {
        page = 1;
        currentPage = 1;
        currentType = RequestType.LOAD_NEW;
        isHaveData = true;
        super.onRefresh();
    }


    @Override
    protected void initView(View view) {
    }


    @Override
    public void getData() {
        initMap();

        RetrofitUtil retrofitUtil = RetrofitUtil.getInstance();
        RetrofitUtil.OnRequestListener<T> onRequestListener = new RetrofitUtil.OnRequestListener<T>() {
            @Override
            public void noNetwork() {
                page = currentPage;
                if (havaDataView != null) {
                    MyToast.showToast("网络似乎出了点问题");
                } else {
                    getCurrentView(ShowCurrentViewENUM.VIEW_NO_NETWORK);
                }
                setStopRefresh();

                if (rl_load != null && rl_load.getVisibility() == View.VISIBLE) {
                    rl_load.setVisibility(View.GONE);
                    isLoading = false;
                }
            }

            @Override
            public void serverErr() {
                page = currentPage;
                setStopRefresh();
                if (totalList.size() > 0) {
                    Toast.makeText(getContext(), "服务器异常!", Toast.LENGTH_LONG).show();
                } else {
                    getCurrentView(VIEW_SERVER_ERR);
                }

                if (rl_load != null && rl_load.getVisibility() == View.VISIBLE) {
                    rl_load.setVisibility(View.GONE);
                    isLoading = false;
                }

            }

            @Override
            public void haveData(T t) {
                if (t.page_info.page_index >= 0) {
                    if (currentType == RequestType.LOAD_NEW || currentType == RequestType.LOAD_fILTER) {
                        totalList.clear();
                    } else {
                        rl_load.setVisibility(View.GONE);
                        isLoading = false;
                    }

                    if (t.page_info.page_count == t.page_info.page_index) {
                        isHaveData = false;
                    }

                    totalList.addAll(t.data);

                    if (totalList.size() > 0) {
                        getCurrentView(VIEW_HAVE_DATA);
                        mAdapter.notifyDataSetChanged();
                        if (currentType == RequestType.LOAD_NEW || currentType == RequestType.LOAD_fILTER) {
                            layoutManager.scrollToPosition(0);
                        }
                    } else if (totalList.size() == 0) {
                        getCurrentView(VIEW_NO_DATA);
                    }

                } else {
                    if (rl_load != null && rl_load.getVisibility() == View.VISIBLE) {
                        rl_load.setVisibility(View.GONE);
                        isLoading = false;
                    }
                    page = currentPage;
                    resultNot0(t);

                }

                setStopRefresh();

            }

            @Override
            public void onCompleted() {
                setStopRefresh();
            }
        };


        if (getMethod() == RequestMenthod.POST) {
            retrofitUtil.get(getUrl(), getMap(), getTClass(), onRequestListener);
        } else {
            retrofitUtil.post(getUrl(), getMap(), getTClass(), onRequestListener);
        }
    }


    protected void initMap() {
        map.put("size", getSize());
        map.put("page", page + "");
    }

    protected void resultNot0(T t) {
        MyToast.showToast(t.msg);
    }


    @Override
    protected View getHaveDataView() {

        View view = inflateView(R.layout.view_show_haveview);

        rv_base = (RecyclerView) view.findViewById(R.id.rv_base);
        rl_load = (RelativeLayout) view.findViewById(R.id.rl_load);

        rv_base.setAdapter(mAdapter = getAdapter(totalList));
        rv_base.setLayoutManager(layoutManager = getLayoutManager());
        RecyclerView.ItemDecoration itemDecoration;
        if ((itemDecoration = getDividerItemDecoration()) != null) {
            rv_base.addItemDecoration(itemDecoration);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        rv_base.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //用来标记是否正在向最后一个滑动
            boolean isSlidingToLast = false;

            private static final int HIDE_THRESHOLD = 20;
            private int scrolledDistance = 0;
            private boolean controlsVisible = true;


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isSlidingToLast = dy > 0 ? true : false;
                if (d != null) {
                    if (isSlidingToLast)
                        d.isUp();
                    else
                        d.isDown();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (getCanRefresh()) {
                    isCanRefresh = layoutManager.findFirstCompletelyVisibleItemPosition() == 0;
                    swipeRefreshLayout.setEnabled(isCanRefresh);

                }

                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (swipeRefreshLayout.isRefreshing()) {
                        return;
                    }



                    //获取最后一个完全显示的ItemPosition
                    int lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = layoutManager.getItemCount();
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast && isHaveData && getLoadMore()) {
                        if (!isLoading) {
                            isLoading = true;
                            swipeRefreshLayout.setEnabled(false);
                            rl_load.setVisibility(View.VISIBLE);
                            currentPage = page;
                            page += 1;
                            currentType = RequestType.LOAD_MORE;
                            layoutManager.scrollToPosition(totalList.size()-getJian());
                            swipeRefreshLayout.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    initData();
                                }
                            }, 150);

                        }
                    }
                }

            }
        });
        return view;
    }

    protected int getJian() {
        return 1;
    }

    protected String getSize() {
        return "10";
    }


    // 设置是否可以加载更多
    protected boolean getLoadMore() {
        return true;
    }

    /**
     * 返回适配器对象
     *
     * @param totalList
     * @return
     */
    protected abstract RecyclerView.Adapter getAdapter(List<D> totalList);


    /**
     * 返回分割线
     *
     * @return
     */
    protected RecyclerView.ItemDecoration getDividerItemDecoration() {
        return null;
    }

    /**
     * 返回视图管理器
     *
     * @return
     */
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }


    public enum RequestType {
        LOAD_MORE, LOAD_NEW, LOAD_fILTER
    }

    protected RecycleScrollViewListener d;

    public void setA(RecycleScrollViewListener d) {
        this.d = d;
    }

}
