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
         public String order_id;
         public String order_no;
         public String postage;
        public int status;
         public String sub_total;
         public String total;
        public String type;
        public String comment;
         public String store_name;
        public Address address;
        public int is_comment;
        public int is_return;
    }

    public static class OrderProduct implements Serializable {
        public String image;
        public String name;
        public String pro_num;
        public String pro_price;
        public int is_comment;
        public int is_return;
        public String product_id;
        public String pigcms_id;
    }

    public static class Address implements Serializable {
        public String address;
        public String province;
        public String city;
        public String area;
        public String province_txt;
        public String city_txt;
        public String county_txt;
    }

}
