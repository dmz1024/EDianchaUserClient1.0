package client.ediancha.com.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.api.RetrofitUtil;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.entity.BaseEntity;
import client.ediancha.com.entity.UserInfo;
import client.ediancha.com.util.MyToast;
import client.ediancha.com.util.SharedPreferenUtil;
import client.ediancha.com.util.Util;

public class CheckLoginActivity extends ToolBarActivity {
    private TextView tv_tips;
    private Button bt_again;
    private Button bt_login;
    private EditText et_code;
    private int codeTime;


    @Override
    protected String getToolBarTitle() {
        return "验证登录";
    }

    @Override
    protected void initView() {
        tv_tips = (TextView) findViewById(R.id.tv_tips);
        bt_again = (Button) findViewById(R.id.bt_again);
        bt_login = (Button) findViewById(R.id.bt_login);
        et_code = (EditText) findViewById(R.id.et_code);
        bt_again.setOnClickListener(this);
        bt_login.setOnClickListener(this);

        et_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_code.getText().toString().length() == 4) {
                    bt_login.setEnabled(true);
                    bt_login.setAlpha(1.0f);
                } else {
                    bt_login.setEnabled(false);
                    bt_login.setAlpha(0.2f);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        tv_tips.setText("我们已发送验证码短信到这个号码" + intent.getStringExtra("tel"));
        checkCanGetCode();
    }

    /**
     * 判断是否能够获取验证码
     */
    private void checkCanGetCode() {
        codeTime = new SharedPreferenUtil(this, "getLoginCode").getInt("loginCode");
        if (codeTime > 0) {
            bt_again.setEnabled(false);
            bt_again.setAlpha(0.5f);
            bt_again.setText("重新获取(" + codeTime + ")");
            mHandler.sendEmptyMessageDelayed(codeTime, 1000);
        } else {
            bt_again.setEnabled(true);
            bt_again.setAlpha(1f);
            bt_again.setText("重新获取");
        }

    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what > 0) {
                codeTime = msg.what - 1;
                bt_again.setEnabled(false);
                bt_again.setAlpha(0.5f);
                bt_again.setText("重新获取(" + codeTime + ")");
                sendEmptyMessageDelayed(codeTime, 1000);
            } else {
                bt_again.setEnabled(true);
                bt_again.setAlpha(1f);
                bt_again.setText("重新获取");
                new SharedPreferenUtil(CheckLoginActivity.this, "getLoginCode").setData("loginCode", 0);
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        new SharedPreferenUtil(this, "getLoginCode").setData("loginCode", codeTime);
        mHandler = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_again:
                again();
                break;
            case R.id.bt_login:
                login();
                break;
        }
    }

    private void again() {
        Map<String, String> map = new HashMap<>();
        map.put("c", "users");
        map.put("a", "get_sms");
        map.put("mobile", getIntent().getStringExtra("tel"));
        RetrofitUtil.getInstance().get("app.php", map, BaseEntity.class, new RetrofitUtil.OnRequestListener<BaseEntity>() {
            @Override
            public void noNetwork() {

            }

            @Override
            public void serverErr() {

            }

            @Override
            public void haveData(BaseEntity baseEntity) {
                if (baseEntity.result == 0) {
                    codeTime = 60;
                    mHandler.sendEmptyMessage(codeTime);

                }
                MyToast.showToast(baseEntity.msg);
            }


            @Override
            public void onCompleted() {

            }
        });
    }


    private void login() {
        Map<String, String> map = new HashMap<>();
        map.put("c", "users");
        map.put("a", "sms_login");
        map.put("mobile", getIntent().getStringExtra("tel"));
        map.put("code", et_code.getText().toString());
        RetrofitUtil.getInstance().get("app.php", map, UserInfo.class, new RetrofitUtil.OnRequestListener<UserInfo>() {
            @Override
            public void noNetwork() {

            }

            @Override
            public void serverErr() {

            }

            @Override
            public void haveData(UserInfo userInfo) {
                if (userInfo.result == 0) {
                    MyToast.showToast("登录成功");
                    new SharedPreferenUtil(CheckLoginActivity.this, "userInfo").
                            setData(new String[]{"newuser", userInfo.newuser, "sign", userInfo.sign, "type", userInfo.type, "time", userInfo.time, "uid", userInfo.uid});
                } else {
                    MyToast.showToast(userInfo.msg);
                }


            }


            @Override
            public void onCompleted() {

            }


        });
    }

    @Override
    protected int getRid() {
        return R.layout.activity_check_login;
    }
}
