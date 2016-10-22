package client.ediancha.com.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.florent37.viewanimator.ViewAnimator;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.api.MyRetrofitUtil;
import client.ediancha.com.api.RetrofitUtil;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.entity.BaseEntity;
import client.ediancha.com.entity.UserInfo;
import client.ediancha.com.util.MyToast;
import client.ediancha.com.util.SharedPreferenUtil;
import client.ediancha.com.util.Util;

public class LoginActivity extends ToolBarActivity implements View.OnFocusChangeListener {
    private ImageView iv_sanjiao;
    private TextView tv_tel;
    private TextView tv_password;

    private RelativeLayout rl_password;
    private RelativeLayout rl_tel;
    private EditText et_password_tel;
    private EditText et_password_password;
    private EditText et_tel_tel;
    private Button bt_tel_login;
    private Button bt_login;
    private int currentType = 0;

    private RelativeLayout rl_root;

    private int req;

    @Override
    protected String getToolBarTitle() {
        return null;
    }

    @Override
    protected int getTop() {
        return 0;
    }

    @Override
    protected void initView() {
        iv_sanjiao = (ImageView) findViewById(R.id.iv_sanjiao);
        tv_tel = (TextView) findViewById(R.id.tv_tel);
        tv_password = (TextView) findViewById(R.id.tv_password);
        et_password_tel = (EditText) findViewById(R.id.et_password_tel);
        et_password_password = (EditText) findViewById(R.id.et_password_password);
        et_tel_tel = (EditText) findViewById(R.id.et_tel_tel);
        bt_tel_login = (Button) findViewById(R.id.bt_tel_login);
        bt_login = (Button) findViewById(R.id.bt_login);
        rl_root = (RelativeLayout) findViewById(R.id.rl_root);
        rl_root.setOnClickListener(this);
        rl_tel = (RelativeLayout) findViewById(R.id.rl_tel);
        rl_password = (RelativeLayout) findViewById(R.id.rl_password);
        tv_password.setOnClickListener(this);
        tv_tel.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        bt_tel_login.setOnClickListener(this);

        et_tel_tel.setOnFocusChangeListener(this);
        et_password_tel.setOnFocusChangeListener(this);
        et_password_password.setOnFocusChangeListener(this);


        ScrollView.LayoutParams layoutParams = (ScrollView.LayoutParams) rl_root.getLayoutParams();

        layoutParams.height = Util.getHeight() + 300;

        rl_root.setLayoutParams(layoutParams);

        rl_root.setFocusable(true);
        rl_root.requestFocus();
        rl_root.setFocusableInTouchMode(true);
        rl_root.findFocus();

    }


    @Override
    protected void initData() {
        et_tel_tel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_tel_tel.getText().toString().length() == 11) {
                    bt_tel_login.setEnabled(true);
                    bt_tel_login.setAlpha(1.0f);
                } else {
                    bt_tel_login.setEnabled(false);
                    bt_tel_login.setAlpha(0.2f);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_password_tel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_password_tel.getText().toString().length() == 11 && et_password_password.getText().length() > 0) {
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

        et_password_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_password_tel.getText().toString().length() == 11 && et_password_password.getText().length() > 0) {
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
    protected int getRid() {
        return R.layout.activity_login;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_tel:
                tel();
                break;
            case R.id.tv_password:
                password();
                break;
            case R.id.bt_login:
                login();
                break;
            case R.id.bt_tel_login:
                telLogin();
                break;
            case R.id.rl_root:
                rl_root.setFocusable(true);
                rl_root.requestFocus();
                rl_root.setFocusableInTouchMode(true);
                rl_root.findFocus();
                break;

        }
    }


