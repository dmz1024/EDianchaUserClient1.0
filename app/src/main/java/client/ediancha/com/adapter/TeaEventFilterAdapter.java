package client.ediancha.com.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.constant.Constant;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.TeaEventFilter;
import client.ediancha.com.entity.TeaOrder;
import client.ediancha.com.myview.MyViewGroup;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaEventFilterAdapter extends SingleBaseAdapter<TeaEventFilter.Data> {

    public TeaEventFilterAdapter(Context ctx, List<TeaEventFilter.Data> list) {
        super(ctx, list);
    }

    public TeaEventFilterAdapter(List<TeaEventFilter.Data> list) {
        super(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TeaEventFilterViewHolder(View.inflate(ctx, R.layout.item_tea_event_filter, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        TeaEventFilterViewHolder mHolder = (TeaEventFilterViewHolder) holder;
        TeaEventFilter.Data data = list.get(position);
        mHolder.tv_title.setText(data.title);
        creatContent(mHolder.rv_content, data.casts);
    }

    private void creatContent(RecyclerView rv_content, List<TeaEventFilter.Casts> casts) {
        GridLayoutManager manager = new GridLayoutManager(ctx, 3);
        TeaEventFilterContentAdapter adapter = new TeaEventFilterContentAdapter(ctx, casts);
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(adapter);
//        rv_content.addItemDecoration(new ItemDecoration(ctx, LinearLayout.HORIZONTAL, 10, "#fbfbfb"));

    }

    public static class TeaEventFilterViewHolder extends BaseViewHolder {
        public TextView tv_title;
        public RecyclerView rv_content;
        public View view_content;
        public TeaEventFilterViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            rv_content = (RecyclerView) itemView.findViewById(R.id.rv_content);
            view_content = itemView.findViewById(R.id.view_content);
            view_content.setOnClickListener(this);
        }

        @Override
        protected boolean isCanOnclick() {
            return false;
        }
    }
}
