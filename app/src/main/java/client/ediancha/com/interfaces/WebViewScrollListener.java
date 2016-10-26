package client.ediancha.com.interfaces;

import android.webkit.WebView;
import android.widget.ScrollView;

import client.ediancha.com.myview.MyWebView;

/**
 * Created by dengmingzhi on 16/10/17.
 */

public interface WebViewScrollListener {
    void onScrollChanged(MyWebView webView, int x, int y, int oldx, int oldy);

}
