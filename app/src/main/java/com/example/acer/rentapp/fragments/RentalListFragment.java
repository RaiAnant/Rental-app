package com.example.acer.rentapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.acer.rentapp.interfaces.GetAssetDataService;
import com.example.acer.rentapp.R;
import com.example.acer.rentapp.adapters.RentAdapter;
import com.example.acer.rentapp.model.Asset;
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
    public Context context ;
    public SharedPreferences pref;
    public ImageView backDrop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_rental_list, container, false);

        AppBarLayout appbar = (AppBarLayout) v.findViewById(R.id.appbar);
        float heightDp = getResources().getDisplayMetrics().heightPixels *2/ 5;
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
        lp.height = (int) heightDp;



        Intent intent = getActivity().getIntent();
        if(savedInstanceState!=null){
            type = savedInstanceState.getString("type");
        }else{
            type = intent.getStringExtra("type");
        }
        backDrop = v.findViewById(R.id.backdrop);
        if(type.toLowerCase().compareTo("car")==0){
            backDrop.setImageResource(R.drawable.car3);
        }else if(type.toLowerCase().compareTo("any")==0) {
            backDrop.setImageResource(R.drawable.backdrop);
        }else
        {
            backDrop.setImageResource(R.drawable.bike3);
        }

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        assetData = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        context = getActivity();
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        return v;
    }

    public void getAssetList() {
        Log.d(TAG, "Login");


        GetAssetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetAssetDataService.class);

        Map<String, String> query = new HashMap<>();
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
                            Log.d("filter", assetData.get(i).getUserName()+pref.getString(getString(R.string.usr_id),"")+assetData.get(i).getAssetType().toLowerCase()+type);
                            if(assetData.get(i).getAssetType().toLowerCase().compareTo(type)!=0 || assetData.get(i).getUserName().compareTo(pref.getString(getString(R.string.usr_id),""))==0){
                                assetData.remove(i);
                                i--;
                                continue;
                            }
                        }else{
                            break;
                        }

                    }
//                    Log.d("list12",String.valueOf(assetData.size()));
                    adapter = new RentAdapter(assetData,context);
                    recyclerView.setAdapter(adapter);

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

    @Override
    public void onStart() {
        super.onStart();
        getAssetList();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("type",type);
    }
}
