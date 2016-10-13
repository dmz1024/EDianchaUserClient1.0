package client.ediancha.com.entity;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaProduct extends BaseListEntity<TeaProduct.Data> {

    public static class Data extends client.ediancha.com.entity.Data {
        public String title;
        public Images images;
        public Type type;
    }

    public static class Images {
        public String large;
    }


    public static class Type {
        public String title;
    }

    public static class Ad {
        public String url;
    }

}
