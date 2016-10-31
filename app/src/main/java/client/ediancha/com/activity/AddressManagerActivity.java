package client.ediancha.com.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.base.ToolBarActivity;
import client.ediancha.com.entity.Address;
import client.ediancha.com.entity.Citys;
import client.ediancha.com.myview.ChooseCityView;
import client.ediancha.com.myview.TextImage;
import client.ediancha.com.myview.TitleRelativeLayout;
import client.ediancha.com.processor.AddressUtil;
import client.ediancha.com.util.MyToast;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 2016/10/25.
 */

public class AddressManagerActivity extends ToolBarActivity {
    private Address.Data data;
    private Button bt_save;
    private EditText et_name;
    private EditText et_tel;
    private EditText et_address;
    private TextImage tv_set;
    private TitleRelativeLayout ct_area;
    private TextView tv_delete;

    private String province;
    private String city;
    private String area;
    private int isDefault = 0;
    private Citys citys;

    @Override
    protected String getToolBarTitle() {
        return data == null ? "添加新地址" : "编辑地址";
    }

    @Override
    protected void initView() {
        bt_save = (Button) findViewById(R.id.bt_save);
        et_name = (EditText) findViewById(R.id.et_name);
        et_tel = (EditText) findViewById(R.id.et_tel);
        et_address = (EditText) findViewById(R.id.et_address);
        tv_set = (TextImage) findViewById(R.id.tv_set);
        ct_area = (TitleRelativeLayout) findViewById(R.id.ct_area);
        tv_delete = (TextView) findViewById(R.id.tv_delete);

        bt_save.setOnClickListener(this);
        ct_area.setOnClickListener(this);
        tv_set.setOnClickListener(this);
        tv_delete.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        data = (Address.Data) getIntent().getSerializableExtra("data");
        if (data != null) {
            et_name.setText(data.name);
            et_address.setText(data.address);
            tv_set.setVisibility(data.is_default == 1 ? View.GONE : View.VISIBLE);
            isDefault = data.is_default;
            province = data.province;
            city = data.city;
            area = data.area;
            ct_area.setContent(data.province_txt + " " + data.city_txt + " " + data.area_txt);
            et_tel.setText(data.tel);
        } else {
            tv_set.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_save:
                save();
                break;
            case R.id.ct_area:
                area();
                break;
            case R.id.tv_set:
                set();
                break;
            case R.id.tv_delete:
                delete();
                break;
        }
    }


    /**
     * 删除地址
     */
    private void delete() {

    }

    /**
     * 设置地址是否默认
     */
    private void set() {
        if (data == null) {
            if (isDefault == 1) {
                isDefault = 0;
                tv_set.setDrawable(R.mipmap.icon_a_select);
            } else {
                isDefault = 1;
                tv_set.setDrawable(R.mipmap.icon_a_selected);
            }

        } else {
            AddressUtil.getInstance().setContext(this).setOnAddressManagerListener(new AddressUtil.OnAddressManagerListener() {
                @Override
                public void ok() {
                    setResult(1, getIntent());
                    finish();
                }

                @Override
                public void faile(String msg) {
                    MyToast.showToast(msg);
                }
            }).deleteOrDefault("set_default", data.address_id, "设置默认地址");
        }
    }

    /**
     * 选择地区
     */
    private void area() {
        List<String> data = new ArrayList<>();
        if (citys == null) {
            citys = new Gson().fromJson(Util.getJsonDataFromAssets(this, "city.json"), Citys.class);
        }

        new ChooseCityView(this, citys.data) {
            @Override
            protected void select(String... select) {
                ct_area.setContent(select[0] + " " + select[2] + " " + select[4]);
                province = select[1];
                city = select[3];
                area = select[5];
            }
        }.showAtLocation();


    }

    /**
     * 编辑添加地址
     */
    private void save() {
        String name = et_name.getText().toString();
        if(TextUtils.isEmpty(name)){
            MyToast.showToast("请输入收货人姓名");
            return;
        }
        String tel = et_tel.getText().toString();
        if(tel.length()!=11){
            MyToast.showToast("请输入正确的手机号");
            return;
        }
        if(TextUtils.isEmpty(province)){
            MyToast.showToast("请输入所在地区");
            return;
        }

        String address = et_address.getText().toString();
        if(TextUtils.isEmpty(address)){
            MyToast.showToast("请输入详细地址");
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("tel", tel);
        map.put("province", province);
        map.put("city", city);
        map.put("area", area);
        map.put("default", isDefault + "");
        map.put("address", address);
        if (data != null) {
            map.put("address_id", data.address_id);
        }

        AddressUtil.getInstance().setContext(this).setOnAddressManagerListener(new AddressUtil.OnAddressManagerListener() {
            @Override
            public void ok() {
                setResult(1, getIntent());
                finish();
            }

            @Override
            public void faile(String msg) {
                MyToast.showToast(msg);
            }
        }).save(map);
    }


    @Override
    protected int getRid() {
        return R.layout.activity_address_manager;
    }
}
