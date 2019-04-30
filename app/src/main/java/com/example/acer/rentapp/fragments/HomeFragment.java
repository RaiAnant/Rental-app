package com.example.acer.rentapp.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.example.acer.rentapp.LoginActivity;
import com.example.acer.rentapp.R;
import com.example.acer.rentapp.RentalListActivity;
import com.example.acer.rentapp.UserDetailsActivity;
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

    public ImageButton carImgButton;
    public ImageButton logout;
    public ImageButton editDetailsButton;


    public HomeFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        AppBarLayout appbar = (AppBarLayout) v.findViewById(R.id.appbar);
        float heightDp = getResources().getDisplayMetrics().heightPixels / 3;
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
        lp.height = (int) heightDp;
        carImgButton = v.findViewById(R.id.carButton);
        logout = v.findViewById(R.id.logout);
        editDetailsButton = v.findViewById(R.id.editDetails);
        carImgButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RentalListActivity.class);
                intent.putExtra("type","car");
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.usr_id), "");
                editor.putString(getString(R.string.usr_name), "");
                editor.putString(getString(R.string.usr_loc), "");
                editor.putString(getString(R.string.usr_cont), "");
                editor.putString(getString(R.string.password), "");
                editor.apply();
                getActivity().finish();
            }
        });

        editDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startUserDetailsActivity();
            }
        });
        return v;
    }

    public void startUserDetailsActivity(){
        Intent intent = new Intent(getActivity(),UserDetailsActivity.class);
        startActivity(intent);
    }


}