    /**
     * 手机号登录
     */
    private void telLogin() {
        onClick(rl_root);
        if (new SharedPreferenUtil(this, "getLoginCode").getInt("loginCode") > 0) {
            bt_tel_login.setAlpha(1f);
            bt_tel_login.setEnabled(true);
            bt_tel_login.setText("登     录");
            Intent intent = new Intent(this, CheckLoginActivity.class);
            intent.putExtra("tel", et_tel_tel.getText().toString());
            startActivity(intent);
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("c", "users");
        map.put("a", "get_sms");
        map.put("mobile", et_tel_tel.getText().toString());
        MyRetrofitUtil.getInstance().get("app.php", map, BaseEntity.class, new MyRetrofitUtil.OnRequestListener<BaseEntity>() {
            @Override
            public void noNetwork() {

            }

            @Override
            public void serverErr() {

            }

            @Override
            public void haveData(BaseEntity baseEntity) {
                if (baseEntity.result == 0) {
                    new SharedPreferenUtil(LoginActivity.this, "getLoginCode").setData("loginCode", 60);
                    Intent intent = new Intent(LoginActivity.this, CheckLoginActivity.class);
                    intent.putExtra("tel", et_tel_tel.getText().toString());
                    startActivityForResult(intent, 101);
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
        }, "正在获取验证码...", LoginActivity.this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        new SharedPreferenUtil(this, "getLoginCode").setData("loginCode", 0);
    }

    /**
     * 账号和密码登录
     */
    private void login() {
        onClick(rl_root);
        Map<String, String> map = new HashMap<>();
        map.put("c", "users");
        map.put("a", "mobile_login");
        map.put("phone", et_password_tel.getText().toString());
        map.put("password", et_password_password.getText().toString());
        MyRetrofitUtil.getInstance().get("app.php", map, UserInfo.class, new MyRetrofitUtil.OnRequestListener<UserInfo>() {
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
                    new SharedPreferenUtil(LoginActivity.this, "userInfo").
                            setData(new String[]{"newuser", userInfo.data.newuser, "sign", userInfo.data.sign, "type", userInfo.data.type, "time", userInfo.data.time, "uid", userInfo.data.uid});
                    Util.setUserInfo(LoginActivity.this);
                    setResult(1,getIntent());
                    finish();
                } else {
                    MyToast.showToast(userInfo.msg);
                }

            }


            @Override
            public void onCompleted() {

            }

            @Override
            public void resultNo0(String s) {
                BaseEntity baseEntity = new Gson().fromJson(s, BaseEntity.class);
                MyToast.showToast(baseEntity.msg);
            }

            @Override
            public void start() {

            }
        }, "正在登录...", LoginActivity.this);

    }


    private void password() {
        if (currentType == 1) {
            return;
        }

        rl_root.setFocusable(true);
        rl_root.requestFocus();
        rl_root.setFocusableInTouchMode(true);
        rl_root.findFocus();

        rl_tel.setVisibility(View.GONE);
        rl_password.setVisibility(View.VISIBLE);
        ViewAnimator.animate(iv_sanjiao).translationX(0, (tv_password.getLeft() + tv_password.getWidth() / 2 - tv_tel.getLeft() - tv_tel.getWidth() / 2)).duration(200).start();
        currentType = 1;
    }

    private void tel() {
        if (currentType == 0) {
            return;
        }

        rl_root.setFocusable(true);
        rl_root.requestFocus();
        rl_root.setFocusableInTouchMode(true);
        rl_root.findFocus();


        rl_tel.setVisibility(View.VISIBLE);
        rl_password.setVisibility(View.GONE);
        ViewAnimator.animate(iv_sanjiao).translationX((tv_password.getLeft() + tv_password.getWidth() / 2 - tv_tel.getLeft() - tv_tel.getWidth() / 2), 0).duration(200).start();
        currentType = 0;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v == et_tel_tel) {
            if (hasFocus) {
                et_tel_tel.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ViewAnimator.animate(rl_root).translationY(0, -250).duration(200).start();
                    }
                }, 250);

            } else {
                ViewAnimator.animate(rl_root).translationY(-250, 0).duration(200).start();
            }
        } else if (v == et_password_tel || v == et_password_password) {
            if (hasFocus) {
                et_password_tel.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ViewAnimator.animate(rl_root).translationY(0, -250).duration(200).start();
                    }
                }, 250);

            } else {
                ViewAnimator.animate(rl_root).translationY(-250, 0).duration(200).start();
            }
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setResult(1,getIntent());
        finish();
    }
}
