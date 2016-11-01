package client.ediancha.com.entity;

/**
 * Created by dengmingzhi on 2016/11/1.
 */

public class StoreInfo extends BaseListEntity<StoreInfo.Data> {

    public static class Data extends client.ediancha.com.entity.Data {
        public int r;
        public Data1 data1;
        public Data2 data2;
        public Data3 data3;
    }

    public class Data1 {
        public String intro;
        public String logo;
        public String name;
        public String store_id;
    }

    public class Data2 {
        public String info;
        public String logo;
        public String name;
        public String url;
    }

    public class Data3 {
        public String image;
        public int is_recommend;
        public String name;
        public String original_price;
        public String price;
        public String product_id;
        public String recommend_title;
    }
}
