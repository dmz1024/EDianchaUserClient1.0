package client.ediancha.com.entity;

/**
 * Created by dengmingzhi on 2016/10/19.
 */

public class UserInfo extends BaseEntity {

    public Data data;

    public static class Data {
        public String newuser;
        public String sign;
        public String time;
        public String type;
        public String uid;
    }


}
