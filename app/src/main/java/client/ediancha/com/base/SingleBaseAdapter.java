package client.ediancha.com.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import client.ediancha.com.interfaces.OnDataCountListener;

/**
 * Created by dengmingzhi on 16/10/11.
 */

public abstract class SingleBaseAdapter<D> extends RecyclerView.Adapter<BaseViewHolder> {
    public List<D> list;
    public Context ctx;
    private OnDataCountListener onDataCountListener;

    public SingleBaseAdapter(Context ctx, List<D> list) {
        this(list);
        this.ctx = ctx;
    }

    public SingleBaseAdapter(List<D> list) {
        this.list = list;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    protected void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size() - 1 - position);
        if (onDataCountListener != null) {
            if (list.size() == 0) {
                onDataCountListener.noData();
            }
            onDataCountListener.dataCount(list.size());
        }

    }

    public void setOnDataCountListener(OnDataCountListener onDataCountListener) {
        this.onDataCountListener = onDataCountListener;
    }

}
