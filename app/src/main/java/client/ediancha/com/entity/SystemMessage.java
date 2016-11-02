package client.ediancha.com.entity;

/**
 * Created by dengmingzhi on 2016/11/2.
 */

public class SystemMessage extends BaseListEntity<SystemMessage.Data> {
    public static class Data extends client.ediancha.com.entity.Data {
        public String id;
        public String time;
        public String text;
    }
}
