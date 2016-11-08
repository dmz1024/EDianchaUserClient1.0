package client.ediancha.com.entity;

import java.util.List;

/**
 * Created by dengmingzhi on 2016/11/8.
 */

public class Hot extends BaseEntity {
    public List<Data> data;

    public static class Data {
        public List<Cat> data;
        public int r;
        public String key;
    }

    public static class Cat {
        public String name;
    }
}
