package client.ediancha.com.entity;

import java.util.List;

/**
 * Created by dengmingzhi on 2016/11/7.
 */

public class WriteBackShopInfo extends BaseEntity {
    public Data data;

    public static class Data extends client.ediancha.com.entity.Data {
        public String address_tel;
        public String address_user;
        public String cancel_dateline;
        public String dateline;
        public String discount;
        public String content;
        public String express_code;
        public String express_company;
        public String express_no;
        public String id;
        public String image;
        public String is_delivered;
        public String name;
        public String order_id;
        public String order_no;
        public String order_product_id;
        public String phone;
        public String postage_money;
        public String pro_num;
        public String pro_price;
        public String product_id;
        public String product_money;
        public String refund_status;
        public String refund_time;
        public String return_no;
        public String shipping_method;
        public int status;
        public String status_txt;
        public String store_content;
        public String platform_point;
        public String type_txt;
        public String user_cancel_dateline;
        public int type;
        public TeaOrder.Address address;
        public List<Express> express;
        public List<String> images;
    }

    public static class Express {
        public String code;
        public String name;
    }


}
