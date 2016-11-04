package client.ediancha.com.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.api.MyRetrofitUtil;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.entity.BaseEntity;
import client.ediancha.com.interfaces.SingleTextWatcher;
import client.ediancha.com.util.MyToast;

/**
 * Created by dengmingzhi on 2016/11/4.
 */

public class FirstSetPasswordActivity extends ToolBarActivity {
    private TextView tv_next;
    private Button bt_set;
    private EditText et_password;

    @Override
    protected String getToolBarTitle() {
        return "设置密码";
    }

    @Override
    protected void initView() {
        tv_next = (TextView) findViewById(R.id.tv_next);
        bt_set = (Button) findViewById(R.id.bt_set);
        et_password = (EditText) findViewById(R.id.et_password);
        tv_next.setOnClickListener(this);
        bt_set.setOnClickListener(this);
        et_password.addTextChangedListener(new SingleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                if (et_password.getText().toString().length() < 6) {
                    bt_set.setEnabled(false);
                    bt_set.setAlpha(0.2f);
                } else {
                    bt_set.setEnabled(true);
                    bt_set.setAlpha(1.0f);
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getRid() {
        return R.layout.activity_first_set_password;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_set:
                setPassword();
                break;
            case R.id.tv_next:
                back();
                break;
        }
    }

    private void setPassword() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);
        map.put("c", "home");
        map.put("a", "set_password");
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
                    MyToast.showToast("密码设置成功,请不要告诉别人呦！", 3000);
                    back();
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
        }, "正在提交密码...", FirstSetPasswordActivity.this);
    }


}
