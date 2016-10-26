package client.ediancha.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.adapter.EvaluateAdapter;
import client.ediancha.com.base.BaseFragment;
import client.ediancha.com.entity.TeaSpaceDesc;

/**
 * Created by dengmingzhi on 2016/10/25.
 */

public class CommentFragment extends Fragment {
    public List<TeaSpaceDesc.Comment> list=new ArrayList<>();
    private EvaluateAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=View.inflate(getContext(),R.layout.item_recyclerview,null);
        RecyclerView rv_content= (RecyclerView) view.findViewById(R.id.rv_content);
        LinearLayoutManager manager=new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mAdapter=new EvaluateAdapter(getContext(),list);

        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setList(List<TeaSpaceDesc.Comment> list){
        this.list.addAll(list);
//        mAdapter.notifyDataSetChanged();
    }
}
