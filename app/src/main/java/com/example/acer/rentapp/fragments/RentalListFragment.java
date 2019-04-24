package com.example.acer.rentapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.acer.rentapp.R;
import com.example.acer.rentapp.adapters.RentAdapter;
import com.example.acer.rentapp.model.User;


public class RentalListFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_rental_list, container, false);
        User[] data = new User[] {
                new User("RaiAnant","Anant","Allahabad","7905389666","hyjkl"),
                new User("pancurry","Pankhuri","Minesota","8587802903","asdfg"),
                new User("H20","Vishal","Allahabad","dfgg","hyjkl")
        };

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        RentAdapter adapter = new RentAdapter(data);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return v;
    }



}
