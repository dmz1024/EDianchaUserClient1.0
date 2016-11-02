package client.ediancha.com.entity;

import java.util.List;

/**
 * Created by dengmingzhi on 2016/11/2.
 */

public class History {
    public List<Data> data;

    public static class Data {
        public List<Content> contents;
    }

    public static class Content{
        public String content;
    }

}
