package client.ediancha.com.myview;

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

import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.adapter.ChooseBuyCarAdapter;
import client.ediancha.com.base.PopBaseView;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.TeaDesc;
import client.ediancha.com.util.GlideUtil;
import client.ediancha.com.util.Util;

/**
 * Created by dengmingzhi on 2016/10/27.
 */

public class ChooseBuyCarView extends PopBaseView {
    private List<TeaDesc.Property> property;
    private List<TeaDesc.SkuList> sku_list;

    public ChooseBuyCarView(Context ctx, List<TeaDesc.Property> property, List<TeaDesc.SkuList> sku_list) {
        super(ctx);
        this.property = property;
        this.sku_list = sku_list;
    }

    @Override
    protected View getView() {

        View view = View.inflate(ctx, R.layout.pop_choose_buy_car, null);
        final TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
        final TextView tv_count = (TextView) view.findViewById(R.id.tv_count);
        final TextView tv_choose = (TextView) view.findViewById(R.id.tv_choose);
        Button bt_ok = (Button) view.findViewById(R.id.bt_ok);
        RecyclerView rv_content = (RecyclerView) view.findViewById(R.id.rv_content);
        ImageView iv_img = (ImageView) view.findViewById(R.id.iv_img);


        String pid = property.get(0).pid;
        String vid = property.get(0).values.get(0).vid;
        tv_choose.setText("已选：" + property.get(0).values.get(0).value);
        exit:
        for (int i = 0; i < sku_list.size(); i++) {
            TeaDesc.SkuList skuList = sku_list.get(i);
            String[] str = skuList.properties.split(";");
            for (int j = 0; j < str.length; j++) {
                if (TextUtils.equals(pid + ":" + vid, str[j])) {
                    tv_price.setText("￥" + skuList.price);
                    tv_count.setText("库存" + skuList.quantity + "件");
                    break exit;
                }
            }

        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
        rv_content.setLayoutManager(layoutManager);
        rv_content.setAdapter(new ChooseBuyCarAdapter(ctx, property) {
            @Override
            protected void select(int fatherPosition, int position) {
                if (position == -1) {
                    return;
                }
                String pid = property.get(fatherPosition).pid;
                String vid = property.get(fatherPosition).values.get(position).vid;
                tv_choose.setText("已选：" + property.get(fatherPosition).values.get(position).value);
                exit:
                for (int i = 0; i < sku_list.size(); i++) {
                    TeaDesc.SkuList skuList = sku_list.get(i);
                    String[] str = skuList.properties.split(";");
                    for (int j = 0; j < str.length; j++) {
                        if (TextUtils.equals(pid + ":" + vid, str[j])) {
                            tv_price.setText("￥" + skuList.price);
                            tv_count.setText("库存" + skuList.quantity + "件");
                            break exit;
                        }
                    }

                }
            }
        });

        rv_content.addItemDecoration(new ItemDecoration(ctx, LinearLayoutManager.VERTICAL, 2, "#ebebeb"));

        return view;
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
