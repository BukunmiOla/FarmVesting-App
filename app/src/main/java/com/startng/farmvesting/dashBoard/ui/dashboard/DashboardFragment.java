package com.startng.farmvesting.dashBoard.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.startng.farmvesting.BusinessInfoActivity;
import com.startng.farmvesting.R;
import com.startng.farmvesting.User;
import com.startng.farmvesting.dashBoard.MainActivity;
import com.startng.farmvesting.dashBoard.UserSelectionListener;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private DatabaseReference mDatabase;
    private static final String TAG = "EmailPassword";
    private List<User> users;
    private UserSelectionListener listener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        return root;
    }
    private  TextInputEditText fullNameEt, emailEt, phoneEt, dateOfBirthEt,
             nextOfKinFullNameEt, nextOfKinEmailEt;
    private String emailText;
    private TextView userNameTv, categoryTv;
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categoryTv = view.findViewById(R.id.category_tv);
        fullNameEt = view.findViewById(R.id.fullName);
        userNameTv = view.findViewById(R.id.username_tv);
        emailEt = view.findViewById(R.id.mail);
        phoneEt = view.findViewById(R.id.phone);
        dateOfBirthEt = view.findViewById(R.id.date_of_birth);
        nextOfKinFullNameEt = view.findViewById(R.id.next_of_kin_fullName);
        nextOfKinEmailEt = view.findViewById(R.id.next_of_kin_email);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        emailText = currentUser.getEmail();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        fetchUser();
    }
    private void fetchUser() {

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                 users = new ArrayList<>();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    users.add(postSnapshot.getValue(User.class));

                }
                for (User user:users){
                    if(user.email.equals(emailText)){
                        listener.onUserLogin(user);
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        listener = new UserSelectionListener() {
            @Override
            public void onUserLogin(User user) {

                fullNameEt.setText(user.Name);
                userNameTv.setText(user.Name);
                emailEt.setText(user.email);
                phoneEt.setText(user.phone);
                dateOfBirthEt.setText(user.dateOfBirth);
                String category = "Status: "+user.userCategory;
                categoryTv.setText(category);
                nextOfKinFullNameEt.setText(user.nextOfKinFullName);
                nextOfKinEmailEt.setText(user.nextOfKinEmail);

            }
        };
    }
}
