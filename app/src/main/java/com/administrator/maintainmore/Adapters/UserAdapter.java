package com.administrator.maintainmore.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.administrator.maintainmore.Models.UsersModal;
import com.administrator.maintainmore.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewHolder>{

    ArrayList<UsersModal> usersModals;
    Context context;

    viewHolder.OnUserCardClickListener cardClickListener;

    public UserAdapter(ArrayList<UsersModal> usersModals, Context context, viewHolder.OnUserCardClickListener cardClickListener) {
        this.usersModals = usersModals;
        this.context = context;
        this.cardClickListener = cardClickListener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_user, parent, false);
        return new viewHolder(view, cardClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        UsersModal modal = usersModals.get(position);

        holder.displayName.setText(modal.getName());
        holder.displayEmail.setText(modal.getEmail());

        Glide.with(context).load(modal.getImageUrl()).placeholder(R.drawable.ic_person).into(holder.profilePicture);


    }

    @Override
    public int getItemCount() {
        return usersModals.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView displayName, displayEmail;
        ImageView profilePicture;

        OnUserCardClickListener cardClickListener;

        public viewHolder(@NonNull View itemView, OnUserCardClickListener cardClickListener) {
            super(itemView);

            displayName = itemView.findViewById(R.id.displayName);
            displayEmail = itemView.findViewById(R.id.displayEmail);
            profilePicture = itemView.findViewById(R.id.profilePicture);

            this.cardClickListener = cardClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            cardClickListener.onUserCardClickListener(getAdapterPosition());
        }

        public interface OnUserCardClickListener{
            void onUserCardClickListener(int position);
        }
    }
}
