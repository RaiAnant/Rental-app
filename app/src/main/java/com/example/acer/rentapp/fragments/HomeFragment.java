package com.example.acer.rentapp.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.acer.rentapp.GetAssetDataService;
import com.example.acer.rentapp.R;
import com.example.acer.rentapp.RentalListActivity;
import com.example.acer.rentapp.model.Asset;
import com.example.acer.rentapp.model.User;
import com.example.acer.rentapp.network.RetrofitClientInstance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class HomeFragment extends Fragment {

    public List<Asset> assetData;
    public ImageButton carImgButton;


    public HomeFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        login();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        AppBarLayout appbar = (AppBarLayout) v.findViewById(R.id.appbar);
        float heightDp = getResources().getDisplayMetrics().heightPixels / 3;
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
        lp.height = (int) heightDp;
        carImgButton = v.findViewById(R.id.carButton);
        carImgButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RentalListActivity.class);
                intent.ad
                startActivity(intent);
            }
        });
        return v;
    }

    public void login() {
        Log.d(TAG, "Login");

//        if (!validate()) {
//            onLoginFailed();
//            return;
//        }

        GetAssetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetAssetDataService.class);

        Map<String, String> query = new HashMap<>();
        query.put("IS_AVAIL", "YES");
        Log.d("where", "outside response");


        Call<List<Asset>> call = service.getAssetCheck(query);
        call.enqueue(new Callback<List<Asset>>() {
            @Override
            public void onResponse(Call<List<Asset>> call, Response<List<Asset>> response) {
                Log.d("where", "inside response");

                if (response.isSuccessful()) {
                    List<Asset> data = response.body();
                    assetData = data;
                    Log.d("Response123", data.toString() + data.size());
                    if (data.size() == 0) {
                        onLoginFailed();
                        return;
                    }
//
//                    for (User user : response.body()) {
//                        Log.wtf("Response", "" + user.getUserName());
//                        Toast.makeText(LoginActivity.this, user.getUserName(), Toast.LENGTH_LONG).show();
//
//                    }
//                    Log.d("data--",data.toString());
//                    Log.d("SUCCESS", response.raw().toString());

                } else {
                    Log.d("SUCCESS BUT NO DATA", "NO DATA");
                    Activity activity = getActivity();
                    Toast.makeText(activity , "FAILED", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Asset>> call, Throwable t) {
                Log.d("FAILED", t.getMessage());

            }
        });

    }

    public void onLoginFailed() {


    }
}

