package client.ediancha.com.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import client.ediancha.com.activity.AddCommentActivity;
import client.ediancha.com.activity.BackShopActivity;
import client.ediancha.com.activity.BuyTeaActivity;
import client.ediancha.com.activity.WriteBackShopActivity;
import client.ediancha.com.adapter.TeaOrderAdapter;
import client.ediancha.com.base.ListNetWorkBaseFragment;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.entity.TeaOrder;
import client.ediancha.com.myview.ChooseTeaOrderView;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaOrderFragment extends ListNetWorkBaseFragment<TeaOrder.Data, TeaOrder> {
    public String type = "";

    public static TeaOrderFragment getInstance(String type) {
        TeaOrderFragment teaOrderFragment = new TeaOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        teaOrderFragment.setArguments(bundle);
        return teaOrderFragment;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getString("type");
        setIsP(true);
    }

    private int layoutPosition;

    @Override
    protected RecyclerView.Adapter getAdapter(final List<TeaOrder.Data> totalList) {
        return new TeaOrderAdapter(getContext(), totalList) {
            @Override
            protected void pingjia(final int layoutPosition) {

                final List<TeaOrder.OrderProduct> list = new ArrayList<>();
                List<TeaOrder.OrderProduct> order_product_list = totalList.get(layoutPosition).order_product_list;
                for (int i = 0; i < order_product_list.size(); i++) {
                    if (order_product_list.get(i).is_comment == 0) {
                        list.add(order_product_list.get(i));
                    }
                }

                new ChooseTeaOrderView(getContext(), list, 1) {
                    @Override
                    protected void choose(int position) {
                        TeaOrderFragment.this.layoutPosition = layoutPosition;
                        Intent intent = new Intent(ctx, AddCommentActivity.class);
                        intent.putExtra("id", list.get(position).product_id);
                        intent.putExtra("type", "PRODUCT");
                        intent.putExtra("pigcms_id", list.get(position).pigcms_id);
                        startActivityForResult(intent, Constant.TEA_ORDER_SPACECOMMENT);
                    }
                }.showAtLocation();


            }

            @Override
            protected void back(final int layoutPosition) {
                final List<TeaOrder.OrderProduct> list = new ArrayList<>();
                List<TeaOrder.OrderProduct> order_product_list = totalList.get(layoutPosition).order_product_list;
                for (int i = 0; i < order_product_list.size(); i++) {
                    if (order_product_list.get(i).is_return == 0) {
                        list.add(order_product_list.get(i));
                    }
                }

                new ChooseTeaOrderView(getContext(), list, 2) {
                    @Override
                    protected void choose(int position) {
                        Intent intent = new Intent(ctx, WriteBackShopActivity.class);
                        intent.putExtra("data", list.get(position));
                        intent.putExtra("sn", "YDC" + totalList.get(layoutPosition).order_no);
                        startActivityForResult(intent, Constant.TEA_ORDER_SPACECOMMENT);
                    }
                }.showAtLocation();
            }

            @Override
            protected void pay(int layoutPosition) {
                TeaOrderFragment.this.layoutPosition = layoutPosition;
                Intent intent = new Intent(ctx, BuyTeaActivity.class);
                intent.putExtra("id", "YDC" + totalList.get(layoutPosition).order_no);
                startActivityForResult(intent, Constant.TEA_ORDER_PAY);
            }
        };
    }

    @Override
    protected String getUrl() {
        return "app.php";
    }

    @Override
    protected Map<String, String> getMap() {
        map.put("a", "goods_orders");
        map.put("c", "myorder");
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);
        map.put("type", type + "");
        return map;
    }


    @Override
    protected Class<TeaOrder> getTClass() {
        return TeaOrder.class;
    }

    @Override
    protected boolean isOnlyInitOne() {
        return false;
    }

    @Override
    protected boolean isCanFirstInitData() {
        return TextUtils.equals(type, "1");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
//            if (requestCode == Constant.TEA_ORDER_SPACECOMMENT || requestCode == Constant.TEA_ORDER_PAY) {
//                ((TeaOrderAdapter) mAdapter).ChangeRemove(layoutPosition);
//            }

//            isP=true;
            onRefresh();
        }
    }
}
