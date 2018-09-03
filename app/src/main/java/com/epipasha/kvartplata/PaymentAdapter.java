package com.epipasha.kvartplata;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {

    private List<Payment> items;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_month_payment, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Payment payment = items.get(position);

        SimpleDateFormat format = new SimpleDateFormat("MMM YYYY");
        holder.tvMonth.setText(format.format(payment.getDate()));
        holder.tvSum.setText(String.valueOf(payment.getSum()));
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public void setItems(List<Payment> payments){
        items = payments;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvMonth;
        TextView tvSum;

        public ViewHolder(View itemView) {
            super(itemView);

            tvMonth = itemView.findViewById(R.id.tvMonth);
            tvSum = itemView.findViewById(R.id.tvSum);
        }
    }
}
