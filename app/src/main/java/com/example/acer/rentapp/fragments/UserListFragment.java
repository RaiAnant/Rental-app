package com.example.acer.rentapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.acer.rentapp.R;
import com.example.acer.rentapp.adapters.RentAdapter;
import com.example.acer.rentapp.adapters.UserAdapter;
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

public class UserListFragment extends Fragment {


    public ArrayList<User> userData;
    public UserAdapter adapter ;
    public RecyclerView recyclerView;
    public Context context ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_rental_list, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        userData = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        context = getActivity();

        return v;
    }

    public void getUserList() {
        Log.d(TAG, "User");


        GetUserDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetUserDataService.class);

        Map<String, String> query = new HashMap<>();
        Log.d("where", "outside response");


        Call<List<User>> call = service.getUserCheck(query);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.d("where", "inside response");

                if (response.isSuccessful()) {
                    List<User> data = response.body();
                    userData = (ArrayList<User>) data;
//                    Log.d("list12",String.valueOf(assetData.size()));
                    adapter = new UserAdapter(userData,context);
                    recyclerView.setAdapter(adapter);

                } else {
                    Log.d("SUCCESS BUT NO DATA", "NO DATA");
                    Activity activity = getActivity();
                    Toast.makeText(activity , "FAILED", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("FAILED", t.getMessage());

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        getUserList();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putString("type",type);
    }
}
