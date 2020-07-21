package com.startng.farmvesting.dashBoard.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.startng.farmvesting.Farmer;
import com.startng.farmvesting.R;
import com.startng.farmvesting.dashBoard.FarmSelectionListener;

import java.util.List;

class FarmAdapter extends RecyclerView.Adapter<FarmViewHolder> {

    private List<Farmer> farmers;
    private FarmSelectionListener listener;

    @NonNull
    @Override
    public FarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage, parent, false);
        return new FarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FarmViewHolder holder, int position) {
        final Farmer farmer = farmers.get(position);
        holder.bindData(farmer);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSelectFarm(farmer);
            }
        });
    }

    @Override
    public int getItemCount() {
        return farmers != null ? farmers.size() : 0;
    }

    void setFarmData(List<Farmer> farmers, FarmSelectionListener listener) {
        this.farmers = farmers;
        this.listener = listener;
        notifyDataSetChanged();
    }
    void setSortedFarmData(List<Farmer> farmers, FarmSelectionListener listener) {
        this.farmers = farmers;
        this.listener = listener;
        notifyDataSetChanged();
    }
}
