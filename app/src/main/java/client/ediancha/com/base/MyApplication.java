package client.ediancha.com.base;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by dengmingzhi on 16/6/3.
 */
public class MyApplication extends Application {
    public static MyApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        application = this;
    }

    public static MyApplication app() {
        return application;
    }

}
