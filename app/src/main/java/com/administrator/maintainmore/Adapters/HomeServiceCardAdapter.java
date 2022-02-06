package com.administrator.maintainmore.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.administrator.maintainmore.Models.HomeServiceCardModal;
import com.administrator.maintainmore.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class HomeServiceCardAdapter extends RecyclerView.Adapter<HomeServiceCardAdapter.viewHolder>{

    ArrayList<HomeServiceCardModal> cardModals;
    Context context;

    viewHolder.OnHomeServiceCardClickListener cardClickListener;

    public HomeServiceCardAdapter(ArrayList<HomeServiceCardModal> cardModals, Context context,
                                  viewHolder.OnHomeServiceCardClickListener cardClickListener) {
        this.cardModals = cardModals;
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

        HomeServiceCardModal modal = cardModals.get(position);

        holder.displayName.setText(modal.getServiceName());
        holder.displayEmail.setText(modal.getServiceDescription());

        Glide.with(context).load(modal.getIconUrl()).placeholder(R.drawable.ic_person).into(holder.profilePicture);


    }

    @Override
    public int getItemCount() {
        return cardModals.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView displayName, displayEmail;
        ImageView profilePicture;

        OnHomeServiceCardClickListener cardClickListener;

        public viewHolder(@NonNull View itemView, OnHomeServiceCardClickListener cardClickListener) {
            super(itemView);

            displayName = itemView.findViewById(R.id.displayName);
            displayEmail = itemView.findViewById(R.id.displayEmail);
            profilePicture = itemView.findViewById(R.id.profilePicture);


            this.cardClickListener = cardClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            cardClickListener.onHomeServiceCardClick(getAdapterPosition());
        }

        public interface OnHomeServiceCardClickListener{
            void onHomeServiceCardClick(int position);
        }
    }
}
