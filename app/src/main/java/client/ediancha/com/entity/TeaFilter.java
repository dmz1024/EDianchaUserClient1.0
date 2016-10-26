package client.ediancha.com.entity;

import java.util.List;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaFilter extends BaseListEntity<TeaFilter.Data> {

    public static class Data extends client.ediancha.com.entity.Data {
        public String name;
        public String key;
        public List<Cat> data;
    }

    public static class Cat {
        public String name;
        public String value;
        public String key;
    }
}
