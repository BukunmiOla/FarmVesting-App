package com.startng.farmvesting.dashBoard.ui.notifications;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.startng.farmvesting.R;
import com.startng.farmvesting.User;

import java.util.List;

class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {

    private List<User> users;


    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_page, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        final User user = users.get(position);
        holder.bindData(user);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return users != null ? users.size() : 0;
    }

    void setUserData(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }
}

