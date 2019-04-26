package com.example.acer.rentapp;

import com.example.acer.rentapp.model.Asset;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;

public interface GetAssetDataService {

    @GET("asset")
    Call<List<Asset>> getAssetCheck (@QueryMap Map<String, String> options);

    @POST("asset")
    Call<Asset>  postAssetCheck (@QueryMap Map<String, String> options);

    @PUT("asset")
    Call<List<Asset>>  putAssetCheck (@QueryMap Map<String, String> options);

    @DELETE("asset")
    Call<List<Asset>>  delAssetCheck (@QueryMap Map<String, String> options);
}
