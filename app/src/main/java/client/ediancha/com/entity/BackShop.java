package client.ediancha.com.entity;

/**
 * Created by dengmingzhi on 2016/11/7.
 */

public class BackShop extends BaseListEntity<BackShop.Data> {

    public static class Data extends client.ediancha.com.entity.Data{
        public String content;
        public String dateline;
        public String id;
        public String images;
        public String name;
        public String order_id;
        public String order_no;
        public String order_product_id;
        public String phone;
        public String pro_num;
        public String pro_price;
        public String product_id;
        public String return_no;
        public int status;
        public String type;
    }
}
