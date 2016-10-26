package client.ediancha.com.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.TeaFilter;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaEventFilterAdapter extends SingleBaseAdapter<TeaFilter.Data> {
    private Map<Integer, Boolean> map = new HashMap<>();
    private boolean isAdd;

    public TeaEventFilterAdapter(Context ctx, List<TeaFilter.Data> list) {
        super(ctx, list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TeaEventFilterViewHolder(View.inflate(ctx, R.layout.item_tea_event_filter, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        TeaEventFilterViewHolder mHolder = (TeaEventFilterViewHolder) holder;
        TeaFilter.Data data = list.get(position);
        mHolder.tv_title.setText(data.name);
        if (!map.containsKey(position)) {
            map.put(position, false);
        }

        if (TextUtils.equals("时间", data.name) && !isAdd) {
            TeaFilter.Cat cat = new TeaFilter.Cat();
            cat.key = "timefadkjhfhdafjhd";
            cat.name = "设置时间";
            cat.value = "设置时间";
            list.get(position).data.add(cat);
            isAdd = true;
        }

        if (map.get(position)) {
            creatContent(mHolder.rv_content, data.data, false, position);
        } else {
            map.put(position, true);
            creatContent(mHolder.rv_content, data.data, true, position);
        }


    }

    private void creatContent(RecyclerView rv_content, List<TeaFilter.Cat> casts, boolean show, int position) {
        GridLayoutManager manager = new GridLayoutManager(ctx, 4);
        TeaEventFilterContentAdapter adapter = new TeaEventFilterContentAdapter(position, ctx, casts) {
            @Override
            protected void select(int fatherPosition, int currentPosition, String key) {
                TeaEventFilterAdapter.this.select(fatherPosition, currentPosition,key);
            }
        };
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(adapter);
        if (show) {
            rv_content.addItemDecoration(new ItemDecoration(ctx, LinearLayout.HORIZONTAL, 10, "#fbfbfb"));
        }
    }

    public static class TeaEventFilterViewHolder extends BaseViewHolder {
        public TextView tv_title;
        public RecyclerView rv_content;


        public TeaEventFilterViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            rv_content = (RecyclerView) itemView.findViewById(R.id.rv_content);
        }

        @Override
        protected boolean isCanOnclick() {
            return false;
        }
    }


    protected void select(int fatherPosition, int currentPosition, String key) {

    }


}
