package client.ediancha.com.entity;

import java.util.List;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaSpace extends BaseListEntity<TeaSpace.Data> {

    public static class Data extends client.ediancha.com.entity.Data {
        public int r;
        public List<Data1> data1;
        public Data2 data2;
    }


    public static class Data1 {
        public String description;
        public String name;
        public String pic;
        public String url;
        public String key;
        public String value;
    }

    public static class Data2 {

        public String address;
        public String attention_num;
        public String images;
        public String name;
        public String pigcms_id;
        public String price;
        public String store_id;
        public String juli;

    }


}
