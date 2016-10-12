package client.ediancha.com.entity;

import java.util.List;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class LikeEvent extends BaseListEntity<LikeEvent.Data> {

    public static class Data extends client.ediancha.com.entity.Data {
        public String title;
        public List<Casts> casts;
        public Images images;
    }

    public static class Casts {
        public String name;
        public String id;
    }

    public static class Images {
        public String large;
    }


}
