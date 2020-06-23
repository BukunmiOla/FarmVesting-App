package com.startng.farmvesting.dashBoard.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.startng.farmvesting.BusinessInfoActivity;
import com.startng.farmvesting.Farmer;
import com.startng.farmvesting.R;
import com.startng.farmvesting.dashBoard.FarmSelectionListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    Spinner categorySp;
    String categoryText;
    private DatabaseReference mDatabase;
    private static final String TAG = "EmailPassword";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }

    RecyclerView farmRv;
    FarmAdapter farmAdapter = new FarmAdapter();
    FarmAdapter sortedFarmAdapter = new FarmAdapter();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        farmRv = view.findViewById(R.id.farm_rv);

        farmRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        farmRv.setAdapter(farmAdapter);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("farms");
        fetchFarms();
        categorySp = view.findViewById(R.id.category_sp);
        categorySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryText = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        view.findViewById(R.id.sort_btn).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 sortFarmers();
             }
         });

    }

    private void sortFarmers() {


        fetchSortedFarmers();

    }

    private void fetchSortedFarmers() {
        final FarmSelectionListener listener = new FarmSelectionListener() {
            @Override
            public void onSelectFarm(Farmer farmer) {

                Intent goToFarmerInformation = new Intent(getActivity(), BusinessInfoActivity.class);
                goToFarmerInformation.putExtra("farmerName", farmer.farmerName);
                goToFarmerInformation.putExtra("address", farmer.address);
                goToFarmerInformation.putExtra("category", farmer.category);
                goToFarmerInformation.putExtra("aboutBusiness", farmer.aboutBusiness);
                goToFarmerInformation.putExtra("returns", farmer.returns);
                String verify = farmer.verification ? "Verified" : "Not Verified";
                goToFarmerInformation.putExtra("verification", verify);
                startActivity(goToFarmerInformation);

            }


        };
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                List<Farmer> farmers = new ArrayList<>();
                List<Farmer> sortedFarmers = new ArrayList<>();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    farmers.add(postSnapshot.getValue(Farmer.class));
                }
                for (Farmer farmer : farmers) {
                    if (farmer.category.equals(categoryText)) {
                        sortedFarmers.add(farmer);
                        sortedFarmAdapter.setSortedFarmData(sortedFarmers, listener);
                    }
                    farmRv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    farmRv.setAdapter(sortedFarmAdapter);
                }
            }
            @Override
            public void onCancelled (DatabaseError error){
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }

        });
    }

    private void fetchFarms() {

        final FarmSelectionListener listener = new FarmSelectionListener() {
            @Override
            public void onSelectFarm(Farmer farmer) {

                Intent goToFarmerInformation = new Intent(getActivity(), BusinessInfoActivity.class);
                goToFarmerInformation.putExtra("farmerName", farmer.farmerName);
                goToFarmerInformation.putExtra("address", farmer.address);
                goToFarmerInformation.putExtra("category", farmer.category);
                goToFarmerInformation.putExtra("aboutBusiness", farmer.aboutBusiness);
                goToFarmerInformation.putExtra("returns", farmer.returns);
                String verify = farmer.verification ? "Verified" : "Not Verified";
                goToFarmerInformation.putExtra("verification", verify);
                startActivity(goToFarmerInformation);

         }


        };

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                List<Farmer> farmers = new ArrayList<>();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    farmers.add(postSnapshot.getValue(Farmer.class));
                }

                farmAdapter.setFarmData(farmers, listener);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

}
