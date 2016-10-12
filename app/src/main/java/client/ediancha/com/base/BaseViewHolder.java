package client.ediancha.com.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by dengmingzhi on 16/10/11.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    public BaseViewHolder(View itemView) {
        super(itemView);
        if (isCanOnclick()) {
            itemView.setOnClickListener(this);
        }
        if (isCanLongOnclick()) {
            itemView.setOnLongClickListener(this);
        }
    }

    protected boolean isCanOnclick() {
        return true;
    }

    protected boolean isCanLongOnclick() {
        return false;
    }

    @Override
    public void onClick(View view) {
        onClick(getLayoutPosition());
    }


    @Override
    public boolean onLongClick(View view) {
        onLongClick(getLayoutPosition());
        return true;
    }

    protected void onLongClick(int layoutPosition) {

    }

    protected void onClick(int layoutPosition) {

    }
}
