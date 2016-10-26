package client.ediancha.com.entity;

import java.util.List;

/**
 * Created by dengmingzhi on 2016/10/25.
 */

public class Citys {
    public List<Data> data;

    public static class Data {
        public String id;
        public String name;
        public List<City> city;
    }

    public static class City {
        public String id;
        public String name;
        public List<County> county;
    }

    public static class County {
        public String id;
        public String name;
    }
}
