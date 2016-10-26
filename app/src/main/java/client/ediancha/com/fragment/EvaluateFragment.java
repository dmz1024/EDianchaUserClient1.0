package client.ediancha.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import java.util.List;
import java.util.Map;

import client.ediancha.com.adapter.EvaluateAdapter;
import client.ediancha.com.adapter.TeaSpaceAdapter;
import client.ediancha.com.base.ListNetWorkBaseFragment;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.Comment;
import client.ediancha.com.entity.TeaEventDesc;
import client.ediancha.com.entity.TeaSpace;
import client.ediancha.com.entity.TeaSpaceDesc;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class EvaluateFragment extends ListNetWorkBaseFragment<TeaSpaceDesc.Comment, Comment> {
    private String id;
    private String type;

    public static EvaluateFragment getInstance(String id, String type) {
        EvaluateFragment evaluateFragment = new EvaluateFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putString("type", type);
        evaluateFragment.setArguments(bundle);
        return evaluateFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        id = bundle.getString("id");
        type = bundle.getString("type");
    }

    @Override
    protected RecyclerView.Adapter getAdapter(List<TeaSpaceDesc.Comment> totalList) {
        return new EvaluateAdapter(getContext(), totalList);
    }

    @Override
    protected String getUrl() {
        return "app.php";
    }

    @Override
    protected Map<String, String> getMap() {
        map.put("data_id", id);
        map.put("type", type);
        map.put("c", "appcomment");
        map.put("a", "index");
        return map;
    }


    @Override
    protected RecyclerView.ItemDecoration getDividerItemDecoration() {
        return new ItemDecoration(getContext(), LinearLayout.VERTICAL, 2, "#ebebeb");
    }

    @Override
    protected Class<Comment> getTClass() {
        return Comment.class;
    }

}
