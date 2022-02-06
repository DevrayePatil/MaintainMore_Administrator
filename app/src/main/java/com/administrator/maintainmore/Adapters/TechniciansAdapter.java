package com.administrator.maintainmore.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.administrator.maintainmore.Models.TechniciansModal;
import com.administrator.maintainmore.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TechniciansAdapter extends RecyclerView.Adapter<TechniciansAdapter.viewHolder>{

    ArrayList<TechniciansModal> TechniciansModals;
    Context context;

    public TechniciansAdapter(ArrayList<TechniciansModal> TechniciansModals, Context context) {
        this.TechniciansModals = TechniciansModals;
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

        TechniciansModal modal = TechniciansModals.get(position);

        holder.displayName.setText(modal.getName());
        holder.displayEmail.setText(modal.getEmail());

        Glide.with(context).load(modal.getImageUrl()).placeholder(R.drawable.ic_person).into(holder.profilePicture);


    }

    @Override
    public int getItemCount() {
        return TechniciansModals.size();
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
