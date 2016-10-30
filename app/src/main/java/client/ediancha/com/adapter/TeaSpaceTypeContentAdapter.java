package client.ediancha.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.activity.TeaEventFilterResultActivity;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.entity.TeaFilter;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaSpaceTypeContentAdapter extends SingleBaseAdapter<TeaFilter.Cat> {
    private String name;
    private String key;

    public TeaSpaceTypeContentAdapter(Context ctx, List<TeaFilter.Cat> list) {
        super(ctx, list);
    }

    public TeaSpaceTypeContentAdapter(List<TeaFilter.Cat> list) {
        super(list);
    }

    public TeaSpaceTypeContentAdapter(Context ctx, List<TeaFilter.Cat> list, String name, String key) {
        super(ctx, list);
        this.name = name;
        this.key = key;

    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TeaOrderShopViewHolder(View.inflate(ctx, R.layout.item_tea_space_type_content, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        TeaOrderShopViewHolder mHolder = (TeaOrderShopViewHolder) holder;
        TeaFilter.Cat cat = list.get(position);
        mHolder.tv_content.setText(cat.name);
    }

    public  class TeaOrderShopViewHolder extends BaseViewHolder {
        public TextView tv_content;

        public TeaOrderShopViewHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
        }

        @Override
        protected void onClick(int layoutPosition) {
            List<TeaFilter.Cat> cats = new ArrayList<>();
            TeaFilter.Cat cat = new TeaFilter.Cat();
            cat.key = list.get(layoutPosition).key;
            cat.name = list.get(layoutPosition).name;
            cat.value = list.get(layoutPosition).value;
            cats.add(cat);
            String listJson = new Gson().toJson(cats);
            Intent intent = new Intent(ctx, TeaEventFilterResultActivity.class);
            intent.putExtra("data", listJson);
            intent.putExtra("type", 2);
            ctx.startActivity(intent);
        }
    }
}
