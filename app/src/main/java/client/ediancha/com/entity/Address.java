package client.ediancha.com.entity;

import java.io.Serializable;

/**
 * Created by dengmingzhi on 2016/10/25.
 */

public class Address extends BaseListEntity<Address.Data> {
    public static class Data extends client.ediancha.com.entity.Data implements Serializable {
        public String name;
        public String tel;
        public String address;
        public int is_default;
        public String address_id;
        public String province;
        public String city;
        public String area;
        public String zipcode;
        public String add_time;
        public String province_txt;
        public String city_txt;
        public String area_txt;
    }
}
