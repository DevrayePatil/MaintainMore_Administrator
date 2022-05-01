package com.administrator.maintainmore.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.administrator.maintainmore.Models.PersonalServiceCardModal;
import com.administrator.maintainmore.Models.RepairApplianceCardModal;
import com.administrator.maintainmore.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RepairApplianceServiceAdapter
        extends RecyclerView.Adapter<RepairApplianceServiceAdapter.ViewHolder> {

    ArrayList<RepairApplianceCardModal> repairApplianceCardModals;
    Context context;

    ViewHolder.OnRepairApplianceServiceClickListener repairApplianceServiceClickListener;

    public RepairApplianceServiceAdapter(ArrayList<RepairApplianceCardModal> repairApplianceCardModals, Context context,
                                         ViewHolder.OnRepairApplianceServiceClickListener repairApplianceServiceClickListener) {
        this.repairApplianceCardModals = repairApplianceCardModals;
        this.context = context;
        this.repairApplianceServiceClickListener = repairApplianceServiceClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_user, parent, false);
        return new ViewHolder(view, repairApplianceServiceClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        RepairApplianceCardModal modal = repairApplianceCardModals.get(position);

        holder.displayName.setText(modal.getServiceName());
        holder.displayEmail.setText(modal.getServiceDescription());

        Glide.with(context).load(modal.getIconUrl()).placeholder(R.drawable.ic_person).into(holder.profilePicture);
    }

    @Override
    public int getItemCount() {
        return repairApplianceCardModals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView displayName, displayEmail;
        ImageView profilePicture;

        OnRepairApplianceServiceClickListener repairApplianceServiceClickListener;
        public ViewHolder(@NonNull View itemView,
                          OnRepairApplianceServiceClickListener repairApplianceServiceClickListener) {
            super(itemView);

            displayName = itemView.findViewById(R.id.displayName);
            displayEmail = itemView.findViewById(R.id.displayEmail);
            profilePicture = itemView.findViewById(R.id.profilePicture);


            this.repairApplianceServiceClickListener = repairApplianceServiceClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            repairApplianceServiceClickListener.onRepairApplianceServiceClickListener(getAdapterPosition());
        }


        public interface OnRepairApplianceServiceClickListener{
            void onRepairApplianceServiceClickListener(int position);
        }
    }
}
