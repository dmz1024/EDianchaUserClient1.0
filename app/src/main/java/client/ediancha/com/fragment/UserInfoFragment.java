package client.ediancha.com.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.activity.LoginActivity;
import client.ediancha.com.activity.MainActivity;
import client.ediancha.com.api.MyRetrofitUtil;
import client.ediancha.com.base.BaseFragment;
import client.ediancha.com.base.NetworkBaseFragment;
import client.ediancha.com.base.SingleNetWorkBaseFragment;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.entity.BaseEntity;
import client.ediancha.com.entity.UserInfo;
import client.ediancha.com.myview.GlideCircleTransform;
import client.ediancha.com.myview.ListViewAlert;
import client.ediancha.com.myview.TitleRelativeLayout;
import client.ediancha.com.util.MyToast;
import client.ediancha.com.util.SharedPreferenUtil;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 16/10/11.
 */

public class UserInfoFragment extends SingleNetWorkBaseFragment<UserInfo> {
    TitleRelativeLayout trl_head;
    TitleRelativeLayout trl_nick_name;
    TitleRelativeLayout trl_tel;
    TitleRelativeLayout trl_set_password;
    TitleRelativeLayout trl_content;
    TitleRelativeLayout trl_sex;
    ImageView iv_head;

    @Override
    protected void writeData(UserInfo t) {
        Glide.with(getContext()).load(t.data.avatar).bitmapTransform(new GlideCircleTransform(getContext())).into(iv_head);
        trl_nick_name.setContent(t.data.nickname);
        trl_tel.setContent(t.data.phone);
        trl_content.setContent(TextUtils.isEmpty(t.data.intro) ? "未设置" : t.data.intro);
        trl_sex.setContent(t.data.sex == 1 ? "男" : (t.data.sex == 2 ? "女" : "未设置"));

    }

    @Override
    protected String getUrl() {
        return "app.php";
    }

    @Override
    protected Map<String, String> getMap() {
        map.put("uid", client.ediancha.com.constant.UserInfo.uid);
        map.put("token", client.ediancha.com.constant.UserInfo.token);
        map.put("c", "home");
        map.put("a", "index");
        return map;
    }

    @Override
    protected ShowCurrentViewENUM getDefaultView() {
        return ShowCurrentViewENUM.VIEW_HAVE_DATA;
    }

    @Override
    protected Class<UserInfo> getTClass() {
        return UserInfo.class;
    }

    @Override
    protected View getHaveDataView() {
        View view = View.inflate(getContext(), R.layout.fragment_userinfo, null);
        trl_head = (TitleRelativeLayout) view.findViewById(R.id.trl_head);
        trl_nick_name = (TitleRelativeLayout) view.findViewById(R.id.trl_nick_name);
        trl_tel = (TitleRelativeLayout) view.findViewById(R.id.trl_tel);
        trl_set_password = (TitleRelativeLayout) view.findViewById(R.id.trl_set_password);
        trl_content = (TitleRelativeLayout) view.findViewById(R.id.trl_content);
        trl_sex = (TitleRelativeLayout) view.findViewById(R.id.trl_sex);
        iv_head = (ImageView) view.findViewById(R.id.iv_head);
        Button bt_exit = (Button) view.findViewById(R.id.bt_exit);
        bt_exit.setOnClickListener(this);
        trl_head.setOnClickListener(this);
        trl_nick_name.setOnClickListener(this);
        trl_tel.setOnClickListener(this);
        trl_set_password.setOnClickListener(this);
        trl_sex.setOnClickListener(this);
        trl_content.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (!result && view.getId() != R.id.bt_exit) {
            return;
        }

        final Map<String, String> map = new HashMap<>();
        switch (view.getId()) {
            case R.id.bt_exit:
                exit();
                break;
            case R.id.trl_head:
                map.put("avatar", Constant.IMAGE);
                changeInfo(map);
                break;
            case R.id.trl_nick_name:
                map.put("nickname", "邓如果");
                changeInfo(map);
                break;
            case R.id.trl_sex:
                new ListViewAlert(getContext(), "性别") {
                    @Override
                    public void select(int position) {
                        map.put("sex", (position + 1) + "");
                        changeInfo(map);
                    }
                }.SingleSelection(new String[]{"男", "女"});
                break;
            case R.id.trl_content:
                map.put("intro", "小李丑的不能看");
                changeInfo(map);
                break;
            case R.id.trl_set_password:

                break;
        }
    }

    /**
     * 选择性别
     *
     * @param position
     */
    private void changeSex(int position) {

    }

    /**
     * 退出登录
     */
    private void exit() {
        new SharedPreferenUtil(getContext(), "userInfo").
                setData(new String[]{"newuser", "", "sign", "", "type", "", "time", "", "uid", ""});
        Util.setUserInfo(getContext());

        startActivity(new Intent(getContext(), MainActivity.class));
    }


    private void changeInfo(Map<String, String> map) {
        map.put("c", "home");
        map.put("a", "profile");
        map.put("action", "content");
        map.put("uid", client.ediancha.com.constant.UserInfo.uid);
        map.put("token", client.ediancha.com.constant.UserInfo.token);

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
                    getData();
                } else {
                    MyToast.showToast(baseEntity.msg);
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
        }, "正在提交修改...", getContext());

    }

}
