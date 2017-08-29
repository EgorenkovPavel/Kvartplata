package com.epipasha.kvartplata;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.epipasha.kvartplata.data.BillDetailLoader;
import com.epipasha.kvartplata.data.KvartplataContract.BillEntry;
import com.epipasha.kvartplata.data.KvartplataContract.ColdWaterEntry;
import com.epipasha.kvartplata.data.KvartplataContract.HotWaterEntry;
import com.epipasha.kvartplata.data.KvartplataContract.ElectricityEntry;
import com.epipasha.kvartplata.data.KvartplataContract.CanalizationEntry;
import com.epipasha.kvartplata.data.KvartplataDbHelper;
import com.epipasha.kvartplata.data.KvartplataDbManager;

public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private final static int DETAIL_LOADER_ID = 0;

    public final static String DB_ID = "db_id";

    private int billId;
    private int month;
    private int year;
    private boolean isNew = false;

    private EditText hotWaterTax;
    private EditText hotWaterValue;
    private TextView hotWaterSum;
    private TextView hotWaterPrevTax, hotWaterPrevValue, hotWaterPrevSum;

    private EditText coldWaterTax;
    private EditText coldWaterValue;
    private TextView coldWaterSum;
    private TextView coldWaterPrevTax, coldWaterPrevValue, coldWaterPrevSum;

    private EditText canalizationTax;
    private TextView canalizationValue;
    private TextView canalizationSum;
    private TextView canalizationPrevTax, canalizationPrevValue, canalizationPrevSum;

    private EditText electricityTax;
    private EditText electricityValue;
    private TextView electricitySum;
    private TextView electricityPrevTax, electricityPrevValue, electricityPrevSum;

    private TextView totalSum;
    private TextView header;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            calc();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_detail, container, false);

        billId = getArguments().getInt(DB_ID);
        if(billId == 0){
            billId = (int) KvartplataDbManager.createBill(getActivity());
            isNew = true;
        };

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String displayHotWaterKey = getString(R.string.pref_enable_hot_water_key);
        String displayColdWaterKey = getString(R.string.pref_enable_cold_water_key);
        String displayCanalizationKey = getString(R.string.pref_enable_canalization_key);
        String displayElectrisityKey = getString(R.string.pref_enable_electricity_key);

        boolean displayHotWater = prefs.getBoolean(displayHotWaterKey,
                Boolean.parseBoolean(getString(R.string.pref_enable_hot_water_default)));

        boolean displayColdWater = prefs.getBoolean(displayColdWaterKey,
                Boolean.parseBoolean(getString(R.string.pref_enable_cold_water_default)));

        boolean displayCanalization = prefs.getBoolean(displayCanalizationKey,
                Boolean.parseBoolean(getString(R.string.pref_enable_canalization_default)));

        boolean displayElectrisity = prefs.getBoolean(displayElectrisityKey,
                Boolean.parseBoolean(getString(R.string.pref_enable_electricity_default)));

        ViewGroup hotWaterGroup = (ViewGroup) v.findViewById(R.id.hot_water_group);
        ViewGroup coldWaterGroup = (ViewGroup) v.findViewById(R.id.cold_water_group);
        ViewGroup canalizationGroup = (ViewGroup) v.findViewById(R.id.canalization_group);
        ViewGroup electricityGroup = (ViewGroup) v.findViewById(R.id.electricity_group);

        if(displayHotWater){
            hotWaterGroup.setVisibility(View.VISIBLE);
        }else{
            hotWaterGroup.setVisibility(View.GONE);
        }

        if(displayColdWater){
            coldWaterGroup.setVisibility(View.VISIBLE);
        }else{
            coldWaterGroup.setVisibility(View.GONE);
        }

        if(displayCanalization){
            canalizationGroup.setVisibility(View.VISIBLE);
        }else{
            canalizationGroup.setVisibility(View.GONE);
        }

        if(displayElectrisity){
            electricityGroup.setVisibility(View.VISIBLE);
        }else{
            electricityGroup.setVisibility(View.GONE);
        }

        hotWaterTax = (EditText) v.findViewById(R.id.hot_water_tax);
        hotWaterValue = (EditText) v.findViewById(R.id.hot_water_value);
        hotWaterSum = (TextView) v.findViewById(R.id.hot_water_sum);
        hotWaterPrevTax = (TextView) v.findViewById(R.id.hot_water_prev_tax);
        hotWaterPrevValue = (TextView) v.findViewById(R.id.hot_water_prev_value);
        hotWaterPrevSum = (TextView) v.findViewById(R.id.hot_water_prev_sum);

        coldWaterTax = (EditText) v.findViewById(R.id.cold_water_tax);
        coldWaterValue = (EditText) v.findViewById(R.id.cold_water_value);
        coldWaterSum = (TextView) v.findViewById(R.id.cold_water_sum);
        coldWaterPrevTax = (TextView)v.findViewById(R.id.cold_water_prev_tax);
        coldWaterPrevValue = (TextView)v.findViewById(R.id.cold_water_prev_value);
        coldWaterPrevSum = (TextView)v.findViewById(R.id.cold_water_prev_sum);

        canalizationTax = (EditText) v.findViewById(R.id.canalization_tax);
        canalizationValue = (TextView) v.findViewById(R.id.canalization_value);
        canalizationSum = (TextView) v.findViewById(R.id.canalization_sum);
        canalizationPrevTax = (TextView) v.findViewById(R.id.canalization_prev_tax);
        canalizationPrevValue = (TextView) v.findViewById(R.id.canalization_prev_value);
        canalizationPrevSum = (TextView) v.findViewById(R.id.canalization_prev_sum);

        electricityTax = (EditText) v.findViewById(R.id.electricity_tax);
        electricityValue = (EditText) v.findViewById(R.id.electricity_value);
        electricitySum = (TextView) v.findViewById(R.id.electricity_sum);
        electricityPrevTax = (TextView) v.findViewById(R.id.electricity_prev_tax);
        electricityPrevValue = (TextView) v.findViewById(R.id.electricity_prev_value);
        electricityPrevSum = (TextView) v.findViewById(R.id.electricity_prev_sum);

        totalSum = (TextView)v.findViewById(R.id.totalSum);
        header = (TextView)v.findViewById(R.id.header);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(DETAIL_LOADER_ID, null, this).forceLoad();
    }

    @Override
    public void onPause() {
        super.onPause();

        KvartplataDbHelper dbHelper = new KvartplataDbHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        updateHotWater(db);
        updateColdWater(db);
        updateCanalization(db);
        updateElectricity(db);

        updateBill(db);

        db.close();
        dbHelper.close();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new BillDetailLoader(getActivity(), billId);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if(data.moveToFirst()) {

            month = KvartplataDbManager.getMonth(billId);
            year = KvartplataDbManager.getYear(billId);

            setValue(hotWaterTax, data, HotWaterEntry.COLUMN_TAX);
            setValue(hotWaterValue, data, HotWaterEntry.COLUMN_VALUE);
            setValue(hotWaterSum, data, HotWaterEntry.COLUMN_SUM);

            setValue(coldWaterTax, data, ColdWaterEntry.COLUMN_TAX);
            setValue(coldWaterValue, data, ColdWaterEntry.COLUMN_VALUE);
            setValue(coldWaterSum, data, ColdWaterEntry.COLUMN_SUM);

            setValue(canalizationTax, data, CanalizationEntry.COLUMN_TAX);
            setValue(canalizationSum, data, CanalizationEntry.COLUMN_SUM);

            setValue(electricityTax, data, ElectricityEntry.COLUMN_TAX);
            setValue(electricityValue, data, ElectricityEntry.COLUMN_VALUE);
            setValue(electricitySum, data, ElectricityEntry.COLUMN_SUM);
        }

        if(data.moveToNext()){

            setValue(hotWaterPrevTax, data, HotWaterEntry.COLUMN_TAX);
            setValue(hotWaterPrevValue, data, HotWaterEntry.COLUMN_VALUE);
            setValue(hotWaterPrevSum, data, HotWaterEntry.COLUMN_SUM);

            setValue(coldWaterPrevTax, data, ColdWaterEntry.COLUMN_TAX);
            setValue(coldWaterPrevValue, data, ColdWaterEntry.COLUMN_VALUE);
            setValue(coldWaterPrevSum, data, ColdWaterEntry.COLUMN_SUM);

            setValue(canalizationPrevTax, data, CanalizationEntry.COLUMN_TAX);
            setValue(canalizationPrevValue, data, CanalizationEntry.COLUMN_VALUE);
            setValue(canalizationPrevSum, data, CanalizationEntry.COLUMN_SUM);

            setValue(electricityPrevTax, data, ElectricityEntry.COLUMN_TAX);
            setValue(electricityPrevValue, data, ElectricityEntry.COLUMN_VALUE);
            setValue(electricityPrevSum, data, ElectricityEntry.COLUMN_SUM);

            if(isNew) {
                setValue(hotWaterTax, data, HotWaterEntry.COLUMN_TAX);
                setValue(coldWaterTax, data, ColdWaterEntry.COLUMN_TAX);
                setValue(canalizationTax, data, CanalizationEntry.COLUMN_TAX);
                setValue(electricityTax, data, ElectricityEntry.COLUMN_TAX);
            }
        }

        setHeader();
        calc();

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void updateHotWater(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(HotWaterEntry.COLUMN_TAX, Utils.getValue(hotWaterTax));
        values.put(HotWaterEntry.COLUMN_VALUE, Utils.getValue(hotWaterValue));
        values.put(HotWaterEntry.COLUMN_SUM, Utils.getValue(hotWaterSum));
        values.put(HotWaterEntry.COLUMN_BILL, billId);

        String where = HotWaterEntry.TABLE_NAME + "." + HotWaterEntry.COLUMN_BILL + " = " + billId;

        long i = db.update(HotWaterEntry.TABLE_NAME, values, where, null);
    }

    private void updateColdWater(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(ColdWaterEntry.COLUMN_TAX, Utils.getValue(coldWaterTax));
        values.put(ColdWaterEntry.COLUMN_VALUE, Utils.getValue(coldWaterValue));
        values.put(ColdWaterEntry.COLUMN_SUM, Utils.getValue(coldWaterSum));
        values.put(ColdWaterEntry.COLUMN_BILL, billId);

        String where = ColdWaterEntry.TABLE_NAME + "." + ColdWaterEntry.COLUMN_BILL + " = " + billId;

        long i = db.update(ColdWaterEntry.TABLE_NAME, values, where, null);
    }

    private void updateCanalization(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(CanalizationEntry.COLUMN_TAX, Utils.getValue(canalizationTax));
        values.put(CanalizationEntry.COLUMN_VALUE, Utils.getValue(canalizationValue));
        values.put(CanalizationEntry.COLUMN_SUM, Utils.getValue(canalizationSum));
        values.put(CanalizationEntry.COLUMN_BILL, billId);

        String where = CanalizationEntry.TABLE_NAME + "." + CanalizationEntry.COLUMN_BILL + " = " + billId;

        long i = db.update(CanalizationEntry.TABLE_NAME, values, where, null);
    }

    private void updateElectricity(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(ElectricityEntry.COLUMN_TAX, Utils.getValue(electricityTax));
        values.put(ElectricityEntry.COLUMN_VALUE, Utils.getValue(electricityValue));
        values.put(ElectricityEntry.COLUMN_SUM, Utils.getValue(electricitySum));
        values.put(ElectricityEntry.COLUMN_BILL, billId);

        String where = ElectricityEntry.TABLE_NAME + "." + ElectricityEntry.COLUMN_BILL + " = " + billId;

        long i = db.update(ElectricityEntry.TABLE_NAME, values, where, null);
    }

    private void updateBill(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(BillEntry.COLUMN_KEY, billId);
        values.put(BillEntry.COLUMN_SUM, Utils.getValue(totalSum));

        String where = BillEntry.TABLE_NAME + "." + BillEntry.COLUMN_KEY + " = " + billId;

        long i = db.update(BillEntry.TABLE_NAME, values, where, null);
    }


    private void setHeader() {
        header.setText(KvartplataDbManager.getBillDate(month, year));
    }

    private void setValue(TextView view, Cursor data, String column){
        view.setText(KvartplataDbManager.getValue(data, column));

        if(view instanceof EditText){
            view.addTextChangedListener(textWatcher);
        }
    }

    private void calc(){

        long hotWaterVal = Math.max(Utils.getValue(hotWaterValue) - Utils.getValue(hotWaterPrevValue), 0);
        long coldWaterVal = Math.max(Utils.getValue(coldWaterValue) - Utils.getValue(coldWaterPrevValue), 0);

        long electricityVal = Math.max(Utils.getValue(electricityValue) - Utils.getValue(electricityPrevValue), 0);

        canalizationValue.setText(String.valueOf(hotWaterVal + coldWaterVal));

        long hotWaterTotal = Utils.getValue(hotWaterTax) * hotWaterVal;
        long coldWaterTotal = Utils.getValue(coldWaterTax) * coldWaterVal;
        long canalizationTotal = Utils.getValue(canalizationTax) * Utils.getValue(canalizationValue);
        long electricityTotal = Utils.getValue(electricityTax) * electricityVal;

        hotWaterSum.setText(String.valueOf(hotWaterTotal));
        coldWaterSum.setText(String.valueOf(coldWaterTotal));
        canalizationSum.setText(String.valueOf(canalizationTotal));
        electricitySum.setText(String.valueOf(electricityTotal));

        long total = hotWaterTotal + coldWaterTotal + canalizationTotal + electricityTotal;
        totalSum.setText(String.valueOf(total));
    }
}
