package client.ediancha.com.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaOrder extends BaseListEntity<TeaOrder.Data> {

    public static class Data extends client.ediancha.com.entity.Data implements Serializable {
        public List<OrderProduct> order_product_list;
        public String add_time;
        public String address_tel;
        public String address_user;
        public String cancel_method;
        public String data_id;
        public String data_money;
        public String order_id;
        public String order_no;
        public String paid_time;
        public String pay_money;
        public String payment_method;
        public String postage;
        public String pro_count;
        public String pro_num;
        public String shipping_method;
        public int status;
        public String store_name;
        public String sub_total;
        public String total;
        public String trade_no;
        public String type;

    }

    public static class OrderProduct implements Serializable {
        public String image;
        public String name;
        public String pro_num;
        public String pro_price;
        public String product_id;
    }

}
