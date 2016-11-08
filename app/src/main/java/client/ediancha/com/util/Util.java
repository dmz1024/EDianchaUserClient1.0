package client.ediancha.com.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import client.ediancha.com.activity.MainActivity;
import client.ediancha.com.base.MyApplication;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.myview.PopMessageTips;
import client.ediancha.com.processor.PopUpdateApk;

/**
 * Created by dengmingzhi on 16/7/12.
 */
public class Util {
    /**
     * 获取应用的上下文对象，尽量使用该方法获取上下文，防止context被长时间占用造成内存泄漏
     *
     * @return
     */
    public static Context getApplication() {
        return MyApplication.app();
    }


    /**
     * 获取屏幕宽
     *
     * @return
     */
    public static int getWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        dm = getApplication().getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels; // 屏幕宽（像素，如：480px）
//        int screenHeight = dm.heightPixels; // 屏幕高（像素，如：800px）
        return screenWidth;
    }

    /**
     * 获取屏幕宽高
     *
     * @return
     */
    public static int getHeight() {
        DisplayMetrics dm = new DisplayMetrics();
        dm = getApplication().getResources().getDisplayMetrics();
        int screenHeight = dm.heightPixels; // 屏幕宽（像素，如：480px）
//        int screenHeight = dm.heightPixels; // 屏幕高（像素，如：800px）
        return screenHeight;
    }

    public static String getPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }


    /**
     * 返回当前程序版本号
     */
    public static int getAppVersionCode() {
        try {
            PackageManager pm = getApplication().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getApplication().getPackageName(), 0);
            return pi.versionCode;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return 0;
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName() {
        try {
            PackageManager pm = getApplication().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getApplication().getPackageName(), 0);
            return pi.versionName;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return "";
    }


    /**
     * 检测当的网络（WLAN、3G/2G）状态
     *
     * @return true 表示网络可用
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivity = (ConnectivityManager) getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 安装apk
     */
    public static void install(String path) {
        // 核心是下面几句代码
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + path),
                "application/vnd.android.package-archive");
        getApplication().startActivity(intent);
    }


    public static int getResource(String imageName) {
        Context ctx = getApplication();
        int resId = ctx.getResources().getIdentifier(imageName, "mipmap", ctx.getPackageName());
        //如果没有在"mipmap"下找到imageName,将会返回0
        return resId;
    }


    public static boolean isHavaChar(String content, String c) {
        int count = content.length();
        boolean isResult = false;
        exit:
        for (int i = 0; i < count; i++) {
            Log.d("内容", content.substring(i, i + 1) + "--" + c);
            if (TextUtils.equals(content.substring(i, i + 1), c)) {
                isResult = true;
                break exit;
            }
        }
        Log.d("返回", isResult + "");
        return isResult;
    }

    public static boolean isHavaString(String content, String c) {
        int count = content.length();
        boolean isResult = false;
        exit:
        for (int i = 0; i < count; i++) {
            Log.d("内容", content.substring(i, i + 1) + "--" + c);
            if (TextUtils.equals(content.substring(i, i + 1), c)) {
                isResult = true;
                break exit;
            }
        }

        Log.d("返回", isResult + "");
        return isResult;
    }

    public static boolean isEmpty(String... str) {
        boolean isResult = false;
        exit:
        for (int i = 0; i < str.length; i++) {
            if (TextUtils.isEmpty(str[i])) {
                isResult = true;
                break exit;
            }
        }
        return isResult;
    }


    public static int dp2Px(float dp) {
        final float scale = getApplication().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2Dp(float px) {
        final float scale = getApplication().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }


    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    Log.i("后台", appProcess.processName);
                    return true;
                } else {
                    Log.i("前台", appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 1, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    public static Bitmap getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 80f;//这里设置高度为800f
        float ww = 48f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 10, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 33) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.PNG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 5;//每次都减少5
        }

        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public static void backgroundAlpha(Activity activity, float bgAlpha) {
        Activity act = activity;
        WindowManager.LayoutParams lp = act.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        act.getWindow().setAttributes(lp);
    }


    public static void skip(Activity activity, Class clx) {
        Intent intent = new Intent(activity, clx);
        activity.startActivity(intent);
    }


    public static void setUserInfo(Context ctx) {
        Map<String, String> userInfo = new SharedPreferenUtil(ctx, "userInfo").getData(new String[]{"newuser", "sign", "type", "time", "uid"});
        UserInfo.uid = userInfo.get("uid");
        UserInfo.token = userInfo.get("sign");
        UserInfo.newuser = userInfo.get("newuser");
        UserInfo.type = userInfo.get("type");
        UserInfo.time = userInfo.get("time");
    }


    public static String stringMD5(String pw) {
        try {

            // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // 输入的字符串转换成字节数组
            byte[] inputByteArray = pw.getBytes();
            // inputByteArray是输入字符串转换得到的字节数组
            messageDigest.update(inputByteArray);
            // 转换并返回结果，也是字节数组，包含16个元素
            byte[] resultByteArray = messageDigest.digest();
            // 字符数组转换成字符串返回
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String byteArrayToHex(byte[] byteArray) {

        // 首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray = new char[byteArray.length * 2];
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }


    /**
     * 读取Assets目录下面指定文件并返回String数据
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String getJsonDataFromAssets(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = context.getClass().getClassLoader().getResourceAsStream("assets/" + fileName);
        try {
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            String json = new String(buffer, "utf-8");
            stringBuilder = stringBuilder.append(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }


    public static void tel(Context context, String tel) {
        if (TextUtils.isEmpty(tel)) {
            MyToast.showToast("号码不能为空");
            return;
        }
        Uri uri = Uri.parse("tel:" + tel);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        context.startActivity(intent);
    }


    public static void navigation(Context ctx, double latitude, double longitude, int zoom,
                                  String addr) {
        StringBuffer sb = new StringBuffer();
        sb.append("geo:").append(latitude).append(",").append(latitude)
                .append("?").append("z=").append(zoom).append("?").append("q=")
                .append(addr);
        Uri mUri = Uri.parse(sb.toString());
        Intent mIntent = new Intent(Intent.ACTION_VIEW, mUri);
        ctx.startActivity(mIntent);
    }


    public static void checkUpdate(final Context ctx) {
        PopUpdateApk popUpdateApk = new PopUpdateApk(ctx);
        popUpdateApk.setOnUpdateApkListener(new PopUpdateApk.OnUpdateApkListener() {
            @Override
            public void cancle(final int code) {

                new PopMessageTips("提示", "下次不再提示该版本更新?", "不再提示", "再想想") {
                    @Override
                    protected void right() {
                        super.right();
                        new SharedPreferenUtil(ctx).setData(new String[]{"code", code + ""});
                    }
                };
            }

            @Override
            public void updateOk() {

            }

            @Override
            public void currentIsNew() {

            }

            @Override
            public void finish() {

            }

            @Override
            public boolean needUpdate(int code) {
                String hl_code = new SharedPreferenUtil(ctx).getString("code");
                if (TextUtils.equals(code + "", hl_code)) {
                    return false;
                }
                return true;
            }
        });
        popUpdateApk.CheckUpdate();
    }



    public static boolean CheckInstall(Context ctx,String packageName){
        boolean checkResult=false;
        try {
            PackageInfo packageInfo=ctx.getPackageManager().getPackageInfo(packageName, 0);
            if (packageInfo == null) {
                checkResult=false;
            } else {
                checkResult=true;
            }
        } catch (Exception e) {
            checkResult=false;
        }
        return checkResult;
    }


}
