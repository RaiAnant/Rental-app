package com.example.acer.rentapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.acer.rentapp.R;
import com.example.acer.rentapp.model.User;

public class RentAdapter extends RecyclerView.Adapter<RentAdapter.ViewHolder> {

    private User[] users;

    public RentAdapter(User[] users){
        this.users = users;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.rent_list_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(users[position].getName());
        holder.usrname.setText(users[position].getUserName());
    }

    @Override
    public int getItemCount() {
        return users.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView usrname;
        public ViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.list_text_name);
            this.usrname = (TextView) itemView.findViewById(R.id.list_text_usrname);
        }
    }



}
