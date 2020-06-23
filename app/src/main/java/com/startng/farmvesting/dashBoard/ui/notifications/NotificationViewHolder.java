package com.startng.farmvesting.dashBoard.ui.notifications;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.startng.farmvesting.Farmer;
import com.startng.farmvesting.R;
import com.startng.farmvesting.User;

class NotificationViewHolder extends RecyclerView.ViewHolder {

    private TextView notifyTv, timeTv;

    public NotificationViewHolder(@NonNull View itemView) {
        super(itemView);
        notifyTv = itemView.findViewById(R.id.notify_tv);
        timeTv = itemView.findViewById(R.id.time_tv);

    }

    public void bindData(User user) {
        notifyTv.setText(user.notification);
        timeTv.setText(user.timeStamp);
    }
}
