package client.ediancha.com.entity;

import java.util.List;

/**
 * Created by dengmingzhi on 16/10/10.
 */

public class Movie {
    List<Data> data;

    public static class Data {
        public String addtime;
    }


    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < data.size(); i++) {
            stringBuffer.append(data.get(i).addtime).append("\n");
        }
        return stringBuffer.toString();
    }
}
