package client.ediancha.com.entity;

import java.util.List;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class TeaEvent extends BaseListEntity<TeaEvent.Data> {

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

}
