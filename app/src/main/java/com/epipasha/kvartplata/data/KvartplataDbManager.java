package com.epipasha.kvartplata.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.epipasha.kvartplata.Utils;
import com.epipasha.kvartplata.data.KvartplataContract.BillEntry;
import com.epipasha.kvartplata.data.KvartplataContract.CanalizationEntry;
import com.epipasha.kvartplata.data.KvartplataContract.ColdWaterEntry;
import com.epipasha.kvartplata.data.KvartplataContract.ElectricityEntry;
import com.epipasha.kvartplata.data.KvartplataContract.HotWaterEntry;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class KvartplataDbManager {

    private static final SQLiteQueryBuilder totalSumQueryBuilder;

    public static final int initialBillId = makeKey(0,0);

    static{
        totalSumQueryBuilder = new SQLiteQueryBuilder();

        totalSumQueryBuilder.setTables(
                BillEntry.TABLE_NAME +
                        " LEFT JOIN " + HotWaterEntry.TABLE_NAME +
                        " ON " + BillEntry.TABLE_NAME + "." + BillEntry.COLUMN_KEY +
                        " = " + HotWaterEntry.TABLE_NAME + "." + HotWaterEntry.COLUMN_BILL +

                        " LEFT JOIN " + ColdWaterEntry.TABLE_NAME +
                        " ON " + BillEntry.TABLE_NAME + "." + BillEntry.COLUMN_KEY +
                        " = " + ColdWaterEntry.TABLE_NAME + "." + ColdWaterEntry.COLUMN_BILL +

                        " LEFT JOIN " + CanalizationEntry.TABLE_NAME +
                        " ON " + BillEntry.TABLE_NAME + "." + BillEntry.COLUMN_KEY +
                        " = " + CanalizationEntry.TABLE_NAME + "." + CanalizationEntry.COLUMN_BILL +

                        " LEFT JOIN " + ElectricityEntry.TABLE_NAME +
                        " ON " + BillEntry.TABLE_NAME + "." + BillEntry.COLUMN_KEY +
                        " = " + ElectricityEntry.TABLE_NAME + "." + ElectricityEntry.COLUMN_BILL);
    }

    public static long createBill(Context context){
        KvartplataDbHelper dbHelper = new KvartplataDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c = db.query(BillEntry.TABLE_NAME,
                new String[]{"COUNT(*)", BillEntry.COLUMN_KEY},
                null,null,null, null, BillEntry.COLUMN_KEY + " DESC", "1");

        Calendar d = Calendar.getInstance();;

        int month = d.get(Calendar.MONTH) + 1;
        int year = d.get(Calendar.YEAR);

        if(c.moveToFirst() && c.getInt(0) > 0){

            int key = c.getInt(1);
            month = KvartplataDbManager.getMonth(key);
            year = KvartplataDbManager.getYear(key);

            if (month == 12){
                month = 1;
                year += 1;
            }else{
                month += 1;
            }
        }

        int billId = KvartplataDbManager.makeKey(month, year);

        createBill(context, billId);

        return billId;
    }

    public static Cursor getBillData(Context context, String[] columns){
        KvartplataDbHelper dbHelper = new KvartplataDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = totalSumQueryBuilder.query(db,columns,
                BillEntry.TABLE_NAME +"."+BillEntry.COLUMN_KEY + " <> " + initialBillId,null,null,null,null);
        return c;
    }

    public static Cursor getDetailData(Context context, int billId){
        KvartplataDbHelper dbHelper = new KvartplataDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = totalSumQueryBuilder.query(db,null,
                BillEntry.TABLE_NAME + "." + BillEntry.COLUMN_KEY + " <= " + billId,null,null,null,BillEntry.COLUMN_KEY + " DESC","2");
        return c;
    }


    public static void createInitialData(Context context){
        createBill(context, initialBillId);
    }

    public static Cursor getInitialData(Context context){
        return getDetailData(context, initialBillId);
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


    public static int makeKey(int month, int year){
        return year*100 + month;
    }

    public static int getMonth(int key){
        return key % 100;
    }

    public static int getYear(int key){
        return key / 100;
    }

    private static void createBill(Context context, int billId){

        KvartplataDbHelper dbHelper = new KvartplataDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BillEntry.COLUMN_KEY, billId);

        db.insert(BillEntry.TABLE_NAME, null, values);

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

    }

}
