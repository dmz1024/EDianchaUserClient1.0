package client.ediancha.com.processor;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import client.ediancha.com.api.MyRetrofitUtil;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.entity.BaseEntity;
import client.ediancha.com.interfaces.OnResultListener;
import client.ediancha.com.util.MyToast;

/**
 * Created by dengmingzhi on 2016/11/2.
 */

public class OrderUtil {
    private Context ctx;

    public static OrderUtil getInstance() {
        return new OrderUtil();
    }

    private OrderUtil() {
    }

    public OrderUtil setContext(Context ctx) {
        this.ctx = ctx;
        return this;
    }

    /**
     * 取消订单
     *
     * @param id
     */
    public void cancle(String id, String type) {
        Map<String, String> map = new HashMap<>();
        map.put("c", "myorder");
        map.put("a", "cancel");
        map.put("dataid", id);
        map.put("type", type);
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);

        MyRetrofitUtil.getInstance().post("app.php", map, BaseEntity.class, new MyRetrofitUtil.OnRequestListener<BaseEntity>() {
            @Override
            public void noNetwork() {

            }

            @Override
            public void serverErr() {

            }

            @Override
            public void haveData(BaseEntity baseEntity) {
                if (baseEntity.result == 0) {
                    MyToast.showToast("取消成功");
                    if (onResultListener != null) {
                        onResultListener.resultOk();
                    }
                } else {
                    MyToast.showToast(baseEntity.msg);
                    if (onResultListener != null) {
                        onResultListener.resultFaile();
                    }
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void resultNo0(String s) {

            }

            @Override
            public void start() {

            }
        }, "正在取消...", ctx);
    }

    private OnResultListener onResultListener;

    public OrderUtil setOnResultListener(OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
        return this;
    }


    /**
     * 确认收货
     *
     * @param no
     */
    public void receive(String no) {
        Map<String, String> map = new HashMap<>();
        map.put("c", "myorder");
        map.put("a", "receive");
        map.put("order_no", no);
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);

        MyRetrofitUtil.getInstance().post("app.php", map, BaseEntity.class, new MyRetrofitUtil.OnRequestListener<BaseEntity>() {
            @Override
            public void noNetwork() {

            }

            @Override
            public void serverErr() {

            }

            @Override
            public void haveData(BaseEntity baseEntity) {
                if (baseEntity.result == 0) {
                    MyToast.showToast("确认收货成功");
                    if (onResultListener != null) {
                        onResultListener.resultOk();
                    }
                } else {
                    MyToast.showToast(baseEntity.msg);
                    if (onResultListener != null) {
                        onResultListener.resultFaile();
                    }
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void resultNo0(String s) {

            }

            @Override
            public void start() {

            }
        }, "正在提交收货信息...", ctx);
    }

}
