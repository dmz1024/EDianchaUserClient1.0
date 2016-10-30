package client.ediancha.com.processor;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import client.ediancha.com.api.MyRetrofitUtil;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.entity.BaseEntity;

/**
 * Created by dengmingzhi on 2016/10/31.
 */

public class PayUtil {
    private Context ctx;

    public static PayUtil getInstance() {
        return new PayUtil();
    }

    private PayUtil() {
    }

    public PayUtil setContext(Context ctx) {
        this.ctx = ctx;
        return this;
    }

    /**
     * 获取微信支付预授权ID
     *
     * @param order_no 订单id
     */
    public void getWechatPayId(String order_no) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);
        map.put("c", "myorder");
        map.put("a", "pay");
        map.put("order_no", order_no);
        map.put("type", "weixin");

        MyRetrofitUtil.getInstance().post("app.php", map, BaseEntity.class, new MyRetrofitUtil.OnRequestListener<BaseEntity>() {
            @Override
            public void noNetwork() {

            }

            @Override
            public void serverErr() {

            }

            @Override
            public void haveData(BaseEntity baseEntity) {

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
        }, "获取支付信息...", ctx);
    }
}
