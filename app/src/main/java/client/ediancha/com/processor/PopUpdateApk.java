package client.ediancha.com.processor;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.activity.MainActivity;
import client.ediancha.com.api.DownFile;
import client.ediancha.com.api.UpdateRetrofitUtil;
import client.ediancha.com.entity.FirimEntity;
import client.ediancha.com.myview.HorizontalProgressBarWithNumber;
import client.ediancha.com.util.MyToast;
import client.ediancha.com.util.SharedPreferenUtil;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 16/8/19.
 */
public class PopUpdateApk {
    private PopupWindow popupWindow;
    private Context ctx;
    private boolean isMust;
    private OnUpdateApkListener onUpdateApkListener;
    private String installPath;

    public PopUpdateApk(Context ctx) {
        this.ctx = ctx;
    }

    //    http://api.fir.im/apps/latest/57b58a49959d6927ad00118e?api_token=2fa659a23dd2c510e76aa917bdebf2a7
    public void CheckUpdate() {
        final int currentCode = Util.getAppVersionCode();
//        String json = new SharedPreferenUtil(ctx, "apkUpdate").getString("info");
//        if (json.length() > 40) {
//            FirimEntity firimEntity = new Gson().fromJson(json, FirimEntity.class);
//            if (!TextUtils.isEmpty(firimEntity.name)) {
//                if (firimEntity.version > currentCode) {
//                    if (onUpdateApkListener != null) {
//                        if (onUpdateApkListener.needUpdate(firimEntity.version)) {
//                            showUpdate(firimEntity);
//                        }
//                    }
//                } else {
//                    if (onUpdateApkListener != null) {
//                        onUpdateApkListener.currentIsNew();
//                    }
//                }
//            }
//        }


        Map<String, String> map = new HashMap<>();
        map.put("api_token", "2fa659a23dd2c510e76aa917bdebf2a7");
        UpdateRetrofitUtil.getInstance().post("", map, FirimEntity.class, new UpdateRetrofitUtil.OnRequestListener<FirimEntity>() {
            @Override
            public void noNetwork() {

            }

            @Override
            public void serverErr() {

            }

            @Override
            public void haveData(FirimEntity firimEntity) {
                if (!TextUtils.isEmpty(firimEntity.name)) {
                    if (firimEntity.version > currentCode) {
                        if (onUpdateApkListener != null) {
                            if (onUpdateApkListener.needUpdate(firimEntity.version)) {
                                showUpdate(firimEntity);
                            }
                        }
                    } else {
                        if (onUpdateApkListener != null) {
                            onUpdateApkListener.currentIsNew();
                        }
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
        });



    }


    public void showUpdate(FirimEntity updateInfo) {
        this.isMust = updateInfo.version % 2 == 0;
        popupWindow = new PopupWindow(getView(updateInfo), FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT, true);
        popupWindow.showAtLocation(((Activity) ctx).findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
    }

    private View getView(final FirimEntity updateInfo) {
        View view = View.inflate(ctx, R.layout.pop_update_apk, null);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_content.setText(updateInfo.changelog);
        final Button bt_update = (Button) view.findViewById(R.id.bt_update);
        final Button bt_cancle = (Button) view.findViewById(R.id.bt_cancle);
        final HorizontalProgressBarWithNumber pb = (HorizontalProgressBarWithNumber) view.findViewById(R.id.pb);
        if (isMust) {
            bt_update.setText("立即更新(否则影响使用)");
            bt_cancle.setVisibility(View.GONE);
        }
        bt_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                if (onUpdateApkListener != null) {
                    onUpdateApkListener.cancle(updateInfo.version);
                }
            }
        });

        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.equals(bt_update.getText().toString(), "安装")) {
                    pb.setVisibility(View.VISIBLE);
                    bt_update.setVisibility(View.GONE);
                    bt_cancle.setVisibility(View.GONE);
                    final DownFile downFile = new DownFile(updateInfo.direct_install_url, "apk");
                    downFile.setOnDownFileListener(new DownFile.OnDownFileListener() {
                        @Override
                        public void err(String e) {
                            bt_update.post(new Runnable() {
                                @Override
                                public void run() {
                                    bt_update.setVisibility(View.VISIBLE);
                                    if (isMust) {
                                        bt_update.setText("立即更新(否则影响使用)");
                                    } else {
                                        bt_update.setText("现在更新");
                                        bt_cancle.setVisibility(View.VISIBLE);
                                    }
                                    pb.setVisibility(View.GONE);
                                    MyToast.showToast("更新出错,请重试！");
                                }
                            });

                        }

                        @Override
                        public void downOk(final String absolutePath) {
                            bt_update.post(new Runnable() {
                                @Override
                                public void run() {
                                    pb.setVisibility(View.GONE);
                                    if (!isMust) {
                                        bt_cancle.setVisibility(View.VISIBLE);
                                    }
                                    bt_update.setVisibility(View.VISIBLE);
                                    bt_update.setText("安装");
                                    installPath = absolutePath;
                                }
                            });
                        }

                        @Override
                        public void progress(long currenSize) {
                            pb.setProgress((int) currenSize);
                        }

                        @Override
                        public void size(long size) {
                            pb.setMax((int) size);
                        }
                    });


                    new Thread() {
                        @Override
                        public void run() {
                            downFile.downLoadFile();
                        }
                    }.start();
                } else {
                    Util.install(installPath);
                }


            }
        });
        return view;
    }


    public interface OnUpdateApkListener {

        void cancle(int code);

        void updateOk();

        void currentIsNew();

        void finish();

        boolean needUpdate(int code);
    }

    public void setOnUpdateApkListener(OnUpdateApkListener onUpdateApkListener) {
        this.onUpdateApkListener = onUpdateApkListener;
    }

}
