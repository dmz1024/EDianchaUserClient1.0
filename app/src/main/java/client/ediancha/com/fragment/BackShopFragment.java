package client.ediancha.com.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import java.util.List;
import java.util.Map;

import client.ediancha.com.activity.BackShopInfoActivity;
import client.ediancha.com.adapter.ApplyOrderAdapter;
import client.ediancha.com.adapter.BackShopAdapter;
import client.ediancha.com.base.ListNetWorkBaseFragment;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.ApplyOrder;
import client.ediancha.com.entity.BackShop;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class BackShopFragment extends ListNetWorkBaseFragment<BackShop.Data, BackShop> {


    @Override
    protected RecyclerView.Adapter getAdapter(List<BackShop.Data> totalList) {
        isP = true;
        return new BackShopAdapter(getContext(), totalList) {
            @Override
            protected void desc(int layoutPosition) {
                Intent intent = new Intent(getContext(), BackShopInfoActivity.class);
                intent.putExtra("id", list.get(layoutPosition).id);
                startActivityForResult(intent, Constant.BACK_SHOP_INFO);
            }
        };
    }

    @Override
    protected String getUrl() {
        return "app.php";
    }

    @Override
    protected Map<String, String> getMap() {
        map.put("c", "myreturn");
        map.put("a", "all");
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);
        map.put("status", "");
        return map;
    }

    @Override
    protected Class<BackShop> getTClass() {
        return BackShop.class;
    }


    @Override
    protected RecyclerView.ItemDecoration getDividerItemDecoration() {
        return new ItemDecoration(getContext(), LinearLayoutManager.VERTICAL, 25, "#fbfbfb");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == Constant.BACK_SHOP_INFO) {
            onRefresh();
        }
    }
}
