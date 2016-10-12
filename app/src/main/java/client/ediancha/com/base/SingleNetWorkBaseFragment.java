package client.ediancha.com.base;

import android.util.Log;
import android.view.View;

import java.util.Map;

import client.ediancha.com.api.RetrofitUtil;
import client.ediancha.com.entity.Data;
import client.ediancha.com.util.MyToast;

/**
 * Created by dengmingzhi on 16/10/11.
 */

public abstract class SingleNetWorkBaseFragment<D> extends NetworkBaseFragment<D> {

    protected abstract void writeData(D t);

    @Override
    protected void getData() {
        RetrofitUtil retrofitUtil = RetrofitUtil.getInstance();
        RetrofitUtil.OnRequestListener<D> onRequestListener = new RetrofitUtil.OnRequestListener<D>() {
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
                writeData(t);

            }

            @Override
            public void onCompleted() {
                setStopRefresh();
            }
        };


        if (getMethod() == RequestMenthod.POST) {
            retrofitUtil.get(getUrl(), getMap(), getTClass(), onRequestListener);
        } else {
            retrofitUtil.post(getUrl(), getMap(), getTClass(), onRequestListener);
        }

    }
}
