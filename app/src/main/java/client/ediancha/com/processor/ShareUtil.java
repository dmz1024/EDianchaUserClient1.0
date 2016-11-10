package client.ediancha.com.processor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

import java.io.File;
import java.util.concurrent.ExecutionException;

import client.ediancha.com.api.DownFile;
import client.ediancha.com.util.Util;

import static client.ediancha.com.util.Util.getApplication;

/**
 * Created by dengmingzhi on 2016/10/21.
 */

public class ShareUtil {
    public static ShareUtil getInstance() {
        return new ShareUtil();
    }

    public void QQFriend(Context ctx, ShareInfo qqInfo) {
        Tencent mTencent = Tencent.createInstance("1105772244", getApplication());
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, qqInfo.title);
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, qqInfo.content);
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, qqInfo.url);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, qqInfo.logo);
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, qqInfo.app_name);
//                    params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
        mTencent.shareToQQ(((Activity) ctx), params, null);
    }


    public void Wechat(final Handler mHandle, final Context ctx, final ShareInfo wechatInfo) {
        final ProgressDialog pd = new ProgressDialog(ctx);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("正在下载分享图片...");
        pd.show();

        new Thread() {
            @Override
            public void run() {
                super.run();
                FutureTarget<File> fileFutureTarget = Glide.with(ctx).load(wechatInfo.logo).downloadOnly(160, 96);
                try {
                    File file = fileFutureTarget.get();
                    String path = file.getAbsolutePath();
                    pd.cancel();
                    IWXAPI mWecha = WXAPIFactory.createWXAPI(ctx, "wx8917d124e0d8d9ff", true);
                    mWecha.registerApp("wx8917d124e0d8d9ff");
                    WXWebpageObject webpageObject = new WXWebpageObject();
                    webpageObject.webpageUrl = wechatInfo.url;
                    WXMediaMessage msg = new WXMediaMessage(webpageObject);
                    msg.title = wechatInfo.title;
                    msg.thumbData = Util.bmpToByteArray(Util.getimage(path), true);
                    msg.description = wechatInfo.content;
                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = String.valueOf(System.currentTimeMillis());//用于唯一标识一个请求
                    req.message = msg;
                    if (wechatInfo.type == 1) {
                        req.scene = SendMessageToWX.Req.WXSceneTimeline;
                    } else if (wechatInfo.type == 0) {
                        req.scene = SendMessageToWX.Req.WXSceneSession;
                    }
                    mWecha.sendReq(req);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }.start();


//        final DownFile downFile = new DownFile(wechatInfo.logo, "png");
//        downFile.setOnDownFileListener(new DownFile.OnDownFileListener() {
//            @Override
//            public void err(String e) {
//                pd.cancel();
//            }
//
//            @Override
//            public void downOk(final String absolutePath) {
//
//                mHandle.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        IWXAPI mWecha = WXAPIFactory.createWXAPI(ctx, "wx8917d124e0d8d9ff", true);
//                        mWecha.registerApp("wx8917d124e0d8d9ff");
//                        WXWebpageObject webpageObject = new WXWebpageObject();
//                        webpageObject.webpageUrl = wechatInfo.url;
//                        WXMediaMessage msg = new WXMediaMessage(webpageObject);
//                        msg.title = wechatInfo.title;
//                        msg.thumbData = Util.bmpToByteArray(Util.getimage(absolutePath), true);
//                        msg.description = wechatInfo.content;
//                        SendMessageToWX.Req req = new SendMessageToWX.Req();
//                        req.transaction = String.valueOf(System.currentTimeMillis());//用于唯一标识一个请求
//                        req.message = msg;
//                        if (wechatInfo.type == 1) {
//                            req.scene = SendMessageToWX.Req.WXSceneTimeline;
//                        } else if (wechatInfo.type == 0) {
//                            req.scene = SendMessageToWX.Req.WXSceneSession;
//                        }
//                        mWecha.sendReq(req);
//                    }
//                });
//
//                pd.cancel();
//
//
//            }
//
//            @Override
//            public void progress(long currenSize) {
//
//            }
//
//            @Override
//            public void size(long size) {
//
//            }
//        });
//
//
//        new Thread() {
//            @Override
//            public void run() {
//                downFile.downLoadFile();
//            }
//        }.start();


    }


    public static class ShareInfo {
        public String title;
        public String content;
        public String url;
        public String logo;
        public String app_name = "E点茶";
        public int type;

    }


}
