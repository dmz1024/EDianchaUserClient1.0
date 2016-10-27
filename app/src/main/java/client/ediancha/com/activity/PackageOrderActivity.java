package client.ediancha.com.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.api.MyRetrofitUtil;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.entity.BaseEntity;
import client.ediancha.com.myview.ChooseStringView;
import client.ediancha.com.myview.PopMessageTips;
import client.ediancha.com.myview.TitleRelativeLayout;
import client.ediancha.com.util.MyToast;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 2016/10/27.
 */

public class PackageOrderActivity extends ToolBarActivity {
    private EditText et_name;
    private EditText et_tel;
    private EditText et_reshu;
    private TitleRelativeLayout trl_come_time;
    private TitleRelativeLayout trl_time;
    private TitleRelativeLayout trl_food;
    private Button bt_summit;
    private int time = 0;
    private String comeTime;
    private int food = 0;
    private TimePickerView pvTime;

    @Override
    protected String getToolBarTitle() {
        return "包厢预订";
    }

    @Override
    protected void initView() {
        bt_summit = (Button) findViewById(R.id.bt_summit);
        et_name = (EditText) findViewById(R.id.et_name);
        et_tel = (EditText) findViewById(R.id.et_tel);
        et_reshu = (EditText) findViewById(R.id.et_reshu);
        trl_come_time = (TitleRelativeLayout) findViewById(R.id.trl_come_time);
        trl_time = (TitleRelativeLayout) findViewById(R.id.trl_time);
        trl_food = (TitleRelativeLayout) findViewById(R.id.trl_food);

        bt_summit.setOnClickListener(this);
        trl_come_time.setOnClickListener(this);
        trl_time.setOnClickListener(this);
        trl_food.setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getRid() {
        return R.layout.activity_package_order;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_summit:
                submit();
                break;
            case R.id.trl_come_time:
                comeTime();
                break;
            case R.id.trl_time:
                time();
                break;
            case R.id.trl_food:
                food();
                break;
        }
    }

    private void food() {
        final List<String> list = new ArrayList<>();
        list.add("不点餐");
        list.add("点餐");
        new ChooseStringView(this, list) {
            @Override
            protected void itemClick(int position) {
                food = position;
                trl_food.setContent(list.get(position));

            }
        }.showAtLocation();
    }

    /**
     * 选择使用时长
     */
    private void time() {
        final List<String> list = new ArrayList<>();
        list.add("1小时");
        list.add("2小时");
        list.add("3小时");
        list.add("4小时");
        list.add("4小时以上");
        new ChooseStringView(this, list) {
            @Override
            protected void itemClick(int position) {
                time = position + 1;
                if (time > 4) {
                    time = 4;
                }
                trl_time.setContent(list.get(position));

            }
        }.showAtLocation();
    }

    /**
     * 选择到店时间
     */
    private void comeTime() {

        pvTime = new TimePickerView(this, TimePickerView.Type.ALL);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        Log.d("时间", calendar.get(Calendar.MONTH) + "--" + calendar.get(Calendar.DAY_OF_MONTH));
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        boolean isAdd = false;
        if (month == 12 && (day + 7 >= 32)) {
            isAdd = true;
        }
        pvTime.setRange(year, year + (isAdd ? 1 : 0));//要在setTime 之前才有效果哦
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                comeTime = "";
                long thisTime = date.getTime();
                long currentTime = new Date().getTime();
                if (thisTime <= currentTime) {
                    trl_come_time.setContent("预约时间小于当前时间");
                    trl_come_time.getTv_content().setTextColor(getResources().getColor(R.color.colorf00));
                } else if ((currentTime + (24 * 60 * 60 * 1000 * 7)) < thisTime) {
                    trl_come_time.setContent("预约时间超出可预约的范围(7天)");
                    trl_come_time.getTv_content().setTextColor(getResources().getColor(R.color.colorf00));
                } else {
                    comeTime = getTime(date);
                    trl_come_time.setContent(comeTime);
                    trl_come_time.getTv_content().setTextColor(getResources().getColor(R.color.color333));
                }

            }
        });

        pvTime.show();
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    private void submit() {
        String name = et_name.getText().toString();
        String tel = et_tel.getText().toString();
        String renshu = et_reshu.getText().toString();
        if (TextUtils.isEmpty(name)) {
            MyToast.showToast("请输入联系人姓名");
            return;
        }
        if (tel.length() != 11) {
            MyToast.showToast("请输入正确的手机号");
            return;
        }

        if (Integer.parseInt(renshu) < 1) {
            MyToast.showToast("请至少选择一位预订人");
            return;
        }

        if (TextUtils.equals(time + "", "0")) {
            MyToast.showToast("请选择使用时长");
            return;
        }
        if (TextUtils.equals(time + "", "0")) {
            MyToast.showToast("请选择使用时长");
            return;
        }
        if (TextUtils.isEmpty(comeTime)) {
            MyToast.showToast("请选择到店时间");
            return;
        }

        if (TextUtils.isEmpty(UserInfo.uid)) {
            MyToast.showToast("请先登录");
            Util.skip(this, LoginActivity.class);
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("c", "chaguan");
        map.put("a", "yuyue");
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);
        map.put("cz_id", getIntent().getStringExtra("cz_id"));
        map.put("physical_id", getIntent().getStringExtra("physical_id"));
        map.put("name", name);
        map.put("tel", tel);
        map.put("gotime", comeTime);
        map.put("time", time + "");
        map.put("num", renshu);
        map.put("food", "" + food);

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
                    MyToast.showToast("预订成功");
                    Util.skip(PackageOrderActivity.this, AppointmentActivity.class);
                    finish();
                } else {
                    MyToast.showToast("预订失败：" + baseEntity.msg);
                }
            }


            @Override
            public void onCompleted() {

            }

            @Override
            public void resultNo0(String s) {
                BaseEntity baseEntity = new Gson().fromJson(s, BaseEntity.class);
                if (baseEntity.msg.contains("token")) {
                    new PopMessageTips("账号信息", "账号信息已过期,请重新登录!", "去登录", "再等等") {
                        @Override
                        protected void right() {
                            super.right();
                            Util.skip(PackageOrderActivity.this, LoginActivity.class);
                        }
                    }.showView(PackageOrderActivity.this);
                }
            }

            @Override
            public void start() {

            }
        }, "正在预订包厢...", PackageOrderActivity.this);

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (pvTime.isShowing()) {
                pvTime.dismiss();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
