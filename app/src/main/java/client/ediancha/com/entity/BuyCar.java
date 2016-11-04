package client.ediancha.com.entity;

import java.util.List;

/**
 * Created by dengmingzhi on 2016/10/28.
 */

public class BuyCar extends BaseListEntity<BuyCar.Shop> {

    public static class Shop extends client.ediancha.com.entity.Data {
        public double after_subscribe_discount;
        public double after_subscribe_price;
        public double drp_level_1_price;
        public double drp_level_2_price;
        public double drp_level_3_price;
        public int buy_quantity;
        public int buyer_quota;
        public int has_property;
        public String image;
        public int is_fx;
        public String name;
        public String pigcms_id;
        public double price;
        public int pro_num;
        public String pro_price;
        public String product_id;
        public int quantity;
        public String sku_id;
        public String sku_num;
        public int status;
        public String store_id;
        public boolean isChoose = false;
        public boolean isDelete = false;
    }


}
