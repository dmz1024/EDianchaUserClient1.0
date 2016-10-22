package client.ediancha.com.entity;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class Like extends BaseListEntity<Like.Data> {

    public static class Data extends client.ediancha.com.entity.Data {
        public String name;
        public String images;
        public String dataid;
    }

}
