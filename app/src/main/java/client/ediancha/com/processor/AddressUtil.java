package client.ediancha.com.processor;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import client.ediancha.com.api.MyRetrofitUtil;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.entity.BaseEntity;
import client.ediancha.com.util.MyToast;

/**
 * Created by dengmingzhi on 2016/10/25.
 */

public class AddressUtil {
    private Context ctx;

    public static AddressUtil getInstance() {
        return new AddressUtil();
    }

    private AddressUtil() {

    }

    public AddressUtil setContext(Context ctx) {
        this.ctx = ctx;
        return this;
    }

    public void deleteOrDefault(String a, String id, final String msg) {
        Map<String, String> map = new HashMap<>();
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);
        map.put("c", "myaddress");
        map.put("a", a);
        map.put("address_id", id);

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
                    MyToast.showToast(msg + "成功");
                    if (onAddressManagerListener != null) {
                        onAddressManagerListener.ok();
                    }
                } else {
                    if (onAddressManagerListener != null) {
                        onAddressManagerListener.faile(baseEntity.msg);
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
        }, msg + "中...", ctx);
    }


    /**
     * 保存修改地址
     * @param map
     */
    public void save(Map<String, String> map) {
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);
        map.put("c", "myaddress");
        map.put("a", "save");

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
                    MyToast.showToast("保存成功");
                    if (onAddressManagerListener != null) {
                        onAddressManagerListener.ok();
                    }
                } else {
                    if (onAddressManagerListener != null) {
                        onAddressManagerListener.faile(baseEntity.msg);
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
        }, "正在保存地址...", ctx);
    }


    public interface OnAddressManagerListener {
        void ok();
        void faile(String msg);
    }

    private OnAddressManagerListener onAddressManagerListener;

    public AddressUtil setOnAddressManagerListener(OnAddressManagerListener onAddressManagerListener) {
        this.onAddressManagerListener = onAddressManagerListener;
        return this;
    }

}
