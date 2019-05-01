package com.example.acer.rentapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.acer.rentapp.adapters.MyAdapter;
import com.example.acer.rentapp.adapters.RentAdapter;
import com.example.acer.rentapp.interfaces.GetAssetDataService;
import com.example.acer.rentapp.interfaces.GetUserDataService;
import com.example.acer.rentapp.model.Asset;
import com.example.acer.rentapp.model.User;
import com.example.acer.rentapp.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class MyAssetActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private List<Asset> assetData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_asset);
    }

    public void getAssetList() {
        Log.d(TAG, "Asset");


        GetAssetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetAssetDataService.class);

        Map<String, String> query = new HashMap<>();
        Log.d("where", "outside response");
        query.put("LENDER_ID", "");

        Call<List<Asset>> call = service.getAssetCheck(query);
        call.enqueue(new Callback<List<Asset>>() {
            @Override
            public void onResponse(Call<List<Asset>> call, Response<List<Asset>> response) {
                Log.d("where", "inside response");

                if (response.isSuccessful()) {
                    List<Asset> data = response.body();
                    assetData = (ArrayList<Asset>) data;
//

                } else {
                    Log.d("SUCCESS BUT NO DATA", "NO DATA");

                    Toast.makeText(MyAssetActivity.this, "FAILED", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Asset>> call, Throwable t) {
                Log.d("FAILED", t.getMessage());

            }
        });

    }


    public void addAssets() {
        Log.d("block", "asset");
        GetAssetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetAssetDataService.class);

        Map<String, String> query = new HashMap<>();
        query.put("ASSET_ID", "");
        query.put("ASSET_NAME", "");
        query.put("ASSET_TYPE", "");
        query.put("LENDER_ID", "");
        query.put("PICKUP_LOCATION", "");
        query.put("DROP_LOCATION", "");
        query.put("CHARGES", "");

        Call<Asset> call = service.postAssetCheck(query);
        call.enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                Log.d("where", "inside response");

                if (response.isSuccessful()) {
                    Asset data = response.body();

                    if (data == null) {
                        Toast.makeText(MyAssetActivity.this, "FAILED", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(MyAssetActivity.this, "ADDED", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Asset> call, Throwable t) {
                Log.d("FAILED", t.getMessage());
                Toast.makeText(MyAssetActivity.this, "FAILED", Toast.LENGTH_LONG).show();
            }
        });
    }
}
