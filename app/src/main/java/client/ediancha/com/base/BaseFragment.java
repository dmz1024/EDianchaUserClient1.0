package client.ediancha.com.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import client.ediancha.com.util.Util;


/**
 * Created by dengmingzhi on 16/6/16.
 */
public abstract class BaseFragment extends Fragment implements PopupWindow.OnDismissListener, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    protected boolean isRefresh;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected boolean isFirst = true;
    protected boolean isVisible;
    protected FrameLayout frameLayout;
    /**
     * 标志位，标志视图已经初始化完成
     */
    boolean isPrepared;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    /**
     * 可见
     */
    protected void onVisible() {

        if (isOnlyInitOne()) {
            if (!isFirst) {
                return;
            }
        }

        if (!isVisible) {
            return;
        }


        if (!isPrepared) {
            return;
        }

        if (isRefresh) {
            return;
        }



        if (isCanRefresh()) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                }
            });

            swipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onRefresh();
                }
            }, 50);

        } else {
            swipeRefreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onRefresh();
                }
            }, 50);
        }

    }


    /**
     * 不可见
     */
    protected void onInvisible() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        swipeRefreshLayout = new SwipeRefreshLayout(getContext());
        SwipeRefreshLayout.LayoutParams params = new SwipeRefreshLayout.LayoutParams(SwipeRefreshLayout.LayoutParams.MATCH_PARENT
                , SwipeRefreshLayout.LayoutParams.MATCH_PARENT);
        swipeRefreshLayout.setLayoutParams(params);

        frameLayout = new FrameLayout(getContext());
        swipeRefreshLayout.addView(frameLayout);

        View view = null;
        if (getViewId() != 0) {
            view = inflater.inflate(getViewId(), null);
        } else {
            view = getShowView();
        }

        initView(view);
        frameLayout.addView(view);
        isPrepared = true;
        swipeRefreshLayout.setOnRefreshListener(this);
        if (isCanRefresh()) {
            if (isCanFirstInitData()) {
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(true);
                    }
                });

                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onRefresh();
                    }
                }, 50);

            }
        } else {
            swipeRefreshLayout.setEnabled(false);
            if (isCanFirstInitData()) {
                onRefresh();
            }


        }
        return swipeRefreshLayout;
    }

    protected boolean isOnlyInitOne() {
        return true;
    }


    @Override
    public void onRefresh() {

        if (isRefresh) {
            return;
        } else {
            isRefresh = true;
        }
        isFirst = false;
        initData();
    }


    protected void setStopRefresh() {
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                isRefresh = false;
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 200);
    }


    /**
     * 是否在加载视图的同时加载数据
     *
     * @return
     */
    protected boolean isCanFirstInitData() {
        return true;
    }

    /**
     * 是否设置下拉刷新
     *
     * @return
     */
    protected boolean isCanRefresh() {
        return true;
    }

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化视图
     *
     * @param view
     */
    protected abstract void initView(View view);

    /**
     * 获取视图资源id
     *
     * @return
     */
    protected abstract int getViewId();

    /**
     * 获取视图
     *
     * @return
     */
    protected View getShowView() {
        return null;
    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public void onDismiss() {
        Util.backgroundAlpha(getActivity(), 1f);
    }

    public View inflateView(int lid) {
        return View.inflate(getContext(), lid, null);
    }
}
