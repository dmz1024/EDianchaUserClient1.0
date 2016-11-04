package client.ediancha.com.myview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.adapter.TeaOrderShopAdapter;
import client.ediancha.com.base.PopBaseView;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.TeaOrder;

/**
 * Created by dengmingzhi on 2016/11/4.
 */

public class ChooseTeaOrderView extends PopBaseView {
    private List<TeaOrder.OrderProduct> list;
    private int type;

    public ChooseTeaOrderView(Context ctx, List<TeaOrder.OrderProduct> list, int type) {
        super(ctx);
        this.list = list;
        this.type = type;
    }

    @Override
    protected int getGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    protected View getView() {
        View view = View.inflate(ctx, R.layout.pop_choose_comment, null);
        TextView tv_title= (TextView) view.findViewById(R.id.tv_title);
        RecyclerView rv_content= (RecyclerView) view.findViewById(R.id.rv_content);
        tv_title.setText(type==1?"请选择评价商品":"请选择退货商品");
        LinearLayoutManager manager = new LinearLayoutManager(ctx);
        TeaOrderShopAdapter adapter = new TeaOrderShopAdapter(ctx, list,true){
            @Override
            protected void choose(int layoutPosition) {
                dismiss();
                ChooseTeaOrderView.this.choose(layoutPosition);
            }
        };
        rv_content.addItemDecoration(new ItemDecoration(ctx, LinearLayout.VERTICAL, 1, "#ebebeb"));
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(adapter);
        return view;
    }

    protected void choose(int position){

    }
}
