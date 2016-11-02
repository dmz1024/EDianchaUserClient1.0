package client.ediancha.com.api;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import client.ediancha.com.activity.AddCommentActivity;
import client.ediancha.com.activity.LoginActivity;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.entity.UploadImage;
import client.ediancha.com.myview.PopMessageTips;
import client.ediancha.com.util.MyToast;
import client.ediancha.com.util.Util;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dengmingzhi on 2016/11/2.
 */

public class UpImageUtil {
    private Context ctx;

    public static UpImageUtil getInstance() {
        return new UpImageUtil();
    }

    public UpImageUtil setContext(Context ctx) {
        this.ctx = ctx;
        return this;
    }
    public void upImage(ArrayList<String> urls) {
        final ProgressDialog pd = new ProgressDialog(ctx);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("正在上传图片");
        pd.show();
        Map<String, RequestBody> params = new HashMap<String, RequestBody>();
        for (int i = 0; i < urls.size(); i++) {
            File file = new File(urls.get(i));
            String mimeType = MimeTypeMap.getSingleton()
                    .getMimeTypeFromExtension(
                            MimeTypeMap.getFileExtensionFromUrl(file.getPath()));
            RequestBody fileBody = RequestBody.create(MediaType.parse(mimeType), file);
            params.put("file[]\"; filename=\"" + file.getName(), fileBody);
        }

        OkHttpClient.Builder builder1 = new OkHttpClient.Builder();

        //设置超时
        builder1.connectTimeout(300, TimeUnit.SECONDS);
        builder1.readTimeout(200, TimeUnit.SECONDS);
        builder1.writeTimeout(200, TimeUnit.SECONDS);

        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl("http://www.ediancha.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder1.build())
                .build();
        UploadFileInterface uploadFile = retrofit1.create(UploadFileInterface.class);
//
        Map<String, String> map = new HashMap<>();
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);
        map.put("c", "appuploadpic");
        map.put("a", "upload");

        Call<UploadImage> upload = uploadFile.upload(map, params);
        upload.enqueue(new Callback<UploadImage>() {
            @Override
            public void onResponse(Call<UploadImage> call, Response<UploadImage> response) {
                UploadImage uploadImage = response.body();
                pd.cancel();
                if (uploadImage.result == 0) {
                    if (onUpLoadListener != null) {
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < uploadImage.data.size(); i++) {
                            list.add(uploadImage.data.get(i).image);
                        }
                        onUpLoadListener.urls(list);
                    }
                } else {
                    MyToast.showToast(uploadImage.msg);
                    if (onUpLoadListener != null) {
                        onUpLoadListener.faile();
                    }

                    if (uploadImage.msg.contains("token")) {
                        new PopMessageTips("账号信息", "账号信息已过期,请重新登录!", "去登录", "再看看") {
                            @Override
                            protected void right() {
                                super.right();
                                MyToast.showToast("请先登录");
                                Util.skip(((Activity) ctx), LoginActivity.class);
                            }
                        }.showView(ctx);
                    } else {
                        MyToast.showToast(uploadImage.msg);
                    }
                }
            }

            @Override
            public void onFailure(Call<UploadImage> call, Throwable t) {
                t.printStackTrace();
                if (onUpLoadListener != null) {
                    onUpLoadListener.faile();
                }
                pd.cancel();
            }
        });

    }

    private OnUpLoadListener onUpLoadListener;

    public UpImageUtil setOnUpLoadListener(OnUpLoadListener onUpLoadListener) {
        this.onUpLoadListener = onUpLoadListener;
        return this;
    }

    public interface OnUpLoadListener {
        void urls(List<String> urls);

        void faile();
    }
}
