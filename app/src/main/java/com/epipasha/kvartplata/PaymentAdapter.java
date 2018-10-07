package com.epipasha.kvartplata;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.epipasha.kvartplata.data.Conv;
import com.epipasha.kvartplata.data.entities.PaymentEntity;

import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {

    private List<PaymentEntity> items;
    private PaymentClickListener mListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_month_payment, parent, false);
        return new ViewHolder(v);
    }

    interface PaymentClickListener{
        void OnPaymentClick(PaymentEntity payment);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PaymentEntity payment = items.get(position);

        holder.tvMonth.setText(Conv.convertDateToString(payment.getDate()));
        holder.tvSum.setText(String.valueOf(payment.getSum()));
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public void setItems(List<PaymentEntity> payments){
        items = payments;
        notifyDataSetChanged();
    }

    public void setPaymentClickListener(PaymentClickListener listener) {
        mListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvMonth;
        TextView tvSum;

        public ViewHolder(View itemView) {
            super(itemView);

            tvMonth = itemView.findViewById(R.id.tvMonth);
            tvSum = itemView.findViewById(R.id.tvSum);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.OnPaymentClick(items.get(getAdapterPosition()));
        }
    }
}
