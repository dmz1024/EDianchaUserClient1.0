package client.ediancha.com.entity;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaEvent extends BaseListEntity<TeaEvent.Data> {

    public static class Data extends client.ediancha.com.entity.Data {
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

}
