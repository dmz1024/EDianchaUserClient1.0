package client.ediancha.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import java.util.List;
import java.util.Map;

import client.ediancha.com.adapter.AddressAdapter;
import client.ediancha.com.adapter.TeaOrderAdapter;
import client.ediancha.com.base.ListNetWorkBaseFragment;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.Address;
import client.ediancha.com.entity.TeaOrder;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class AddressFragment extends ListNetWorkBaseFragment<Address.Data, Address> {


    @Override
    protected RecyclerView.Adapter getAdapter(List<Address.Data> totalList) {
        return new AddressAdapter(getContext(), totalList) {
            @Override
            protected void onRefresh() {
                AddressFragment.this.onRefresh();
            }
        };
    }

    @Override
    protected String getUrl() {
        return "app.php";
    }

    @Override
    protected Map<String, String> getMap() {
        map.put("a", "all");
        map.put("c", "myaddress");
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);
        return map;
    }


    @Override
    protected Class<Address> getTClass() {
        return Address.class;
    }


    @Override
    protected RecyclerView.ItemDecoration getDividerItemDecoration() {
        return new ItemDecoration(getContext(), LinearLayoutManager.VERTICAL, 10, "#fbfbfb");
    }
}
