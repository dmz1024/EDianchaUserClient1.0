package client.ediancha.com.entity;

import java.util.List;

/**
 * Created by dengmingzhi on 16/10/11.
 */

public class TeaEventDesc extends BaseEntity {

    public Data data;

    public static class Data {
        public Show show;
        public List<Lists> list;
        public Share share;
    }


    public static class Show {
        public String address;
        public String bm;
        public String content;
        public String endtime;
        public String images;
        public String name;
        public String price;
        public String renshu;
        public String storename;
        public String sttime;
        public String url;
        public double zlat;
        public double zlong;

    }

    public static class Lists {
        public String address;
        public String desc;
        public String endtime;
        public String images;
        public String name;
        public String pigcms_id;
        public String storename;
        public String sttime;

    }

    public static class Share{
        public String info;
        public String logo;
        public String name;
        public String url;
    }



}
