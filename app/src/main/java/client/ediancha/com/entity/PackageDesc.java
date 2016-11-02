package client.ediancha.com.entity;

import java.util.List;

/**
 * Created by dengmingzhi on 16/10/11.
 */

public class PackageDesc extends BaseEntity {
    public Data data;

    public static class Data {
        public String conten;
        public String cz_id;
        public List<String> images;
        public String name;
        public String phone1;
        public String phone2;
        public String physical_id;
        public String price;
        public String renshu;
        public String sale;
        public String store_name;
        public Share share;
        public String content;
    }

    public static class Share{
        public String info;
        public String logo;
        public String name;
        public String url;
    }

}
