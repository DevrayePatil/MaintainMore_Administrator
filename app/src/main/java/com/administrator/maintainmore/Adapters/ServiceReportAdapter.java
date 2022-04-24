package com.administrator.maintainmore.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.administrator.maintainmore.Models.ServiceReportModal;
import com.administrator.maintainmore.R;

import java.util.ArrayList;

public class ServiceReportAdapter extends RecyclerView.Adapter<ServiceReportAdapter.ViewHolder>{

    ArrayList<ServiceReportModal> serviceReportModals;
    Context context;

    ViewHolder.OnServiceReportCardChickListener cardClickListener;

    public ServiceReportAdapter(ArrayList<ServiceReportModal> serviceReportModals, Context context,
                                ViewHolder.OnServiceReportCardChickListener cardClickListener) {
        this.serviceReportModals = serviceReportModals;
        this.context = context;

        this.cardClickListener = cardClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_service_report, parent, false);
        return new ViewHolder(view, cardClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServiceReportModal modal = serviceReportModals.get(position);

        holder.serviceID.setText(modal.getServiceID());
        holder.whoBookedService.setText(modal.getWhoBookedService());

    }

    @Override
    public int getItemCount() {
        return serviceReportModals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView serviceID, whoBookedService;
        OnServiceReportCardChickListener cardClickListener;

        public ViewHolder(@NonNull View itemView, OnServiceReportCardChickListener cardClickListener) {
            super(itemView);

            serviceID = itemView.findViewById(R.id.displayServiceID);
            whoBookedService = itemView.findViewById(R.id.displayWhoBookedService);

            this.cardClickListener = cardClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            cardClickListener.onServiceCardClickListener(getAdapterPosition());
        }

        public interface OnServiceReportCardChickListener{
            void onServiceCardClickListener(int position);
        }
    }


}
