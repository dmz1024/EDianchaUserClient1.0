package client.ediancha.com.entity;

import java.util.List;

/**
 * Created by dengmingzhi on 16/8/4.
 */
public class OrderLogistics extends BaseListEntity<OrderLogistics.Data> {

    public class Data extends client.ediancha.com.entity.Data {
        public List<Content> content;
        public String express_company;
        public String express_no;
        public String name;
    }

    public static class Content {
        public String context;
        public String time;
    }
}
