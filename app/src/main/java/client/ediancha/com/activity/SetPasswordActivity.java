package client.ediancha.com.activity;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.api.MyRetrofitUtil;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.entity.BaseEntity;
import client.ediancha.com.interfaces.SingleTextWatcher;
import client.ediancha.com.util.MyToast;
import client.ediancha.com.util.SharedPreferenUtil;

/**
 * Created by dengmingzhi on 2016/11/2.
 */

public class SetPasswordActivity extends ToolBarActivity {
    private EditText et_tel;
    private EditText et_code;
    private EditText et_password;
    private Button bt_code;
    private Button bt_set;
    private int codeTime = 60;

    @Override
    protected String getToolBarTitle() {
        return "设置密码";
    }

    @Override
    protected void initView() {
        et_tel = (EditText) findViewById(R.id.et_tel);
        et_code = (EditText) findViewById(R.id.et_code);
        et_password = (EditText) findViewById(R.id.et_password);
        bt_code = (Button) findViewById(R.id.bt_code);
        bt_set = (Button) findViewById(R.id.bt_set);
        bt_code.setOnClickListener(this);
        bt_set.setOnClickListener(this);

        et_tel.addTextChangedListener(new SingleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (et_tel.getText().toString().length() == 11 && codeTime == 60) {
                    bt_code.setEnabled(true);
                    bt_code.setAlpha(1.0f);
                } else {
                    bt_code.setEnabled(false);
                    bt_code.setAlpha(0.2f);
                }

                if (et_tel.getText().toString().length() == 11 && et_code.getText().toString().length() == 4 && et_password.getText().toString().length() > 0) {
                    bt_set.setAlpha(1.0f);
                    bt_set.setEnabled(true);
                } else {
                    bt_set.setAlpha(0.2f);
                    bt_set.setEnabled(false);
                }

            }
        });

        et_password.addTextChangedListener(new SingleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (et_tel.getText().toString().length() == 11 && codeTime == 60) {
                    bt_code.setEnabled(true);
                    bt_code.setAlpha(1.0f);
                } else {
                    bt_code.setEnabled(false);
                    bt_code.setAlpha(0.2f);
                }

                if (et_tel.getText().toString().length() == 11 && et_code.getText().toString().length() == 4 && et_password.getText().toString().length() > 0) {
                    bt_set.setAlpha(1.0f);
                    bt_set.setEnabled(true);
                } else {
                    bt_set.setAlpha(0.2f);
                    bt_set.setEnabled(false);
                }

            }
        });

        et_code.addTextChangedListener(new SingleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (et_tel.getText().toString().length() == 11 && codeTime == 60) {
                    bt_code.setEnabled(true);
                    bt_code.setAlpha(1.0f);
                } else {
                    bt_code.setEnabled(false);
                    bt_code.setAlpha(0.2f);
                }

                if (et_tel.getText().toString().length() == 11 && et_code.getText().toString().length() == 4 && et_password.getText().toString().length() > 0) {
                    bt_set.setAlpha(1.0f);
                    bt_set.setEnabled(true);
                } else {
                    bt_set.setAlpha(0.2f);
                    bt_set.setEnabled(false);
                }

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_code:
                code();
                break;
            case R.id.bt_set:
                set();
                break;
        }
    }

    /**
     * 设置密码
     */
    private void set() {
        Map<String, String> map = new HashMap<>();
        map.put("c", "users");
        map.put("a", "set_password");
        map.put("mobile", et_tel.getText().toString());
        map.put("code", et_code.getText().toString());
        map.put("password", et_password.getText().toString());

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
                    MyToast.showToast("密码设置成功");
                    finish();
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
        }, "正在设置密码", SetPasswordActivity.this);
    }

    /**
     * 获取验证码
     */
    private void code() {
        Map<String, String> map = new HashMap<>();
        map.put("c", "users");
        map.put("a", "get_sms");
        map.put("mobile", et_tel.getText().toString());
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
                    codeTime = 60;
                    mHandler.sendEmptyMessage(codeTime);
                }
                MyToast.showToast(baseEntity.msg);
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
        }, "正在获取验证码...", SetPasswordActivity.this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getRid() {
        return R.layout.activity_set_password;
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what > 0) {
                codeTime = msg.what - 1;
                bt_code.setEnabled(false);
                bt_code.setAlpha(0.2f);
                bt_code.setText("重新获取(" + codeTime + ")");
                sendEmptyMessageDelayed(codeTime, 1000);
            } else {
                bt_code.setEnabled(true);
                bt_code.setAlpha(1f);
                bt_code.setText("获取验证码");
                codeTime = 60;
            }
        }
    };
}
