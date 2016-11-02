package client.ediancha.com.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.entity.ApplyOrder;
import client.ediancha.com.entity.SystemMessage;
import client.ediancha.com.interfaces.OnResultListener;
import client.ediancha.com.processor.OrderUtil;
import client.ediancha.com.util.GlideUtil;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class SystemMessageAdapter extends SingleBaseAdapter<SystemMessage.Data> {

    public SystemMessageAdapter(Context ctx, List<SystemMessage.Data> list) {
        super(ctx, list);
    }

    public SystemMessageAdapter(List<SystemMessage.Data> list) {
        super(list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SystemMessagViewHolder(View.inflate(ctx, R.layout.item_system, null));
    }

    //3 1 2
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        SystemMessagViewHolder mHolder = (SystemMessagViewHolder) holder;
        SystemMessage.Data data = list.get(position);
        mHolder.tv_name.setText(data.time);
        mHolder.tv_content.setText(data.text);
    }

    public class SystemMessagViewHolder extends BaseViewHolder {
        public TextView tv_name;
        public TextView tv_content;


        public SystemMessagViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);

        }

    }
}
