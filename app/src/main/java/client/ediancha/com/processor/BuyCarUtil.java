package client.ediancha.com.processor;

import android.app.Activity;
import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import client.ediancha.com.activity.LoginActivity;
import client.ediancha.com.api.MyRetrofitUtil;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.entity.BaseEntity;
import client.ediancha.com.entity.PayOrderNo;
import client.ediancha.com.interfaces.OnResultListener;
import client.ediancha.com.myview.PopMessageTips;
import client.ediancha.com.util.MyToast;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 2016/10/30.
 */

public class BuyCarUtil {
    private Context ctx;

    public static BuyCarUtil getInstance() {
        return new BuyCarUtil();
    }

    private BuyCarUtil() {
    }

    public BuyCarUtil setContext(Context ctx) {
        this.ctx = ctx;
        return this;
    }


    /**
     * 加入购物车
     *
     * @param count      商品数量
     * @param sku_id     商品属性ID 有属性的商品为必填
     * @param product_id 商品ID
     */
    public void addBuyCar(int count, String sku_id, String product_id) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);
        map.put("product_id", product_id);
        map.put("quantity", count + "");
        map.put("sku_id", sku_id);
        map.put("send_other", "0");
        map.put("is_add_cart", "1");
        map.put("c", "myorder");
        map.put("a", "add");

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
                    MyToast.showToast("成功加入购物车");
                } else {
                    MyToast.showToast(baseEntity.msg);
                }

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void resultNo0(String s) {
                if (s.contains("token")) {
                    new PopMessageTips("账号信息", "账号信息已过期,请重新登录!", "去登录", "") {
                        @Override
                        protected void right() {
                            super.right();
                            Util.skip(((Activity) ctx), LoginActivity.class);
                        }
                    }.showView(ctx);
                }
            }

            @Override
            public void start() {

            }
        }, "正在加入购物车...", ctx);
    }


    /**
     * 删除购物车
     *
     * @param cart_id 购物车id
     */
    public void deleteBuyCar(String cart_id) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);
        map.put("c", "appcart");
        map.put("a", "delete");
        map.put("cart_id", cart_id);

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
                    MyToast.showToast("成功删除购物车");
                    if (onResultListener != null) {
                        onResultListener.resultOk();
                    }
                } else {
                    MyToast.showToast(baseEntity.msg);
                }

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void resultNo0(String s) {
                if (s.contains("token")) {
                    new PopMessageTips("账号信息", "账号信息已过期,请重新登录!", "去登录", "") {
                        @Override
                        protected void right() {
                            super.right();
                            Util.skip(((Activity) ctx), LoginActivity.class);
                        }
                    }.showView(ctx);
                }
            }

            @Override
            public void start() {

            }
        }, "正在删除购物车...", ctx);
    }


    public void updateCount(String cart_id, String sku_id, String product_id, String number) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);
        map.put("c", "appcart");
        map.put("a", "quantity");
        map.put("cart_id", cart_id);
        map.put("sku_id", sku_id);
        map.put("product_id", product_id);
        map.put("number", number);

        MyRetrofitUtil.getInstance().post("app.php", map, BaseEntity.class, new MyRetrofitUtil.OnRequestListener<BaseEntity>() {

            @Override
            public void noNetwork() {
                if (onResultListener != null) {
                    onResultListener.resultFaile();
                }
            }

            @Override
            public void serverErr() {
                if (onResultListener != null) {
                    onResultListener.resultFaile();
                }
            }

            @Override
            public void haveData(BaseEntity baseEntity) {
                if (baseEntity.result == 0) {
                    if (onResultListener != null) {
                        onResultListener.resultOk();
                    }
                } else {
                    MyToast.showToast(baseEntity.msg);
                }

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void resultNo0(String s) {
                if (s.contains("token")) {
                    new PopMessageTips("账号信息", "账号信息已过期,请重新登录!", "去登录", "") {
                        @Override
                        protected void right() {
                            super.right();
                            Util.skip(((Activity) ctx), LoginActivity.class);
                        }
                    }.showView(ctx);
                }
            }

            @Override
            public void start() {

            }
        }, "......", ctx);
    }


    private OnResultListener onResultListener;

    public BuyCarUtil setOnResultListener(OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
        return this;
    }

    private OnPayListener onPayListener;

    public BuyCarUtil setOnPayListener(OnPayListener onPayListener) {
        this.onPayListener = onPayListener;
        return this;
    }

    public interface OnPayListener{
        void payId(String id);
    }


    public void pay(String cart_id){
        Map<String, String> map = new HashMap<>();
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);
        map.put("c", "appcart");
        map.put("a", "pay");
        map.put("cart_id", cart_id);

        MyRetrofitUtil.getInstance().post("app.php", map, PayOrderNo.class, new MyRetrofitUtil.OnRequestListener<PayOrderNo>() {

            @Override
            public void noNetwork() {

            }

            @Override
            public void serverErr() {

            }

            @Override
            public void haveData(PayOrderNo baseEntity) {
                if (baseEntity.err_code == 0) {
                    if (onPayListener != null) {
                        onPayListener.payId(baseEntity.err_msg);
                    }
                } else {
                    MyToast.showToast(baseEntity.msg);
                }

            }


            @Override
            public void onCompleted() {

            }

            @Override
            public void resultNo0(String s) {
                if (s.contains("token")) {
                    new PopMessageTips("账号信息", "账号信息已过期,请重新登录!", "去登录", "") {
                        @Override
                        protected void right() {
                            super.right();
                            Util.skip(((Activity) ctx), LoginActivity.class);
                        }
                    }.showView(ctx);
                }
            }

            @Override
            public void start() {

            }
        }, "生成订单中...", ctx);
    }

}
