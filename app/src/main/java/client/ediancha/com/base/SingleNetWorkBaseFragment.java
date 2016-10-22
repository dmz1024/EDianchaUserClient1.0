package client.ediancha.com.base;

import android.text.TextUtils;

import com.google.gson.Gson;

import client.ediancha.com.activity.LoginActivity;
import client.ediancha.com.api.MyRetrofitUtil;
import client.ediancha.com.api.RetrofitUtil;
import client.ediancha.com.entity.BaseEntity;
import client.ediancha.com.myview.PopMessageTips;
import client.ediancha.com.util.MyToast;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 16/10/11.
 */

public abstract class SingleNetWorkBaseFragment<D extends BaseEntity> extends NetworkBaseFragment<D> {
    public boolean result;

    protected abstract void writeData(D t);

    @Override
    protected void getData() {
        MyRetrofitUtil retrofitUtil = MyRetrofitUtil.getInstance();
        MyRetrofitUtil.OnRequestListener<D> onRequestListener = new MyRetrofitUtil.OnRequestListener<D>() {
            @Override
            public void noNetwork() {
                if (havaDataView != null) {
                    MyToast.showToast("网络似乎出了点问题");
                } else {
                    getCurrentView(ShowCurrentViewENUM.VIEW_NO_NETWORK);
                }

                setStopRefresh();
            }

            @Override
            public void serverErr() {
                if (havaDataView != null) {
                    MyToast.showToast("服务器似乎出了点问题");
                } else {
                    getCurrentView(ShowCurrentViewENUM.VIEW_SERVER_ERR);
                }

                setStopRefresh();
            }

            @Override
            public void haveData(D t) {
                setStopRefresh();
                getCurrentView(ShowCurrentViewENUM.VIEW_HAVE_DATA);
                if (t.result == 0) {
                    result = true;
                    writeData(t);
                }
            }

            @Override
            public void onCompleted() {
                setStopRefresh();
            }

            @Override
            public void resultNo0(String s) {
                resultNot0(s);

            }

            @Override
            public void start() {

            }
        };


        if (getMethod() == RequestMenthod.POST) {
            retrofitUtil.get(getUrl(), getMap(), getTClass(), onRequestListener);
        } else {
            retrofitUtil.post(getUrl(), getMap(), getTClass(), onRequestListener);
        }

    }

    protected void resultNot0(String t) {
        result = false;

    }
}
