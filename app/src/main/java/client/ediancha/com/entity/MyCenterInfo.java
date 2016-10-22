package client.ediancha.com.entity;

/**
 * Created by dengmingzhi on 16/10/11.
 */

public class MyCenterInfo extends BaseEntity {
    public Data data;

    public static class Data {
        public String avatar;
        public int sex;
        public String intro;
        public String nickname;
        public String phone;
        public String point_balance;
    }
}
