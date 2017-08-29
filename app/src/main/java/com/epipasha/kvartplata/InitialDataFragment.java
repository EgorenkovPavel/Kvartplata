package com.epipasha.kvartplata;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.epipasha.kvartplata.data.KvartplataContract.ColdWaterEntry;
import com.epipasha.kvartplata.data.KvartplataContract.ElectricityEntry;
import com.epipasha.kvartplata.data.KvartplataContract.HotWaterEntry;
import com.epipasha.kvartplata.data.KvartplataDbHelper;
import com.epipasha.kvartplata.data.KvartplataDbManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class InitialDataFragment extends Fragment {

    private EditText hotWaterValue, coldWaterValue, electricityValue;

    public InitialDataFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_initial_data, container, false);

        hotWaterValue = (EditText)v.findViewById(R.id.hot_water_value);
        coldWaterValue = (EditText)v.findViewById(R.id.cold_water_value);
        electricityValue = (EditText)v.findViewById(R.id.electricity_value);

        loadData();

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        saveData();
    }

    private void saveData() {
        KvartplataDbHelper helper = new KvartplataDbHelper(getActivity());
        SQLiteDatabase db = helper.getWritableDatabase();

        saveHotWater(db);
        saveColdWater(db);
        saveElectricity(db);

        db.close();
    }

    private void saveHotWater(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(HotWaterEntry.COLUMN_BILL, KvartplataDbManager.initialBillId);
        values.put(HotWaterEntry.COLUMN_VALUE, Utils.getValue(hotWaterValue));

        db.update(HotWaterEntry.TABLE_NAME, values, HotWaterEntry.COLUMN_BILL + " = " + KvartplataDbManager.initialBillId, null);
    }

    private void saveColdWater(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(ColdWaterEntry.COLUMN_BILL, KvartplataDbManager.initialBillId);
        values.put(ColdWaterEntry.COLUMN_VALUE, Utils.getValue(coldWaterValue));

        db.update(ColdWaterEntry.TABLE_NAME, values, ColdWaterEntry.COLUMN_BILL + " = " + KvartplataDbManager.initialBillId, null);
    }

    private void saveElectricity(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(ElectricityEntry.COLUMN_BILL, KvartplataDbManager.initialBillId);
        values.put(ElectricityEntry.COLUMN_VALUE, Utils.getValue(electricityValue));

        db.update(ElectricityEntry.TABLE_NAME, values, ElectricityEntry.COLUMN_BILL + " = " + KvartplataDbManager.initialBillId, null);
    }

    private void loadData(){
        Cursor c = KvartplataDbManager.getInitialData(getActivity());
        if (c.moveToFirst()){
            hotWaterValue.setText(KvartplataDbManager.getValue(c, HotWaterEntry.COLUMN_VALUE));
            coldWaterValue.setText(KvartplataDbManager.getValue(c, ColdWaterEntry.COLUMN_VALUE));
            electricityValue.setText(KvartplataDbManager.getValue(c, ElectricityEntry.COLUMN_VALUE));
        }else {
            KvartplataDbManager.createInitialData(getActivity());
        }
    }

}
