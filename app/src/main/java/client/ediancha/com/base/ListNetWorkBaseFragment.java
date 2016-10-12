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
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
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
        if(isLoading){
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
                page=currentPage;
                if (havaDataView != null) {
                    MyToast.showToast("网络似乎出了点问题");
                } else {
                    getCurrentView(ShowCurrentViewENUM.VIEW_NO_NETWORK);
                }
                setStopRefresh();

                if(rl_load!=null && rl_load.getVisibility()==View.VISIBLE){
                    rl_load.setVisibility(View.GONE);
                    isLoading = false;
                }
            }

            @Override
            public void serverErr() {
                page=currentPage;
                setStopRefresh();
                if (totalList.size() > 0) {
                    Toast.makeText(getContext(), "服务器异常!", Toast.LENGTH_LONG).show();
                } else {
                    getCurrentView(VIEW_SERVER_ERR);
                }

                if(rl_load!=null && rl_load.getVisibility()==View.VISIBLE){
                    rl_load.setVisibility(View.GONE);
                    isLoading = false;
                }

            }

            @Override
            public void haveData(T t) {
                if (t.start >= 0) {
                    if (currentType == RequestType.LOAD_NEW || currentType == RequestType.LOAD_fILTER) {
                        totalList.clear();
                    } else {
                        rl_load.setVisibility(View.GONE);
                        isLoading = false;
                    }

                    if (t.start == t.total) {
                        isHaveData = false;
                    }

                    totalList.addAll(t.subjects);

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
                    if(rl_load!=null && rl_load.getVisibility()==View.VISIBLE){
                        rl_load.setVisibility(View.GONE);
                        isLoading = false;
                    }
                    page=currentPage;
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
                            currentPage = page;
                            page += 1;
                            currentType = RequestType.LOAD_MORE;
                            layoutManager.scrollToPosition(totalList.size()-1);
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


}