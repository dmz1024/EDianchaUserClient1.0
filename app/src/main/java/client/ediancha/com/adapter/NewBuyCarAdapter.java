package client.ediancha.com.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.ediancha.com.R;
import client.ediancha.com.base.BaseViewHolder;
import client.ediancha.com.base.SingleBaseAdapter;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.BuyCar;
import client.ediancha.com.entity.NewBuyCar;
import client.ediancha.com.entity.TeaDesc;
import client.ediancha.com.myview.TitleRelativeLayout;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class NewBuyCarAdapter extends SingleBaseAdapter<NewBuyCar.Data> {
    private Map<Integer, Boolean> dividerMap = new HashMap<>();

    public NewBuyCarAdapter(Context ctx, List<NewBuyCar.Data> list) {
        super(ctx, list);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChooseBuyCarViewHolder(View.inflate(ctx, R.layout.item_buy_car, null));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        ChooseBuyCarViewHolder mHolder = (ChooseBuyCarViewHolder) holder;
        NewBuyCar.Data data = list.get(position);
        mHolder.trl_store_name.setTitle(data.store_name);
        if (!dividerMap.containsKey(position)) {
            dividerMap.put(position, false);
        }
        mHolder.trl_store_name.setContent(list.get(position).isDelete ? "取消" : "编辑");
        mHolder.view_choose.setBackgroundResource(data.isChoose ? R.mipmap.icon_shopcart_checked : R.mipmap.icon_shopcart_check);

        creatContent(mHolder.rv_content, data, dividerMap.get(position), position);

    }

    private void creatContent(RecyclerView rv_content, final NewBuyCar.Data data, boolean isAdd, final int position) {
        LinearLayoutManager manager = new LinearLayoutManager(ctx);

        BuyCarShopAdapter shopAdapter = new BuyCarShopAdapter(ctx, data.cart_list, data.isDelete) {
            @Override
            protected void delete() {
                if (data.cart_list.size() == 0) {
                    NewBuyCarAdapter.this.remove(position);
                } else {
                    List<BuyCar.Shop> cart_list = data.cart_list;
                    int count = cart_list.size();
                    boolean isChoose = true;
                    exit:
                    for (int i = 0; i < count; i++) {
                        if (!cart_list.get(i).isChoose) {
                            isChoose = false;
                            break exit;
                        }
                    }
                    data.isChoose = isChoose;
                }

                NewBuyCarAdapter.this.notifyItemChanged(position);
                change();

            }

            @Override
            protected void change() {
                NewBuyCarAdapter.this.change();
            }

            @Override
            protected void select() {
                List<BuyCar.Shop> cart_list = data.cart_list;
                int count = cart_list.size();
                boolean isChoose = true;
                exit:
                for (int i = 0; i < count; i++) {
                    if (!cart_list.get(i).isChoose) {
                        isChoose = false;
                        break exit;
                    }
                }
                data.isChoose = isChoose;
                NewBuyCarAdapter.this.notifyItemChanged(position);
                change();
            }
        };

        if (isAdd) {
            rv_content.addItemDecoration(new ItemDecoration(ctx, LinearLayoutManager.VERTICAL, 1, "#ebebeb"));
        }
        rv_content.setLayoutManager(manager);
        rv_content.setAdapter(shopAdapter);
    }

    public class ChooseBuyCarViewHolder extends BaseViewHolder {
        public RecyclerView rv_content;
        public View view_choose;
        public TitleRelativeLayout trl_store_name;

        public ChooseBuyCarViewHolder(View itemView) {
            super(itemView);
            view_choose = itemView.findViewById(R.id.view_choose);
            trl_store_name = (TitleRelativeLayout) itemView.findViewById(R.id.trl_store_name);
            rv_content = (RecyclerView) itemView.findViewById(R.id.rv_content);
            trl_store_name.getTv_content().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.get(getLayoutPosition()).isDelete = !list.get(getLayoutPosition()).isDelete;
                    notifyItemChanged(getLayoutPosition());
                    change();
                }
            });
            view_choose.setOnClickListener(this);
        }

        @Override
        protected void itemOnclick(int id, int layoutPosition) {
            switch (id) {
                case R.id.view_choose:
                    choose(layoutPosition);
                    break;
            }
        }

        @Override
        protected boolean isCanOnclick() {
            return false;
        }
    }


    protected void choose(int layoutPosition) {
        boolean isChoose = list.get(layoutPosition).isChoose;
        List<BuyCar.Shop> cart_list = list.get(layoutPosition).cart_list;
        int count = cart_list.size();
        for (int i = 0; i < count; i++) {
            cart_list.get(i).isChoose = !isChoose;
        }
        list.get(layoutPosition).isChoose = !isChoose;
        notifyItemChanged(layoutPosition);
        change();
    }


    protected void change() {

    }

}
