package client.ediancha.com.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * Created by dengmingzhi on 16/7/12.
 */
public class WebBaseFragment extends BaseFragment {
    private String url;
    private WebView webview;
    private OnReturnTitleListener onReturnTitleListener;
    private boolean isFistInitData = true;
    private boolean isCanRefresh = true;

    public static WebBaseFragment getInstance(String url) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        WebBaseFragment mapFragment = new WebBaseFragment();
        mapFragment.setArguments(bundle);
        return mapFragment;
    }

    public static WebBaseFragment getInstance(String url, boolean isFistInitData) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putBoolean("isFistInitData", isFistInitData);
        WebBaseFragment mapFragment = new WebBaseFragment();
        mapFragment.setArguments(bundle);
        return mapFragment;
    }

    public static WebBaseFragment getInstance(String url, boolean isFistInitData, boolean isCanRefresh) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putBoolean("isFistInitData", isFistInitData);
        bundle.putBoolean("isCanRefresh", isCanRefresh);
        WebBaseFragment mapFragment = new WebBaseFragment();
        mapFragment.setArguments(bundle);
        return mapFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            url = bundle.getString("url");
            isFistInitData = bundle.getBoolean("isFistInitData", true);
            isCanRefresh = bundle.getBoolean("isCanRefresh", true);
        }

    }

    @Override
    protected boolean isCanRefresh() {
        return isCanRefresh;
    }


    @Override
    protected boolean isCanFirstInitData() {
        return isFistInitData;
    }


    @Override
    protected void initData() {
        webview.loadUrl(url);
    }

    @Override
    protected void initView(View view) {
        webview = (WebView) view;
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                setStopRefresh();
                if (onReturnTitleListener != null) {
                    onReturnTitleListener.title(view.getTitle());
                }
            }
        });
    }

    @Override
    protected int getViewId() {
        return 0;
    }

    @Override
    protected View getShowView() {
        return new WebView(getContext());
    }

    public interface OnReturnTitleListener {
        void title(String title);
    }

    public void setOnReturnTitleListener(OnReturnTitleListener onReturnTitleListener) {
        this.onReturnTitleListener = onReturnTitleListener;
    }


    public WebView getWebView() {
        return webview;
    }


}
