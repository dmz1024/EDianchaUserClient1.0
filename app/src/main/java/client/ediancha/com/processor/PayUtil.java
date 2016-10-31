package client.ediancha.com.processor;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.Map;

import client.ediancha.com.entity.WechatInfo;
import client.ediancha.com.util.MyToast;

/**
 * Created by dengmingzhi on 2016/10/31.
 */

public class PayUtil {
    private Context ctx;

    public static PayUtil getInstance() {
        return new PayUtil();
    }

    public PayUtil setContext(Context ctx) {
        this.ctx = ctx;
        return this;
    }


    public void weChatPay(WechatInfo.Data data) {
        IWXAPI wxapi = WXAPIFactory.createWXAPI(ctx, "wx8917d124e0d8d9ff", false);
        wxapi.registerApp("wx8917d124e0d8d9ff");
        if (!wxapi.isWXAppInstalled()) {
            MyToast.showToast("请先下载微信客户端");
            return;
        }

        PayReq req = new PayReq();
        req.appId = "wx8917d124e0d8d9ff";
        req.partnerId = data.partnerid;
        req.prepayId = data.prepayid;
        req.nonceStr = data.noncestr;
        req.timeStamp = data.timestamp;
        req.packageValue = "Sign=WXPay";
        req.sign = data.sign;
        req.extData = "app data"; // optional
        wxapi.sendReq(req);
    }

    public void aliPay(final String data){
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(((Activity) ctx));
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(data, true);
                Message msg = new Message();
                msg.what = 1;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        }).start();



    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        MyToast.showToast("支付成功");
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            MyToast.showToast("支付结果确认中");
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            MyToast.showToast("支付失败");
                        }

                    }
                    break;
                }
            }
        }

    };
}
