package client.ediancha.com.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import java.util.List;
import java.util.Map;

import client.ediancha.com.activity.AddCommentActivity;
import client.ediancha.com.activity.BuyTeaActivity;
import client.ediancha.com.adapter.TeaOrderAdapter;
import client.ediancha.com.base.ListNetWorkBaseFragment;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.entity.TeaOrder;

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
    }

    private int layoutPosition;

    @Override
    protected RecyclerView.Adapter getAdapter(final List<TeaOrder.Data> totalList) {
        return new TeaOrderAdapter(getContext(), totalList) {
            @Override
            protected void pingjia(int layoutPosition) {
                TeaOrderFragment.this.layoutPosition = layoutPosition;
                Intent intent = new Intent(ctx, AddCommentActivity.class);
                intent.putExtra("id", totalList.get(layoutPosition).order_id);
                intent.putExtra("type", "PRODUCT");
                startActivityForResult(intent, Constant.TEA_ORDER_SPACECOMMENT);
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
            if (requestCode == Constant.TEA_ORDER_SPACECOMMENT || requestCode == Constant.TEA_ORDER_PAY) {
                ((TeaOrderAdapter) mAdapter).ChangeRemove(layoutPosition);
            }

        }
    }
}
