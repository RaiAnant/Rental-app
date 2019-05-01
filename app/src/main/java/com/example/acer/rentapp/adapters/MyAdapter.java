package com.example.acer.rentapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acer.rentapp.AssetPickup;
import com.example.acer.rentapp.EditAssetActivity;
import com.example.acer.rentapp.R;
import com.example.acer.rentapp.model.Asset;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Asset> assets;
    private Context context;

    public MyAdapter(List<Asset> assets, Context context){
        this.assets = assets;
        this.context = context;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.rent_list_item,parent,false);
        MyAdapter.ViewHolder viewHolder = new MyAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        final Asset asset = assets.get(position);
        holder.name.setText(asset.getAssetName());
        holder.cost.setText(asset.getCharges());
        holder.location.setText(asset.getPickupLocation());
        if(asset.getAssetType().toLowerCase().compareTo("car")==0){
            holder.img.setImageResource(R.drawable.car);
        }else{
            holder.img.setImageResource(R.drawable.bike);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,EditAssetActivity.class);
                intent.putExtra("type","del_edit");
                intent.putExtra("Asset",asset);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return assets.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView cost;
        public TextView location;
        public ImageView img;
        public View itemView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.list_text_name);
            this.cost = (TextView) itemView.findViewById(R.id.cost);
            this.location  = (TextView) itemView.findViewById(R.id.Location);
            this.img = (ImageView) itemView.findViewById(R.id.logo);
            this.itemView = itemView;
        }
    }



}