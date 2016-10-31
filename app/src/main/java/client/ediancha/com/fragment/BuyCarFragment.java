package client.ediancha.com.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.ediancha.com.adapter.BuyCarShopAdapter;
import client.ediancha.com.base.ListNetWorkBaseFragment;
import client.ediancha.com.constant.UserInfo;
import client.ediancha.com.divider.ItemDecoration;
import client.ediancha.com.entity.BuyCar;

import static client.ediancha.com.base.NetworkBaseFragment.ShowCurrentViewENUM.VIEW_HAVE_DATA;
import static client.ediancha.com.base.NetworkBaseFragment.ShowCurrentViewENUM.VIEW_NO_DATA;

/**
 * Created by dengmingzhi on 2016/10/30.
 */

public class BuyCarFragment extends ListNetWorkBaseFragment<BuyCar.Shop, BuyCar> {
    private Map<Integer, Integer> chooseMap = new HashMap<>();

    @Override
    protected RecyclerView.Adapter getAdapter(List<BuyCar.Shop> totalList) {
        if (onHavePrice != null) {
            onHavePrice.price(0);
        }
        return new BuyCarShopAdapter(getContext(), totalList, chooseMap) {
            @Override
            protected void changeMap() {
                double price = 0;
                for (Integer key : chooseMap.keySet()) {
                    BuyCar.Shop shop = list.get(key);
                    price = price + (shop.pro_num * shop.price);
                }
                if (onHavePrice != null) {
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
    protected Class<BuyCar> getTClass() {
        return BuyCar.class;
    }


    public boolean setDelete(boolean isDelete) {
        if (mAdapter != null) {
            ((BuyCarShopAdapter) mAdapter).setDelete(isDelete);
            return true;
        }
        return false;
    }


    @Override
    protected RecyclerView.ItemDecoration getDividerItemDecoration() {
        return new ItemDecoration(getContext(), LinearLayoutManager.VERTICAL, 2, "#e1e1e1");
    }

    public void clear() {
        totalList.clear();
        mAdapter.notifyDataSetChanged();
        getCurrentView(VIEW_NO_DATA);
    }

    public interface OnHavePrice {
        void price(double price);
    }

    private OnHavePrice onHavePrice;

    public void setOnHavePrice(OnHavePrice onHavePrice) {
        this.onHavePrice = onHavePrice;
    }


    public String getPayId() {
        StringBuffer sb = new StringBuffer();
        int count = 0;
        for (Integer key : chooseMap.keySet()) {
            BuyCar.Shop shop = totalList.get(key);
            sb.append(shop.pigcms_id);
            count += 1;
            if (count != chooseMap.size()) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
}
