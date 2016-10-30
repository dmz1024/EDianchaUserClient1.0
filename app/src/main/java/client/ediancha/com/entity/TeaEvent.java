package client.ediancha.com.entity;

import java.util.List;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaEvent extends BaseListEntity<TeaEvent.Data> {

    public static class Data extends client.ediancha.com.entity.Data {
        public int r;
        public List<Data1> data1;
        public Data2 data2;
    }

    public static class Data2 {
        public String address;
        public String desc;
        public String endtime;
        public String images;
        public String logo;
        public String name;
        public String pigcms_id;
        public String price;
        public String sttime;
    }

    public static class Data1 {
        public String cat_id;
        public String cat_name;
        public String cat_pic;
    }

}
