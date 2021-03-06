package client.ediancha.com.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import client.ediancha.com.interfaces.WebViewScrollListener;

/**
 * Created by dengmingzhi on 2016/10/26.
 */

public class MyWebView extends WebView {
    private WebViewScrollListener webViewScrollListener;

    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (webViewScrollListener != null) {
            webViewScrollListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }


    public void setWebViewScrollListener(WebViewScrollListener webViewScrollListener) {
        this.webViewScrollListener = webViewScrollListener;
    }
}
