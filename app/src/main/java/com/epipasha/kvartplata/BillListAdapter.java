package com.epipasha.kvartplata;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.epipasha.kvartplata.data.KvartplataContract.BillEntry;
import com.epipasha.kvartplata.data.KvartplataContract.HotWaterEntry;
import com.epipasha.kvartplata.data.KvartplataContract.ColdWaterEntry;
import com.epipasha.kvartplata.data.KvartplataContract.CanalizationEntry;
import com.epipasha.kvartplata.data.KvartplataContract.ElectricityEntry;
import com.epipasha.kvartplata.data.KvartplataDbManager;


public class BillListAdapter extends RecyclerView.Adapter<BillListAdapter.BillListHolder>{

    private Context context;
    private Cursor cursor;

    public BillListAdapter(Context context){
        this.context = context;
    }

    public void setCursor(Cursor cursor){
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public BillListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_bill, parent, false);

        BillListHolder vh = new BillListHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(BillListHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    public class BillListHolder extends RecyclerView.ViewHolder{

        TextView billDate;
        TextView billHotWater;
        TextView billColdWater;
        TextView billCanalization;
        TextView billElectricity;
        TextView billTotal;

        int id;

        public BillListHolder(View itemView) {
            super(itemView);

            billDate = (TextView) itemView.findViewById(R.id.bill_date);
            billHotWater = (TextView) itemView.findViewById(R.id.bill_hot_water);
            billColdWater = (TextView) itemView.findViewById(R.id.bill_cold_water);
            billCanalization = (TextView) itemView.findViewById(R.id.bill_canalization);
            billElectricity = (TextView) itemView.findViewById(R.id.bill_electricity);
            billTotal = (TextView) itemView.findViewById(R.id.bill_total);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra(DetailFragment.DB_ID, id);
                    context.startActivity(i);
                }
            });
        }

        public void setData(int position){
            cursor.moveToPosition(position);

            id = cursor.getInt(cursor.getColumnIndex(BillEntry._ID));
            int month = cursor.getInt(cursor.getColumnIndex(BillEntry.COLUMN_MONTH));
            int year = cursor.getInt(cursor.getColumnIndex(BillEntry.COLUMN_YEAR));

            billDate.setText(KvartplataDbManager.getBillDate(month, year));

            billHotWater.setText(KvartplataDbManager.getValue(cursor, HotWaterEntry.COLUMN_SUM));
            billColdWater.setText(KvartplataDbManager.getValue(cursor, ColdWaterEntry.COLUMN_SUM));
            billCanalization.setText(KvartplataDbManager.getValue(cursor, CanalizationEntry.COLUMN_SUM));
            billElectricity.setText(KvartplataDbManager.getValue(cursor, ElectricityEntry.COLUMN_SUM));

            billTotal.setText(KvartplataDbManager.getValue(cursor, BillEntry.COLUMN_SUM));
        }
    }

}
