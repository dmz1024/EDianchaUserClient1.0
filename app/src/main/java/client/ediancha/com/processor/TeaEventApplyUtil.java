package client.ediancha.com.processor;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import client.ediancha.com.activity.LoginActivity;
import client.ediancha.com.api.MyRetrofitUtil;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.entity.BaseEntity;
import client.ediancha.com.myview.PopMessageTips;
import client.ediancha.com.myview.PopTeaEventApply;
import client.ediancha.com.util.MyToast;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 2016/10/21.
 */

public class TeaEventApplyUtil {
    public static TeaEventApplyUtil getInstance() {
        return new TeaEventApplyUtil();
    }

    public void apply(final Context ctx, final String id){
        new PopTeaEventApply(ctx) {
            @Override
            protected void itemClick(String tel, String name) {
                if (TextUtils.isEmpty(UserInfo.uid)) {
                    MyToast.showToast("请先登录");
                    Util.skip(((Activity) ctx), LoginActivity.class);
                    return;
                }

                Map<String, String> map = new HashMap<>();
                map.put("uid", UserInfo.uid);
                map.put("token", UserInfo.token);
                map.put("name", name);
                map.put("mobile", tel);
                map.put("cid", id);
                map.put("c", "chahui");
                map.put("a", "baoming");

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
                            MyToast.showToast("报名成功");
                            onDismiss();
                        } else {
                            if (baseEntity.msg.contains("token")) {
                                new PopMessageTips("账号信息", "账号信息已过期,请重新登录!", "去登录", "再看看") {
                                    @Override
                                    protected void right() {
                                        super.right();
                                        Util.skip(((Activity) ctx), LoginActivity.class);
                                    }
                                }.showView(ctx);
                            } else {
                                MyToast.showToast(baseEntity.msg);
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
                }, "正在提交报名信息...", ctx);

            }
        }.showAtLocation();
    }

}
