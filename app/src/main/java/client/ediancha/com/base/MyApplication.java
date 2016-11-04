package client.ediancha.com.base;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by dengmingzhi on 16/6/3.
 */
public class MyApplication extends Application {
    public static MyApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        initJpush();
    }

    public static MyApplication app() {
        return application;
    }


    /**
     * 初始化极光推送
     */
    private void initJpush() {
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);
    }

}
