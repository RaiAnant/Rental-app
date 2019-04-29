package com.example.acer.rentapp;

import com.example.acer.rentapp.model.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;

public interface GetUserDataService {
    @GET("user")
    Call<List<User>> getUserCheck (@QueryMap Map<String, String> options);

    @POST("user")
    Call<User>  postUserCheck (@QueryMap Map<String, String> options);

    @PUT("user")
    Call<User>  putUserCheck (@QueryMap Map<String, String> options);

    @DELETE("user")
    Call<List<User>>  delUserCheck (@QueryMap Map<String, String> options);

}