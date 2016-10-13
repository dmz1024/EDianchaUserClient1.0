package client.ediancha.com.base;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.util.MyToast;

/**
 * Created by dengmingzhi on 16/6/14.
 */
public abstract class NetworkBaseFragment<D> extends BaseFragment {
    protected boolean isCanRefresh = false;
    protected Map<String, String> map = new HashMap<>();
    public D d;
    protected View noDataView;
    protected View havaDataView;
    protected View noNetworkView;
    protected View serverErrView;
    protected View isLoadingView;
    protected ShowCurrentViewENUM currentView;

    @Override
    protected void initData() {
        getData();
    }


    @Override
    protected void initView(View view) {

    }


    @Override
    protected int getViewId() {
        return 0;
    }


    @Override
    protected View getShowView() {
        switch (currentView = getDefaultView()) {
            case VIEW_IS_LOADING:
                isCanRefresh = false;
                return isLoadingView = getIsLoadingView();
            case VIEW_HAVE_DATA:
                isCanRefresh = true;
                return havaDataView = getHaveDataView();
            case VIEW_NO_DATA:
                isCanRefresh = true;
                return noDataView = getNoDataView();
            case VIEW_SERVER_ERR:
                isCanRefresh = true;
                return serverErrView = getServerErrView();
            case VIEW_NO_NETWORK:
                isCanRefresh = true;
                return noNetworkView = getNoNetworkView();
        }

        return isLoadingView = getIsLoadingView();
    }


    /**
     * 第一次进入页面的时候默认加载的页面
     *
     * @return
     */
    protected ShowCurrentViewENUM getDefaultView() {
        return ShowCurrentViewENUM.VIEW_IS_LOADING;
    }

    /**
     * 获取网络数据
     */
    protected abstract void getData();

    /**
     * 请求方式
     *
     * @return
     */
    protected RequestMenthod getMethod() {
        return RequestMenthod.POST;
    }


    /**
     * 返回http请求地址
     *
     * @return
     */
    protected abstract String getUrl();

    /**
     * 参数集合
     *
     * @return
     */
    protected abstract Map<String, String> getMap();

    /**
     * 返回数据转换之后的类型
     *
     * @return
     */
    protected abstract Class<D> getTClass();


    public enum ShowCurrentViewENUM {
        //有数据、没有数据,没有网络,服务器错误,正在加载数据
        VIEW_HAVE_DATA, VIEW_NO_DATA, VIEW_NO_NETWORK, VIEW_SERVER_ERR, VIEW_IS_LOADING;
    }

    public enum RequestMenthod {
        POST, GET;
    }

    /**
     * 根据请求服务器返回的数据来判断当前显示的视图
     *
     * @param type
     */
    protected void getCurrentView(ShowCurrentViewENUM type) {
        View showView = null;
        swipeRefreshLayout.setEnabled(false);
        switch (type) {
            case VIEW_HAVE_DATA:
                if (havaDataView == null) {
                    havaDataView = getHaveDataView();
                }

                showView = havaDataView;
                break;
            case VIEW_NO_DATA:
                if (noDataView == null) {
                    noDataView = getNoDataView();
                }
                showView = noDataView;
                break;
            case VIEW_NO_NETWORK:
                if (noNetworkView == null) {
                    noNetworkView = getNoNetworkView();
                }
                showView = noNetworkView;
                break;
            case VIEW_SERVER_ERR:
                if (serverErrView == null) {
                    serverErrView = getServerErrView();
                }
                showView = serverErrView;
                break;
            case VIEW_IS_LOADING:
                if (isLoadingView == null) {
                    isLoadingView = getIsLoadingView();
                }
                showView = isLoadingView;
                break;
        }

        if (type != currentView) {
            currentView = type;
            frameLayout.removeAllViews();
            frameLayout.addView(showView);
        }

        if (showView != isLoadingView &&getCanRefresh()) {
            isCanRefresh = true;
            swipeRefreshLayout.setEnabled(true);
        }

    }


    /**
     * 数据异常
     *
     * @return
     */
    protected View getServerErrView() {
        View view = inflateView(R.layout.view_show_err);
        Button bt_err = (Button) view.findViewById(R.id.bt_err);
        bt_err.setOnClickListener(this);
        return view;
    }

    ;

    /**
     * 网络异常
     *
     * @return
     */
    protected View getNoNetworkView() {
        View view = inflateView(R.layout.view_show_nonetwork);
        Button bt_network = (Button) view.findViewById(R.id.bt_network);
        bt_network.setOnClickListener(this);
        return view;
    }

    /**
     * 空数据
     *
     * @return
     */
    protected View getNoDataView() {
        View view = inflateView(R.layout.view_show_empty);
        Button bt_empty = (Button) view.findViewById(R.id.bt_empty);
        bt_empty.setOnClickListener(this);
        return view;
    }

    /**
     * 有数据
     *
     * @return
     */
    protected abstract View getHaveDataView();

    /**
     * 正在加载
     *
     * @return
     */
    protected View getIsLoadingView() {
        View view = inflateView(R.layout.view_show_load);
        ImageView iv_load = (ImageView) view.findViewById(R.id.iv_load);
        Glide.with(getContext()).load(R.mipmap.loading).into(iv_load);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_err:
                setUserVisibleHint(true);
                break;
            case R.id.bt_empty:
                setUserVisibleHint(true);
                break;
            case R.id.bt_network:
                MyToast.showToast("设置网络");
                break;
        }
    }

    @Override
    public boolean isCanRefresh() {
        return isCanRefresh;
    }

    protected boolean getCanRefresh() {
        return true;
    }

}
