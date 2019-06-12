package com.example.duanwu.demo_p21;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface MyServler {
    String url="https://www.wanandroid.com/project/list/";
    @GET("1/json?")
    Observable<Bean>  getImage(@QueryMap Map<String,Object> map);
}
