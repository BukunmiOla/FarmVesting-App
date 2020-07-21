package com.startng.farmvesting.dashBoard.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.startng.farmvesting.R;
import com.startng.farmvesting.User;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {
    private DatabaseReference mDatabase;
    private static final String TAG = "EmailPassword";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        return root;
    }
    RecyclerView NotificationRv;
    NotificationAdapter notificationAdapter = new NotificationAdapter();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NotificationRv = view.findViewById(R.id.notification_rv);

        NotificationRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        NotificationRv.setAdapter(notificationAdapter);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("notifications");
        fetchNotifications();
    }

    private void fetchNotifications() {

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                List<User> users = new ArrayList<>();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    users.add(postSnapshot.getValue(User.class));
                }
                notificationAdapter.setUserData(users);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
