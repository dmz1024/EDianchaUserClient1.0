package client.ediancha.com.processor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import client.ediancha.com.activity.LoginActivity;
import client.ediancha.com.api.MyRetrofitUtil;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.entity.BaseEntity;
import client.ediancha.com.interfaces.OnResultListener;
import client.ediancha.com.myview.PopMessageTips;
import client.ediancha.com.util.MyToast;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 2016/10/21.
 */

public class CollectionUtil {
    private String collectType = "收藏";

    public static CollectionUtil getInstance() {
        return new CollectionUtil();
    }


    /**
     * 是否收藏
     */
    public void isCollection(String id, String type) {
        if (TextUtils.isEmpty(UserInfo.uid)) {
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("c", "shoucang");
        map.put("a", "find");
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);
        map.put("dataid", id);
        map.put("type", type);

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
                    collectType = "收藏";
                } else {
                    collectType = "取消收藏";
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
        });
    }

    /**
     * 收藏
     */
    public void collection(final Context ctx, String id, String type) {
        if (TextUtils.isEmpty(UserInfo.uid)) {
            MyToast.showToast("请先登录");
            Intent intent = new Intent(ctx, LoginActivity.class);
            ((Activity) ctx).startActivityForResult(intent, Constant.COLLECTION_REQ);
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("c", "shoucang");
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);
        map.put("dataid", id);
        map.put("type", type);
        if (TextUtils.equals("取消收藏", collectType)) {
            map.put("a", "cancel");
        } else {
            map.put("a", "add");
        }

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
                    if (onResultListener != null) {
                        onResultListener.resultOk();
                    }
                    MyToast.showToast(collectType + "成功");
                    collectType = TextUtils.equals("取消收藏", collectType) ? "收藏" : "取消收藏";
                } else {
                    if (baseEntity.msg.contains("token")) {
                        new PopMessageTips("账号信息", "账号信息已过期,请重新登录!", "去登录", "再看看") {
                            @Override
                            protected void right() {
                                super.right();
                                MyToast.showToast("请先登录");
                                Intent intent = new Intent(ctx, LoginActivity.class);
                                ((Activity) ctx).startActivityForResult(intent, Constant.COLLECTION_REQ);
                            }
                        }.showView(ctx);
                    } else {
                        MyToast.showToast(baseEntity.msg);
                    }
                    onResultListener.resultFaile();

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
        }, "正在" + collectType + "...", ctx);


    }


    public String collectType() {
        return collectType;
    }

    public CollectionUtil setCollectType(String collectType) {
        this.collectType = collectType;
        return this;
    }

    private OnResultListener onResultListener;

    public CollectionUtil setOnResultListener(OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
        return this;
    }

}
