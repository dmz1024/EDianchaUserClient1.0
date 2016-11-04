package client.ediancha.com.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.ediancha.com.activity.BuyTeaActivity;
import client.ediancha.com.adapter.BuyCarShopAdapter;
import client.ediancha.com.adapter.NewBuyCarAdapter;
import client.ediancha.com.base.ListNetWorkBaseFragment;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.BuyCar;
import client.ediancha.com.entity.NewBuyCar;
import client.ediancha.com.myview.ChooseShopView;
import client.ediancha.com.processor.BuyCarUtil;

import static client.ediancha.com.base.NetworkBaseFragment.ShowCurrentViewENUM.VIEW_NO_DATA;

/**
 * Created by dengmingzhi on 2016/10/30.
 */

public class BuyCar1Fragment extends ListNetWorkBaseFragment<NewBuyCar.Data, NewBuyCar> {
    Map<Integer, NewBuyCar.Data> chooseMap = new HashMap<>();
//    private String id;

//    public static BuyCar1Fragment getInstance(String id) {
//        Bundle bundle = new Bundle();
//        BuyCar1Fragment buyCar1Fragment = new BuyCar1Fragment();
//        bundle.putString("id", id);
//        buyCar1Fragment.setArguments(bundle);
//        return buyCar1Fragment;
//    }
//
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        id = getArguments().getString("id");
//    }

    @Override
    protected RecyclerView.Adapter getAdapter(final List<NewBuyCar.Data> totalList) {
        return new NewBuyCarAdapter(getContext(), totalList) {
            @Override
            protected void change() {
                chooseMap.clear();
                if (onHavePrice != null) {
                    double price = 0;
                    int count = totalList.size();
                    for (int i = 0; i < count; i++) {
                        List<BuyCar.Shop> cart_list = totalList.get(i).cart_list;
                        int countCart = cart_list.size();
                        for (int j = 0; j < countCart; j++) {
                            if (cart_list.get(j).isChoose) {
                                chooseMap.put(i, totalList.get(i));
                                price = price + (cart_list.get(j).price * cart_list.get(j).pro_num);
                            }
                        }
                    }

                    onHavePrice.price(price);
                }
            }
        };
    }

    @Override
    protected String getUrl() {
        return "app.php";
    }

    @Override
    protected Map<String, String> getMap() {
        map.put("c", "appcart");
        map.put("a", "cart_list");
        map.put("uid", UserInfo.uid);
        map.put("token", UserInfo.token);
        return map;
    }


    @Override
    protected Class<NewBuyCar> getTClass() {
        return NewBuyCar.class;
    }


    @Override
    protected RecyclerView.ItemDecoration getDividerItemDecoration() {
        return new ItemDecoration(getContext(), LinearLayoutManager.VERTICAL, 2, "#e1e1e1");
    }

    public void clear() {
        totalList.clear();
        mAdapter.notifyDataSetChanged();
    }

    public interface OnHavePrice {
        void price(double price);
    }

    private OnHavePrice onHavePrice;

    public void setOnHavePrice(OnHavePrice onHavePrice) {
        this.onHavePrice = onHavePrice;
    }

    @Override
    protected boolean getCanRefresh() {
        return false;
    }

    @Override
    public boolean isCanRefresh() {
        return false;
    }


    public void getPayId(int choose) {
        StringBuffer sb = new StringBuffer();
        List<BuyCar.Shop> cart_list = chooseMap.get(choose).cart_list;
        int count = cart_list.size();
        for (int i = 0; i < count; i++) {
            if (cart_list.get(i).isChoose) {
                sb.append(cart_list.get(i).pigcms_id);
                if (i != count - 1) {
                    sb.append(",");
                }
            }
        }

        BuyCarUtil.getInstance().setContext(getContext()).setOnPayListener(new BuyCarUtil.OnPayListener() {
            @Override
            public void payId(String id) {
                Intent intent = new Intent(getContext(), BuyTeaActivity.class);
                intent.putExtra("id", "YDC" + id);
                startActivity(intent);
                getActivity().finish();
                onRefresh();

            }
        }).pay(sb.toString(), chooseMap.get(choose).store_id);

    }


    public void choosePay() {
        if (chooseMap.size() > 1) {
            List<ChooseShopView.ChooseShop> chooseShops = new ArrayList<>();
            for (Integer key : chooseMap.keySet()) {
                ChooseShopView.ChooseShop chooseShop = new ChooseShopView.ChooseShop();
                NewBuyCar.Data data = chooseMap.get(key);
                int count = 0;
                double price = 0;
                List<BuyCar.Shop> cart_list = data.cart_list;
                for (int i = 0; i < cart_list.size(); i++) {
                    BuyCar.Shop shop = cart_list.get(i);
                    if (shop.isChoose) {
                        count = count + shop.pro_num;
                        price = price + (shop.pro_num * shop.price);
                    }
                }
                chooseShop.name = data.store_name;
                chooseShop.price = price;
                chooseShop.count = count;
                chooseShop.select = key;
                chooseShops.add(chooseShop);
            }

            new ChooseShopView(getContext(), chooseShops) {
                @Override
                protected void choose(int position) {
                    getPayId(position);
                }
            }.showAtLocation();
        } else if (chooseMap.size() == 1) {
            for (Integer key : chooseMap.keySet()) {
                getPayId(key);
            }
        }


    }

    @Override
    protected boolean getLoadMore() {
        return false;
    }


}
