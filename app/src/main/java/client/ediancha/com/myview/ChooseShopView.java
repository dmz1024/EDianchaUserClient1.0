package client.ediancha.com.myview;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import client.ediancha.com.R;
import client.ediancha.com.base.PopBaseView;

/**
 * Created by dengmingzhi on 2016/11/4.
 */

public class ChooseShopView extends PopBaseView {
    private List<ChooseShop> chooseShops;

    public ChooseShopView(Context ctx, List<ChooseShop> chooseShops) {
        super(ctx);
        this.chooseShops = chooseShops;
    }

    @Override
    protected int getGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    protected View getView() {
        View view = View.inflate(ctx, R.layout.pop_choose_shop, null);
        ListView lv_content = (ListView) view.findViewById(R.id.lv_content);
        lv_content.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return chooseShops.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View itemView = View.inflate(ctx, R.layout.item_choose_shop, null);
                TextView tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                TextView tv_count = (TextView) itemView.findViewById(R.id.tv_count);
                TextView tv_price = (TextView) itemView.findViewById(R.id.tv_price);
                Button bt_pay = (Button) itemView.findViewById(R.id.bt_pay);

                final ChooseShop chooseShop = chooseShops.get(position);
                tv_name.setText(chooseShop.name);
                tv_count.setText("数量：" + chooseShop.count);
                tv_price.setText("总价：" + chooseShop.price);
                bt_pay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                        choose(chooseShop.select);

                    }
                });
                return itemView;
            }
        });
        return view;
    }


    public static class ChooseShop {
        public String name;
        public int count;
        public double price;
        public int select;
    }


    protected void choose(int position) {
    }
}
