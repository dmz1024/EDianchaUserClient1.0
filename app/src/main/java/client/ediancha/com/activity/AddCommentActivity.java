package client.ediancha.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.api.MyRetrofitUtil;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.entity.BaseEntity;
import client.ediancha.com.interfaces.SingleTextWatcher;
import client.ediancha.com.myview.MyRatingBar;
import client.ediancha.com.myview.PopMessageTips;
import client.ediancha.com.util.MyToast;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 2016/10/24.
 */

public class AddCommentActivity extends ToolBarActivity implements MyRatingBar.OnFillStarListener {
    private EditText et_content;
    private Button bt_submit;
    private MyRatingBar ratingbar;
    private TextView tv_core;
    private RecyclerView rv_image;
    private EditText et_price;

    @Override
    protected String getToolBarTitle() {
        return "评论";
    }

    @Override
    protected void initView() {
        et_content = (EditText) findViewById(R.id.et_content);
        bt_submit = (Button) findViewById(R.id.bt_submit);
        ratingbar = (MyRatingBar) findViewById(R.id.ratingbar);
        tv_core = (TextView) findViewById(R.id.tv_core);
        rv_image = (RecyclerView) findViewById(R.id.rv_image);
        et_price = (EditText) findViewById(R.id.et_price);
        bt_submit.setOnClickListener(this);
        ratingbar.setOnFillStarListener(this);

        et_content.addTextChangedListener(new SingleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(et_content.getText().toString()) || TextUtils.isEmpty(et_price.getText().toString())) {
                    bt_submit.setAlpha(0.3f);
                    bt_submit.setEnabled(false);
                } else {
                    bt_submit.setAlpha(1f);
                    bt_submit.setEnabled(true);
                }
            }
        });
        et_price.addTextChangedListener(new SingleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(et_content.getText().toString()) || TextUtils.isEmpty(et_price.getText().toString())) {
                    bt_submit.setAlpha(0.3f);
                    bt_submit.setEnabled(false);
                } else {
                    bt_submit.setAlpha(1f);
                    bt_submit.setEnabled(true);
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getRid() {
        return R.layout.activity_add_comment;
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(UserInfo.uid)) {
            MyToast.showToast("请先登录!");
            Util.skip(this, LoginActivity.class);
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("c", "appcomment");
        map.put("a", "add");
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);
        map.put("score", ratingbar.getCurrentCore() + "");
        map.put("images", Constant.IMAGE);
        map.put("data_id", getIntent().getStringExtra("id"));
        map.put("type", getIntent().getStringExtra("type"));
        map.put("content", et_content.getText().toString());
        map.put("price", et_price.getText().toString());


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
                    MyToast.showToast("评论成功,该条评论将进入审核区，审核成功即可展示!");
                    finish();
                } else {
                    if (baseEntity.msg.contains("token")) {
                        new PopMessageTips("账号信息", "账号信息已过期,请重新登录!", "去登录", "再看看") {
                            @Override
                            protected void right() {
                                super.right();
                                MyToast.showToast("请先登录");
                                Util.skip(AddCommentActivity.this, LoginActivity.class);
                            }
                        }.showView(AddCommentActivity.this);
                    } else {
                        MyToast.showToast(baseEntity.msg);
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
        }, "正在提交评论...", AddCommentActivity.this);
    }

    @Override
    public void currentFillFloat(float fill) {
        tv_core.setText((fill + "").substring(0, 3) + "分");
    }

    @Override
    public void currentFillInt(int fill) {

    }
}
