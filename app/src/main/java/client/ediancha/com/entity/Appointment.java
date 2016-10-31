package client.ediancha.com.entity;

import java.util.List;

/**
 * Created by dengmingzhi on 16/10/12.
 */

public class Appointment extends BaseListEntity<Appointment.Data> {

    public static class Data extends client.ediancha.com.entity.Data {
        public String bz;
        public String dateline;
        public String dd_time;
        public String food;
        public String images;
        public String name;
        public String num;
        public String order_id;
        public String orderid;
        public String phone;
        public String sc;
        public String status;
        public String store_name;
        public String store_uid;
        public String tableid;
        public String tablename;
        public int type;
        public String use_time;

    }



}
