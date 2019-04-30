package com.example.acer.rentapp.interfaces;

import com.example.acer.rentapp.model.Admin;
import com.example.acer.rentapp.model.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;

public interface GetAdminDataService {

    @GET("admin")
    Call<List<Admin>> getAdminCheck (@QueryMap Map<String, String> options);

    @POST("admin")
    Call<Admin>  postAdminCheck (@QueryMap Map<String, String> options);

    @PUT("admin")
    Call<Admin>  putAdminCheck (@QueryMap Map<String, String> options);

    @DELETE("admin")
    Call<List<Admin>>  delAdminCheck (@QueryMap Map<String, String> options);
}
