package client.ediancha.com.activity;

import android.view.KeyEvent;
import android.webkit.WebView;

import client.ediancha.com.R;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.base.WebBaseFragment;
import client.ediancha.com.myview.MyWebView;

/**
 * Created by dengmingzhi on 2016/11/3.
 */

public class WebViewActivity extends ToolBarActivity implements WebBaseFragment.OnReturnTitleListener {
    private WebBaseFragment webBaseFragment;

    @Override
    protected String getToolBarTitle() {
        return null;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fg_base, webBaseFragment = WebBaseFragment.getInstance(getIntent()
                        .getStringExtra("url")))
                .commit();
        webBaseFragment.setOnReturnTitleListener(this);
    }

    @Override
    protected int getRid() {
        return R.layout.base_framelayout;
    }

    @Override
    public void title(String title) {
        setTitle(title);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            if (webBaseFragment != null) {
                WebView webView;
                if ((webView = webBaseFragment.getWebView()) != null && webView.canGoBack()) {
                    webView.goBack();
                } else {
                    back();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
