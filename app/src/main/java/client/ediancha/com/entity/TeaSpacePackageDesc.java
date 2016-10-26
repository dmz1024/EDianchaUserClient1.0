package client.ediancha.com.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dengmingzhi on 16/10/11.
 */

public class TeaSpacePackageDesc extends BaseEntity {
    public Data data;

    public static class Data {
        public List<BaoXiang> bx;
        public List<Comment> comment;
        public List<OtherStore> list;
        public Show show;
    }

    public static class Event {
        public String title;
        public String image;
    }


    public static class BaoXiang implements Serializable{
        public String cz_id;
        public String images;
        public String name;
        public String price;
        public String renshu;
    }

    public static class Comment {

    }

    public static class OtherStore {
        public String address;
        public String images;
        public String name;
        public String pigcms_id;
        public String price;
    }


    public static class Show {
        public String address;
        public String business_hours;
        public int commentcount;
        public int commentscore;
        public String content;
        public String name;
        public String phone1;
        public String phone2;
        public String price;
        public String shortdesc;
        public String store_id;
        public String storename;
        public String url;
        public List<String> images;
    }


}
