package client.ediancha.com.processor;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import client.ediancha.com.api.MyRetrofitUtil;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.entity.AliPayInfo;
import client.ediancha.com.entity.BaseEntity;
import client.ediancha.com.entity.WechatInfo;
import client.ediancha.com.util.MyToast;

/**
 * Created by dengmingzhi on 2016/10/31.
 */

public class PayInfoUtil {
    private Context ctx;

    public static PayInfoUtil getInstance() {
        return new PayInfoUtil();
    }

    private PayInfoUtil() {
    }

    public PayInfoUtil setContext(Context ctx) {
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

        MyRetrofitUtil.getInstance().post("app.php", map, WechatInfo.class, new MyRetrofitUtil.OnRequestListener<WechatInfo>() {
            @Override
            public void noNetwork() {

            }

            @Override
            public void serverErr() {

            }

            //Sign=WXPay
            @Override
            public void haveData(WechatInfo wechatInfo) {
                if (wechatInfo.result == 0) {
                    PayUtil.getInstance().setContext(ctx).weChatPay(wechatInfo.data);
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
        }, "获取支付信息...", ctx);
    }

    /**
     * 获取微信支付预授权ID
     *
     * @param order_no 订单id
     */
    public void getAliPayId(String order_no) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);
        map.put("c", "myorder");
        map.put("a", "pay");
        map.put("order_no", order_no);
        map.put("type", "alipay");

        MyRetrofitUtil.getInstance().post("app.php", map, AliPayInfo.class, new MyRetrofitUtil.OnRequestListener<AliPayInfo>() {
            @Override
            public void noNetwork() {

            }

            @Override
            public void serverErr() {

            }

            @Override
            public void haveData(AliPayInfo aliPayInfo) {
                if (aliPayInfo.result == 0) {
                    PayUtil.getInstance().setContext(ctx).aliPay(aliPayInfo.data);
                } else {
                    MyToast.showToast(aliPayInfo.msg);
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
        }, "获取支付信息...", ctx);
    }


    public interface OnPayInfoListener {
        void payInfo();
    }
}
