package com.example.maintainmoreadministrator.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.maintainmoreadministrator.Models.UserModal;
import com.example.maintainmoreadministrator.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewHolder>{

    ArrayList<UserModal> userModals;
    Context context;

    public UserAdapter(ArrayList<UserModal> userModals, Context context) {
        this.userModals = userModals;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_user, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        UserModal modal = userModals.get(position);

        holder.displayName.setText(modal.getName());
        holder.displayEmail.setText(modal.getEmail());

        Glide.with(context).load(modal.getImageUrl()).into(holder.profilePicture);


    }

    @Override
    public int getItemCount() {
        return userModals.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{

        TextView displayName, displayEmail;
        ImageView profilePicture;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            displayName = itemView.findViewById(R.id.displayName);
            displayEmail = itemView.findViewById(R.id.displayEmail);
            profilePicture = itemView.findViewById(R.id.profilePicture);
        }
    }
}
