package client.ediancha.com.entity;

import java.util.List;

/**
 * Created by dengmingzhi on 2016/10/28.
 */

public class NewBuyCar extends BaseListEntity<NewBuyCar.Data> {

    public static class Data extends client.ediancha.com.entity.Data {
        public String store_id;
        public String store_name;
        public List<BuyCar.Shop> cart_list;
        public boolean isChoose = false;
        public boolean isDelete=false;
    }

//    public static class CartList {
//        public int buy_quantity;
//        public String buyer_quota;
//        public int has_property;//属性商品1是2否
//        public String image;
//        public String name;
//        public String pigcms_id;
//        public String price;
//        public int pro_num;//购买数量
//        public String pro_price;
//        public String product_id;
//        public String quantity;
//        public String sku_id;
//        public int sku_num;//属性商品库存
//    }


}
