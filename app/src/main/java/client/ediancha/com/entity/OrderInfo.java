package client.ediancha.com.entity;

import java.util.List;

/**
 * Created by dengmingzhi on 2016/10/31.
 */
public class OrderInfo extends BaseEntity {
    public Data data;
    public static class Data {
        public List<TeaOrder.OrderProduct> data1;
        public Data2 data2;
        public Data3 data3;
    }


    public static class Data2 {
        public String add_time;
        public String address;
        public String address_id;
        public String area;
        public String area_txt;
        public String city;
        public String city_txt;
        public String name;
        public String province;
        public String province_txt;
        public String tel;
        public String zipcode;
        public String msg;
    }

    public static class Data3 {
        public String postage;
        public String sale_total;
        public String total;
    }
}
