package com.example.acer.rentapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.acer.rentapp.interfaces.GetAssetDataService;
import com.example.acer.rentapp.model.Asset;
import com.example.acer.rentapp.network.RetrofitClientInstance;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAssetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_asset);
    }

    public void modifyAssets() {
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

        Call<Asset> call = service.putAssetCheck(query);
        call.enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                Log.d("where", "inside response");

                if (response.isSuccessful()) {
                    Asset data = response.body();

                    if (data == null) {
                        Toast.makeText(EditAssetActivity.this, "FAILED", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(EditAssetActivity.this, "MODIFIED", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Asset> call, Throwable t) {
                Log.d("FAILED", t.getMessage());
                Toast.makeText(EditAssetActivity.this, "FAILED", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void deleteAssets() {
        Log.d("del", "asset");
        GetAssetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetAssetDataService.class);

        Map<String, String> query = new HashMap<>();
        query.put("ASSET_ID", "");


        Call<Asset> call = service.delAssetCheck(query);
        call.enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                Log.d("where", "inside response");

                if (response.isSuccessful()) {
                    Toast.makeText(EditAssetActivity.this, "DELETED", Toast.LENGTH_LONG).show();
                    

                }
            }
            @Override
            public void onFailure(Call<Asset> call, Throwable t) {
                Log.d("FAILED", t.getMessage());
                Toast.makeText(EditAssetActivity.this, "DELETION FAILED", Toast.LENGTH_LONG).show();
            }
        });
    }
}
