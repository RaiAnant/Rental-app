package com.example.acer.rentapp.interfaces;

import com.example.acer.rentapp.model.Asset;
import com.example.acer.rentapp.model.Request;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;

public interface GetRequestDataService {

    @GET("request")
    Call<List<Request>> getRequestCheck (@QueryMap Map<String, String> options);

    @POST("request")
    Call<Request>  postRequestCheck (@QueryMap Map<String, String> options);

    @PUT("request")
    Call<Request>  putRequestCheck (@QueryMap Map<String, String> options);

    @DELETE("request")
    Call<List<Request>>  delRequestCheck (@QueryMap Map<String, String> options);
}
