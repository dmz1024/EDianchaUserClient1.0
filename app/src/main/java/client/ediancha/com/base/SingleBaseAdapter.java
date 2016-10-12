package client.ediancha.com.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by dengmingzhi on 16/10/11.
 */

public abstract class SingleBaseAdapter<D> extends RecyclerView.Adapter<BaseViewHolder> {
    public List<D> list;
    public Context ctx;

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


}
