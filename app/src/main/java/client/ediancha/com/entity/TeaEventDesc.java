package client.ediancha.com.entity;

/**
 * Created by dengmingzhi on 16/10/11.
 */

public class TeaEventDesc extends BaseListEntity<TeaEventDesc.Event> {
    public int count;

    public static class Event extends Data {
        public String title;
        public String image;
    }
}
