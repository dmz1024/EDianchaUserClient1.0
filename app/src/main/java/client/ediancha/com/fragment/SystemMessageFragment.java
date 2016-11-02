package client.ediancha.com.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;
import java.util.Map;
import client.ediancha.com.adapter.SystemMessageAdapter;
import client.ediancha.com.base.ListNetWorkBaseFragment;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.Address;
import client.ediancha.com.entity.SystemMessage;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class SystemMessageFragment extends ListNetWorkBaseFragment<SystemMessage.Data, SystemMessage> {


    @Override
    protected RecyclerView.Adapter getAdapter(List<SystemMessage.Data> totalList) {
        return new SystemMessageAdapter(getContext(), totalList);
    }

    @Override
    protected String getUrl() {
        return "app.php";
    }

    @Override
    protected Map<String, String> getMap() {
        map.put("a", "send_list");
        map.put("c", "home");
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);
        return map;
    }


    @Override
    protected Class<SystemMessage> getTClass() {
        return SystemMessage.class;
    }


    @Override
    protected RecyclerView.ItemDecoration getDividerItemDecoration() {
        return new ItemDecoration(getContext(), LinearLayoutManager.VERTICAL, 2, "#e1e1e1");
    }
}
