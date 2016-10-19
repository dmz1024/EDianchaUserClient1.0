package client.ediancha.com.adapter;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.entity.LikeEvent;
import client.ediancha.com.entity.TeaEvent;
import client.ediancha.com.entity.TeaEventFilter;
import client.ediancha.com.interfaces.OnTeaEventFilterResultChangeListener;

import static client.ediancha.com.R.id.tv_title;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaEventFilterResultAdapter extends SingleBaseAdapter<String> {
    private OnTeaEventFilterResultChangeListener onTeaEventFilterResultChangeListener;

    public TeaEventFilterResultAdapter(Context ctx, List<String> list) {
        super(ctx, list);
    }

    public TeaEventFilterResultAdapter(List<String> list) {
        super(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TeaEventFileterResultViewHolder(View.inflate(ctx, R.layout.item_tea_event_filter_result, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        TeaEventFileterResultViewHolder mHolder = (TeaEventFileterResultViewHolder) holder;
        mHolder.tv_name.setText(list.get(position));

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
            if(onTeaEventFilterResultChangeListener!=null){
                onTeaEventFilterResultChangeListener.change(list.size(),layoutPosition);
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
