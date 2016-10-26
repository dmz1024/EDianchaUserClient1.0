package client.ediancha.com.entity;

import java.util.List;

/**
 * Created by dengmingzhi on 16/10/11.
 */

public class TeaDesc extends BaseEntity {
    public Data data;

    public static class Data {
        public List<TeaSpaceDesc.Comment> comment;
        public String content;
        public List<Image> images;
        public String name;
        public String phone1;
        public String phone2;
        public String postage;
        public String price;
        public String product_id;
        public String quantity;
        public String store_id;
        public String store_name;
        public Share share;
    }

    public static class Image {
        public String image;
    }

    public static class Share{
        public String info;
        public String logo;
        public String name;
        public String url;
    }

}
