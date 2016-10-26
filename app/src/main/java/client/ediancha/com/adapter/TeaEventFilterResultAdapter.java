package client.ediancha.com.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.entity.TeaFilter;
import client.ediancha.com.interfaces.OnTeaEventFilterResultChangeListener;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaEventFilterResultAdapter extends SingleBaseAdapter<TeaFilter.Cat> {
    private OnTeaEventFilterResultChangeListener onTeaEventFilterResultChangeListener;

    public TeaEventFilterResultAdapter(Context ctx, List<TeaFilter.Cat> list) {
        super(ctx, list);
    }

    public TeaEventFilterResultAdapter(List<TeaFilter.Cat> list) {
        super(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TeaEventFileterResultViewHolder(View.inflate(ctx, R.layout.item_tea_event_filter_result, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        TeaEventFileterResultViewHolder mHolder = (TeaEventFileterResultViewHolder) holder;
        TeaFilter.Cat cat = list.get(position);
        mHolder.tv_name.setText(cat.name);

    }

    public class TeaEventFileterResultViewHolder extends BaseViewHolder {
        public TextView tv_name;
        public View view_delete;

        public TeaEventFileterResultViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            view_delete = itemView.findViewById(R.id.view_delete);
            view_delete.setOnClickListener(this);
        }

        @Override
        protected void itemOnclick(int layoutPosition) {
            list.remove(layoutPosition);
            notifyItemRemoved(layoutPosition);
            notifyItemRangeChanged(layoutPosition, list.size() - 1 - layoutPosition);
            if (onTeaEventFilterResultChangeListener != null) {
                onTeaEventFilterResultChangeListener.change(list.size(), layoutPosition);
            }
        }

        @Override
        protected boolean isCanOnclick() {
            return false;
        }
    }


    public void setOnTeaEventFilterResultChangeListener(OnTeaEventFilterResultChangeListener onTeaEventFilterResultChangeListener) {
        this.onTeaEventFilterResultChangeListener = onTeaEventFilterResultChangeListener;
    }
}
