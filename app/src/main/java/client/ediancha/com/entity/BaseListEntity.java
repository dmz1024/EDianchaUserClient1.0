package client.ediancha.com.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengmingzhi on 16/10/8.
 */

public class BaseListEntity<D extends Data> extends BaseEntity {
    public List<D> subjects;
//    public PageInfo page_info;
//
//    public static class PageInfo {
//        public int page_count;
//        public int page_index;
//    }

    public int start;
    public int total;
}
