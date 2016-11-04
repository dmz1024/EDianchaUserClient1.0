package client.ediancha.com.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.entity.TeaDesc;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class BuyCarAdapter extends SingleBaseAdapter<TeaDesc.Property> {
    public BuyCarAdapter(Context ctx, List<TeaDesc.Property> list) {
        super(ctx, list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChooseBuyCarViewHolder(View.inflate(ctx, R.layout.item_choose_buy_car, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        ChooseBuyCarViewHolder mHolder = (ChooseBuyCarViewHolder) holder;
        TeaDesc.Property data = list.get(position);
        mHolder.tv_title.setText(data.name);
        creatContent(mHolder.rv_content, data.values, position);
    }

    private void creatContent(RecyclerView rv_content, List<TeaDesc.Value> Value, int position) {
        LinearLayoutManager manager = new LinearLayoutManager(ctx);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);

        ChooseBuyCarContentAdapter adapter = new ChooseBuyCarContentAdapter(position, ctx, Value) {
            @Override
            protected void select(int fatherPosition, int currentPosition) {
                BuyCarAdapter.this.select(fatherPosition, currentPosition);
            }
        };
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(adapter);
    }

    public static class ChooseBuyCarViewHolder extends BaseViewHolder {
        public TextView tv_title;
        public RecyclerView rv_content;


        public ChooseBuyCarViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            rv_content = (RecyclerView) itemView.findViewById(R.id.rv_content);
        }

        @Override
        protected boolean isCanOnclick() {
            return false;
        }
    }


    protected void select(int fatherPosition, int position) {

    }


}
