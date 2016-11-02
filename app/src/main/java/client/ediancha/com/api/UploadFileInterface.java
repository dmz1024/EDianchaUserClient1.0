package client.ediancha.com.api;


import java.util.Map;

import client.ediancha.com.entity.UploadImage;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by dengmingzhi on 16/9/21.
 */
public interface UploadFileInterface {
    @Multipart
    @POST("http://www.ediancha.com/app.php")
    Call<UploadImage> upload(@QueryMap Map<String, String> map, @PartMap Map<String, RequestBody> pararms);
}
