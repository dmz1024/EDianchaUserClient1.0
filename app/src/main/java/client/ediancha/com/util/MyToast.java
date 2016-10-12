package client.ediancha.com.util;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.widget.Toast;

import client.ediancha.com.base.MyApplication;


/**
 * 自定义的Toast,弹出新的toast时会将前面的cancle掉,避免toast叠加显示
 */
public class MyToast {
    private static Toast mToast;
    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
        }
    };

    public static void showToast(String text) {
        show(text);
        mHandler.postDelayed(r, 1800);
        mToast.show();
    }

    public static void showToast(Context ctx, String text) {
        show(ctx, text);
        mHandler.postDelayed(r, 1800);
        mToast.show();
    }


    public static void showToast(String text, int time) {
        show(text);
        mHandler.postDelayed(r, time);
        mToast.show();
    }

    private static void show(String text) {
        mHandler.removeCallbacks(r);

        if (mToast != null) {
            mToast.setText(text);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast = Toast.makeText(MyApplication.app(), text, Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        }
    }

    private static void show(Context context, String text) {
        mHandler.removeCallbacks(r);

        if (mToast != null) {
            mToast.setText(text);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        }
    }
}

