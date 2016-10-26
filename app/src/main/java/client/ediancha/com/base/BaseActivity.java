package client.ediancha.com.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dengmingzhi on 2016/10/24.
 */

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private final static List<BaseActivity> activities = new LinkedList<>();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addActivity(this);
    }

    public static void addActivity(BaseActivity activity) {
        synchronized (activities) {
            activities.add(activity);
        }
    }

    public static void removeActivity(Activity activity) {
        synchronized (activities) {
            activities.remove(activity);
        }
    }

    public void finishAll() {
        /*
         * 复制了一份mActivities集合
		 *(预防在finish()后调用调用activity的onDestroy()方法,
		 *在activity的onDestroy()又设置了removeActivity()方法,在遍历过程中无法移除会报异常的)
		 *所有复制一份
		 */
        List<Activity> copy;
        synchronized (activities) {
            copy = new LinkedList<Activity>(activities);
        }
        for (Activity activity : copy) {
            activity.finish();
        }
        // 杀死当前的进程
//        android.os.Process.killProcess(android.os.Process.myPid());
    }

    @Override
    public void onClick(View v) {

    }
}
