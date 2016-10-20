package client.ediancha.com.api;

import java.util.Map;

import client.ediancha.com.entity.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;


/**
 * Created by dengmingzhi on 16/10/10.
 */

public interface ApiService {

    String BASE_URL = "http://keji.lovect.cn/app/";
    /**
     * 普通写法
     * http://keji.lovect.cn/app/mall.php
     */
    @GET("{url}")
    Observable<Movie> getTopMovie(@Path("url") String url, @QueryMap Map<String, String> map);


    @GET("http://keji.lovect.cn/app/mall.php")
    Observable<Movie> get(
            @QueryMap Map<String, String> map);


    @POST("/{url}")
    Call<Movie> post(
            @Path("url") String url,
            @Query("size") String start, @Query("clients") String count, @Query("date") String date);

    @GET("{url}")
    Observable<String> get(
            @Path("url") String url,
            @QueryMap Map<String, String> map);
    @GET("{url}")
    Observable<String> post(
            @Path("url") String url,
            @QueryMap Map<String, String> map);

}
