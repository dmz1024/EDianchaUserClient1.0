package client.ediancha.com.entity;

/**
 * Created by dengmingzhi on 2016/10/31.
 */

public class WechatInfo extends BaseEntity {
    public Data data;
    public static class Data{
        public String appid;
        public String noncestr;
        public String partnerid;
        public String prepayid;
        public String sign;
        public String timestamp;
    }
}
