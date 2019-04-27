package com.example.acer.rentapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.rentapp.GetAssetDataService;
import com.example.acer.rentapp.R;
import com.example.acer.rentapp.RentalListActivity;
import com.example.acer.rentapp.adapters.RentAdapter;
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


public class RentalListFragment extends Fragment {


    public ArrayList<Asset> assetData;
    public RentAdapter adapter ;
    public RecyclerView recyclerView;
    public String type;
    public Context context = getActivity();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_rental_list, container, false);


        Intent intent = getActivity().getIntent();
        type = intent.getStringExtra("type");

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        assetData = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return v;
    }

    public void getAssetList() {
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
                    assetData = (ArrayList<Asset>) data;

                    for(int i = 0; i < assetData.size() ;i++){
//                        Log.d("list1",data.get(i).getAssetName());
                        if(type.compareTo("any")!=0){
                            if(assetData.get(i).getAssetType().toLowerCase().compareTo(type)!=0){
                                assetData.remove(i);
                                i--;
                            }
                        }
                    }
//                    Log.d("list12",String.valueOf(assetData.size()));
                    adapter = new RentAdapter(assetData,context);
                    recyclerView.setAdapter(adapter);
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


}
