package com.startng.farmvesting.dashBoard.ui.home;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.startng.farmvesting.Farmer;
import com.startng.farmvesting.R;

class FarmViewHolder extends RecyclerView.ViewHolder {

    private TextView farmerNameTv, descriptionTv, returnTv, verificationStatusTv;

    public FarmViewHolder(@NonNull View itemView) {
        super(itemView);

        farmerNameTv = itemView.findViewById(R.id.farmer_name_tv);
        returnTv = itemView.findViewById(R.id.return_tv);
        descriptionTv = itemView.findViewById(R.id.description_tv);
        verificationStatusTv = itemView.findViewById(R.id.verification_status_tv);
    }

    public void bindData(Farmer farmer) {
        farmerNameTv.setText(farmer.farmerName);
        returnTv.setText(farmer.returns);
        descriptionTv.setText(farmer.shortDescription);
        verificationStatusTv.setText(farmer.verification ? "Verified" : "Not Verified");
        if (verificationStatusTv.getText().equals("Not Verified")){
            verificationStatusTv.setTextColor(Color.parseColor("#EF0808"));
        }
    }
}
