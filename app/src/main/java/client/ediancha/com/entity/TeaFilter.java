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
        public String key1;
        public String key2;
        public List<Cat1> brand;
        public List<Cat1> yt;
    }

    public static class Cat1 {
        public String name;
        public String value;
        public String key;
    }

}
