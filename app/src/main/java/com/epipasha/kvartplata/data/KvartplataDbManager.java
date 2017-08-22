package com.epipasha.kvartplata.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.epipasha.kvartplata.data.KvartplataContract.BillEntry;
import com.epipasha.kvartplata.data.KvartplataContract.CanalizationEntry;
import com.epipasha.kvartplata.data.KvartplataContract.ColdWaterEntry;
import com.epipasha.kvartplata.data.KvartplataContract.ElectricityEntry;
import com.epipasha.kvartplata.data.KvartplataContract.HotWaterEntry;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class KvartplataDbManager {

    private static final SQLiteQueryBuilder totalSumQueryBuilder;

    static{
        totalSumQueryBuilder = new SQLiteQueryBuilder();

        totalSumQueryBuilder.setTables(
                BillEntry.TABLE_NAME +
                        " LEFT JOIN " + HotWaterEntry.TABLE_NAME +
                        " ON " + BillEntry.TABLE_NAME + "." + BillEntry._ID +
                        " = " + HotWaterEntry.TABLE_NAME + "." + HotWaterEntry.COLUMN_BILL +

                        " LEFT JOIN " + ColdWaterEntry.TABLE_NAME +
                        " ON " + BillEntry.TABLE_NAME + "." + BillEntry._ID +
                        " = " + ColdWaterEntry.TABLE_NAME + "." + ColdWaterEntry.COLUMN_BILL +

                        " LEFT JOIN " + CanalizationEntry.TABLE_NAME +
                        " ON " + BillEntry.TABLE_NAME + "." + BillEntry._ID +
                        " = " + CanalizationEntry.TABLE_NAME + "." + CanalizationEntry.COLUMN_BILL +

                        " LEFT JOIN " + ElectricityEntry.TABLE_NAME +
                        " ON " + BillEntry.TABLE_NAME + "." + BillEntry._ID +
                        " = " + ElectricityEntry.TABLE_NAME + "." + ElectricityEntry.COLUMN_BILL);
    }

    public static long createBill(Context context){
        KvartplataDbHelper dbHelper = new KvartplataDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c = db.query(BillEntry.TABLE_NAME,
                new String[]{"COUNT(*)", "MAX("+BillEntry.COLUMN_MONTH + ")", BillEntry.COLUMN_YEAR},
                BillEntry.COLUMN_YEAR + " = " + "(SELECT max(" + BillEntry.COLUMN_YEAR + ") FROM " + BillEntry.TABLE_NAME + ")",
                null,null, null, null);


        Calendar d = Calendar.getInstance();;

        int month = d.get(Calendar.MONTH) + 1;
        int year = d.get(Calendar.YEAR);

        if(c.moveToFirst() && c.getInt(0) > 0){
            month = c.getInt(1);
            year = c.getInt(2);

            if (month == 12){
                month = 1;
                year += 1;
            }else{
                month += 1;
            }
        }

        ContentValues values = new ContentValues();
        values.put(KvartplataContract.BillEntry.COLUMN_MONTH, month);
        values.put(KvartplataContract.BillEntry.COLUMN_YEAR, year);

        int billId = (int)db.insert(BillEntry.TABLE_NAME, null, values);

        ContentValues hotWaterValues = new ContentValues();
        hotWaterValues.put(HotWaterEntry.COLUMN_BILL, billId);
        db.insert(HotWaterEntry.TABLE_NAME, null, hotWaterValues);

        ContentValues coldWaterValues = new ContentValues();
        coldWaterValues.put(ColdWaterEntry.COLUMN_BILL, billId);
        db.insert(ColdWaterEntry.TABLE_NAME, null, coldWaterValues);

        ContentValues canalizationValues = new ContentValues();
        coldWaterValues.put(CanalizationEntry.COLUMN_BILL, billId);
        db.insert(CanalizationEntry.TABLE_NAME, null, canalizationValues);

        ContentValues electricityValues = new ContentValues();
        coldWaterValues.put(ElectricityEntry.COLUMN_BILL, billId);
        db.insert(ElectricityEntry.TABLE_NAME, null, electricityValues);

        return billId;
    }

    public static Cursor getDetailData(Context context, int billId){
        KvartplataDbHelper dbHelper = new KvartplataDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = totalSumQueryBuilder.query(db,null,
                BillEntry.TABLE_NAME + "." + BillEntry._ID + " = " + billId,null,null,null,null);
        c.moveToFirst();
        return c;
    }

    public static Cursor getBillData(Context context, String[] columns){
        KvartplataDbHelper dbHelper = new KvartplataDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = totalSumQueryBuilder.query(db,columns,null,null,null,null,null);
        return c;
    }


    public static String getBillDate(int month, int year){
        Calendar c = Calendar.getInstance();
        c.set(year, month-1, 1);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
        return dateFormat.format(c.getTime());
    }

    public static String getValue(Cursor cursor, String column){
        return String.valueOf(cursor.getLong(cursor.getColumnIndex(column)));
    }


}
