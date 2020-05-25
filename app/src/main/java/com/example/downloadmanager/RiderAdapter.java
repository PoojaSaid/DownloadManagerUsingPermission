package com.example.downloadmanager;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RiderAdapter extends RecyclerView.Adapter<RiderAdapter.RiderViewHolder>{

    public class RiderViewHolder extends RecyclerView.ViewHolder{
        public RiderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    @NonNull
    @Override
    public RiderAdapter.RiderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RiderAdapter.RiderViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
