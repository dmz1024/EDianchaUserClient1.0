package client.ediancha.com.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dengmingzhi on 16/5/10.
 */
public class SharedPreferenUtil {
    private Context ctx;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public SharedPreferenUtil(Context ctx, String title) {
        this.ctx = ctx;
        sp = ctx.getSharedPreferences(title, Context.MODE_PRIVATE);
        editor = sp.edit();

    }

    public SharedPreferenUtil(Context ctx) {
        this.ctx = ctx;
        sp = ctx.getSharedPreferences("setting", Context.MODE_PRIVATE);
        editor = sp.edit();

    }

    public void setData(String[] strings) {
        for (int i = 0; i < strings.length; i = i + 2) {
            editor.putString(strings[i], strings[i + 1]);
        }
        editor.commit();
    }

    public void setData(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void setData(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public void setData(String msg, boolean type) {
        editor.putBoolean(msg, type);
        editor.commit();
    }

    public boolean getData(String msg) {
        return sp.getBoolean(msg, false);
    }

    public Map<String, String> getData(String[] strings) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < strings.length; i++) {
            String key = strings[i];
            String value = sp.getString(key, "");
            map.put(key, value);
        }
        return map;
    }

    public String getString(String key) {
        return sp.getString(key, "");
    }

    public int getInt(String key) {
        return sp.getInt(key, -1);
    }
}
