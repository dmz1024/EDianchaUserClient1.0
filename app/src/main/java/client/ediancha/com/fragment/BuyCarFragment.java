package client.ediancha.com.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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
    @Override
    protected RecyclerView.Adapter getAdapter(List<BuyCar.Shop> totalList) {
        if (onHavePrice != null) {
            onHavePrice.price(getPrice());
        }
        return new BuyCarShopAdapter(getContext(), totalList) {
            @Override
            protected void dataChange() {
                if (onHavePrice != null) {
                    onHavePrice.price(getPrice());
                }
            }
        };
    }

    private double getPrice() {
        double price = 0;
        for (int i = 0; i < totalList.size(); i++) {
            BuyCar.Shop shop = totalList.get(i);
            price = price + (shop.pro_num * shop.price);
        }
        return price;
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
        int count = totalList.size();
        for (int i = 0; i < count; i++) {
            BuyCar.Shop shop = totalList.get(i);
            sb.append(shop.pigcms_id);
            if (i + 1 != count) {
                sb.append(",");
            }
        }

        return sb.toString();
    }
}
