package com.administrator.maintainmore.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.administrator.maintainmore.Models.NewTechnicianModal;
import com.administrator.maintainmore.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NewTechnicianAdapter extends RecyclerView.Adapter<NewTechnicianAdapter.viewHolder>{

    ArrayList<NewTechnicianModal> technicianModals;
    Context context;

    viewHolder.OnNewTechnicianCardClickListener newTechnicianCardClickListener;

    public NewTechnicianAdapter(ArrayList<NewTechnicianModal> technicianModals, Context context,
                                viewHolder.OnNewTechnicianCardClickListener newTechnicianCardClickListener) {
        this.technicianModals = technicianModals;
        this.context = context;
        this.newTechnicianCardClickListener = newTechnicianCardClickListener;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_new_technician, parent, false);
        return new NewTechnicianAdapter.viewHolder(view, newTechnicianCardClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        NewTechnicianModal modal = technicianModals.get(position);

        holder.displayName.setText(modal.getTechnicianName());
        holder.displayEmail.setText(modal.getTechnicianEmail());
        holder.displayPhoneNumber.setText(modal.getTechnicianPhoneNumber());

        Glide.with(context).load(modal.getTechnicianImageUrl()).placeholder(R.drawable.ic_person).into(holder.profilePicture);

    }

    @Override
    public int getItemCount() {
        return technicianModals.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView displayName, displayEmail, displayPhoneNumber;
        ImageView profilePicture;

        OnNewTechnicianCardClickListener newTechnicianCardClickListener;

        public viewHolder(@NonNull View itemView, OnNewTechnicianCardClickListener newTechnicianCardClickListener) {
            super(itemView);

            displayName = itemView.findViewById(R.id.displayName);
            displayEmail = itemView.findViewById(R.id.displayEmail);
            profilePicture = itemView.findViewById(R.id.profilePicture);
            displayPhoneNumber = itemView.findViewById(R.id.displayPhoneNumber);

            this.newTechnicianCardClickListener = newTechnicianCardClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            newTechnicianCardClickListener.onNewTechnicianCardClickListener(getAdapterPosition());
        }

        public interface OnNewTechnicianCardClickListener{
            void onNewTechnicianCardClickListener(int position);
        }
    }
}
