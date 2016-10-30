package client.ediancha.com.entity;

import java.util.List;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaProduct extends BaseListEntity<TeaProduct.Data> {

    public static class Data extends client.ediancha.com.entity.Data {
        public int r;
        public Content data1;
        public Content data4;
        public Data2 data2;
        public List<Data3> data3;
    }


    public static class Content {
        public String image;
        public String is_recommend;
        public String name;
        public String original_price;
        public String price;
        public String product_id;
        public String recommend_title;
    }

    public static class Data2 {
        public String name;
        public List<TeaFilter.Cat> data;
        public String key;

    }


    public static class Data3 {
        public String bg_color;
        public String cat_id;
        public String id;
        public String last_time;
        public String name;
        public String pic;
        public String sort_order;
        public String status;
        public String store_id;
        public String url;
    }


}
