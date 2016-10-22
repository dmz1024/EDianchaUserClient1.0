package client.ediancha.com.entity;

import java.util.List;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class ApplyOrder extends BaseListEntity<ApplyOrder.Data> {

    public static class Data extends client.ediancha.com.entity.Data {
        public String id;
        public String store_id;
        public String cid;
        public String name;
        public String Mobile;
        public String addtime;
        public String sttime;
        public String endtime;
        public String ch_name;
        public String address;
        public String store_name;
        public String images;
        public int status;
    }


}
