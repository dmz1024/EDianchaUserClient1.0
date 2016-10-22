package client.ediancha.com.fragment;

import android.support.v7.widget.RecyclerView;

import java.util.List;
import java.util.Map;

import client.ediancha.com.adapter.TeaSpaceAdapter;
import client.ediancha.com.processor.ShareUtil;
import client.ediancha.com.base.ListNetWorkBaseFragment;
import client.ediancha.com.entity.TeaSpace;
import client.ediancha.com.interfaces.ShareInfoInterface;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaSpaceFragment extends ListNetWorkBaseFragment<TeaSpace.Data, TeaSpace> implements ShareInfoInterface {

    @Override
    protected RecyclerView.Adapter getAdapter(List<TeaSpace.Data> totalList) {
        return new TeaSpaceAdapter(getContext(), totalList);
    }

    @Override
    protected String getUrl() {
        return "app.php";
    }

    @Override
    protected Map<String, String> getMap() {
        map.put("c", "chaguan");
        map.put("a", "alist");
        return map;
    }


    @Override
    protected Class<TeaSpace> getTClass() {
        return TeaSpace.class;
    }

    @Override
    protected boolean isCanFirstInitData() {
        return false;
    }


    @Override
    public ShareUtil.ShareInfo getShareInfo() {
        return null;
    }
}
