package client.ediancha.com.api;

import android.app.ProgressDialog;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import client.ediancha.com.util.MyToast;
import client.ediancha.com.util.Util;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dengmingzhi on 16/10/10.
 */

public class RetrofitUtil {
    public static final String BASE_URL = "http://t.mobaoxiu.com/";
    private static RetrofitUtil retrofitUtil;
    private static final int DEFAULT_TIMEOUT = 5;
    private Retrofit retrofit;
    private ApiService apiService;
    private static Gson gson;

    //构造方法私有
    private RetrofitUtil() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        apiService = retrofit.create(ApiService.class);
    }


    //在访问RetrofitUtil时创建单例
    private static class SingletonHolder {
        private static final RetrofitUtil INSTANCE = new RetrofitUtil();
    }

    //获取单例
    public static RetrofitUtil getInstance() {
        if (retrofitUtil == null) {
            return retrofitUtil = RetrofitUtil.SingletonHolder.INSTANCE;
        }
        return retrofitUtil;
    }

//    pd = new ProgressDialog(ctx);
//    pd.setCancelable(false);
//    pd.setCanceledOnTouchOutside(false);
//    pd.setMessage(msg);
//    pd.show();
//    pd.getCurrentFocus().postDelayed(new Runnable() {
//        @Override
//        public void run() {
//            get();
//        }
//    },300);


    public void get(String url, Map<String, String> map, final Class cla, final OnRequestListener onRequestListener) {

        if (!Util.isNetworkAvailable()) {
            onRequestListener.noNetwork();
            return;
        }

        if (TextUtils.isEmpty(url)) {
            url = "";
        }

        if (map == null) {
            map = new HashMap<>();
        }

        apiService.get(url, map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!Util.isNetworkAvailable()) {
                            onRequestListener.noNetwork();
                        } else {
                            onRequestListener.serverErr();
                        }

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("返回数据", s);
                        if (gson == null) {
                            gson = new Gson();
                        }
                        try {
                            onRequestListener.haveData(gson.fromJson(s, cla));
                        } catch (Exception e) {
                            haveData(s);
                        }

                    }

                    @Override
                    public void onStart() {

                    }

                });

    }

    public void get(String url, Map<String, String> map, final Class cla, final OnRequestListener onRequestListener, String msg) {

        if (!Util.isNetworkAvailable()) {
            onRequestListener.noNetwork();
            return;
        }

        if (TextUtils.isEmpty(url)) {
            url = "";
        }

        if (map == null) {
            map = new HashMap<>();
        }


        ProgressDialog pd = new ProgressDialog(Util.getApplication());
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage(msg);
        pd.show();

        apiService.get(url, map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!Util.isNetworkAvailable()) {
                            onRequestListener.noNetwork();
                        } else {
                            onRequestListener.serverErr();
                        }

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("返回数据", s);
                        if (gson == null) {
                            gson = new Gson();
                        }
                        try {
                            onRequestListener.haveData(gson.fromJson(s, cla));
                        } catch (Exception e) {
                            haveData(s);
                        }

                    }

                    @Override
                    public void onStart() {

                    }

                });

    }

    protected void haveData(String s) {
        MyToast.showToast("错误格式");
        Log.d("错误格式", s);
    }


    public void post(String url, Map<String, String> map, final Class cla, final OnRequestListener onRequestListener, String msg) {

        if (!Util.isNetworkAvailable()) {
            onRequestListener.noNetwork();
            return;
        }

        if (TextUtils.isEmpty(url)) {
            url = "";
        }

        if (map == null) {
            map = new HashMap<>();
        }


        apiService.post(url, map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        onRequestListener.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!Util.isNetworkAvailable()) {
                            onRequestListener.noNetwork();
                        } else {
                            onRequestListener.serverErr();
                        }

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("返回数据", s);
                        if (gson == null) {
                            gson = new Gson();
                        }
                        onRequestListener.haveData(gson.fromJson(s, cla));
                    }

                    @Override
                    public void onStart() {

                    }

                });

    }


    public void post(String url, Map<String, String> map, final Class cla, final OnRequestListener onRequestListener) {

        if (!Util.isNetworkAvailable()) {
            onRequestListener.noNetwork();
            return;
        }

        if (TextUtils.isEmpty(url)) {
            url = "";
        }

        if (map == null) {
            map = new HashMap<>();
        }

        apiService.post(url, map).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        onRequestListener.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!Util.isNetworkAvailable()) {
                            onRequestListener.noNetwork();
                        } else {
                            onRequestListener.serverErr();
                        }

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("返回数据", s);
                        if (gson == null) {
                            gson = new Gson();
                        }
                        onRequestListener.haveData(gson.fromJson(s, cla));
                    }

                    @Override
                    public void onStart() {

                    }

                });

    }


    public interface OnRequestListener<T> {

        void noNetwork();

        void serverErr();

        void haveData(T t);

        void onCompleted();
    }

}
