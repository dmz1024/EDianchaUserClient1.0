package client.ediancha.com.myview;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.activity.LoginActivity;
import client.ediancha.com.adapter.ChooseBuyCarAdapter;
import client.ediancha.com.base.PopBaseView;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.TeaDesc;
import client.ediancha.com.util.GlideUtil;
import client.ediancha.com.util.MyToast;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 2016/10/27.
 */

public class ChooseBuyCarView extends PopBaseView {
    private List<TeaDesc.Property> property;
    private List<TeaDesc.SkuList> sku_list;
    private TextView tv_price;
    private TextView tv_count;
    private TextView tv_choose;
    private ImageView iv_img;
    private Map<Integer, Integer> chooseMap = new HashMap<>();
    private int count;
    private int sku_count;
    private String sku_id;
    private AddAndSub add_sub;

    public ChooseBuyCarView(Context ctx, List<TeaDesc.Property> property, List<TeaDesc.SkuList> sku_list) {
        super(ctx);
        this.property = property;
        this.sku_list = sku_list;
        count = property.size();
        sku_count = sku_list.size();
    }

    private void choose(int fatherPosition, int childPosition) {
        chooseMap.put(fatherPosition, childPosition);

        if (fatherPosition == 0) {
            GlideUtil.GlideErrAndOc(ctx, property.get(0).values.get(childPosition).image, iv_img);
        }

        if (chooseMap.size() == count) {
            StringBuffer sb = new StringBuffer("已选择:");
            StringBuffer pids = new StringBuffer();
            for (int i = 0; i < count; i++) {
                TeaDesc.Property proper = property.get(i);
                sb.append(" ").append(proper.values.get(chooseMap.get(i)).value);
                List<TeaDesc.Value> values = proper.values;
                String pid = proper.pid;
                String vid = values.get(chooseMap.get(i)).vid;
                pids.append(pid).append(":").append(vid);
                if (i + 1 != count) {
                    pids.append(";");
                }
            }
            tv_choose.setText(sb.toString());

            exit:
            for (int i = 0; i < sku_count; i++) {
                TeaDesc.SkuList skuList = sku_list.get(i);
                if (TextUtils.equals(pids.toString(), skuList.properties)) {
                    tv_price.setText("￥" + skuList.price);
                    tv_count.setText("库存" + skuList.quantity + "件");
                    sku_id = skuList.sku_id;
                    add_sub.setCount(0, skuList.quantity).setCount(add_sub.getCurrent());
                    break exit;

                }

            }
        }
    }

    @Override
    protected View getView() {

        View view = View.inflate(ctx, R.layout.pop_choose_buy_car, null);
        tv_price = (TextView) view.findViewById(R.id.tv_price);
        tv_count = (TextView) view.findViewById(R.id.tv_count);
        tv_choose = (TextView) view.findViewById(R.id.tv_choose);
        Button bt_ok = (Button) view.findViewById(R.id.bt_ok);
        RecyclerView rv_content = (RecyclerView) view.findViewById(R.id.rv_content);
        iv_img = (ImageView) view.findViewById(R.id.iv_img);
        add_sub = (AddAndSub) view.findViewById(R.id.add_sub);
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chooseMap.size() == 0) {
                    dismiss();
                } else if (add_sub.getCurrent() == 0) {
                    MyToast.showToast("请选择商品数量");
                } else {
                    if (TextUtils.isEmpty(UserInfo.uid)) {
                        MyToast.showToast("请先登录");
                        Util.skip(((Activity) ctx), LoginActivity.class);
                        return;
                    }
                    dismiss();
                    chooseOk(add_sub.getCurrent(), sku_id);
                }
            }
        });

        for (int i = 0; i < count; i++) {
            choose(i, 0);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
        rv_content.setLayoutManager(layoutManager);
        rv_content.setAdapter(new ChooseBuyCarAdapter(ctx, property) {
            @Override
            protected void select(int fatherPosition, int position) {
                choose(fatherPosition, position);
            }
        });

        rv_content.addItemDecoration(new ItemDecoration(ctx, LinearLayoutManager.VERTICAL, 2, "#ebebeb"));

        return view;
    }

    protected void chooseOk(int count, String sku_id) {

    }

    @Override
    protected int getGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    protected int height() {
        return (Util.getHeight() / 3) * 2;
    }
}
