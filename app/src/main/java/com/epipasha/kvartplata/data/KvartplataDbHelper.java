package com.epipasha.kvartplata.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.epipasha.kvartplata.data.KvartplataContract.BillEntry;
import com.epipasha.kvartplata.data.KvartplataContract.ColdWaterEntry;
import com.epipasha.kvartplata.data.KvartplataContract.ElectricityEntry;
import com.epipasha.kvartplata.data.KvartplataContract.HotWaterEntry;
import com.epipasha.kvartplata.data.KvartplataContract.CanalizationEntry;

public class KvartplataDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "weather.db";

    public KvartplataDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_BILL_TABLE = "CREATE TABLE " + BillEntry.TABLE_NAME + " (" +
                BillEntry.COLUMN_KEY + " INTEGER PRIMARY KEY," +
                BillEntry.COLUMN_SUM + " REAL  DEFAULT (0))";

        sqLiteDatabase.execSQL(SQL_CREATE_BILL_TABLE);

        final String SQL_CREATE_HOTWATER_TABLE = "CREATE TABLE " + HotWaterEntry.TABLE_NAME + " (" +
                HotWaterEntry.COLUMN_BILL + " INTEGER NOT NULL, " +
                HotWaterEntry.COLUMN_TAX + " REAL DEFAULT (0), " +
                HotWaterEntry.COLUMN_VALUE + " REAL  DEFAULT (0), " +
                HotWaterEntry.COLUMN_SUM + " REAL  DEFAULT (0), " +
                " FOREIGN KEY (" + HotWaterEntry.COLUMN_BILL + ") REFERENCES " +
                BillEntry.TABLE_NAME + " (" + BillEntry.COLUMN_KEY + "))";

        sqLiteDatabase.execSQL(SQL_CREATE_HOTWATER_TABLE);

        final String SQL_CREATE_COLDWATER_TABLE = "CREATE TABLE " + ColdWaterEntry.TABLE_NAME + " (" +
                ColdWaterEntry.COLUMN_BILL + " INTEGER NOT NULL, " +
                ColdWaterEntry.COLUMN_TAX + " REAL  DEFAULT (0), " +
                ColdWaterEntry.COLUMN_VALUE + " REAL  DEFAULT (0), " +
                ColdWaterEntry.COLUMN_SUM + " REAL  DEFAULT (0), " +
                " FOREIGN KEY (" + ColdWaterEntry.COLUMN_BILL + ") REFERENCES " +
                BillEntry.TABLE_NAME + " (" + BillEntry.COLUMN_KEY + "))";

        sqLiteDatabase.execSQL(SQL_CREATE_COLDWATER_TABLE);

        final String SQL_CREATE_ELECTRICITY_TABLE = "CREATE TABLE " + ElectricityEntry.TABLE_NAME + " (" +
                ElectricityEntry.COLUMN_BILL + " INTEGER NOT NULL, " +
                ElectricityEntry.COLUMN_TAX + " REAL  DEFAULT (0), " +
                ElectricityEntry.COLUMN_VALUE + " REAL  DEFAULT (0), " +
                ElectricityEntry.COLUMN_SUM + " REAL  DEFAULT (0), " +
                " FOREIGN KEY (" + ElectricityEntry.COLUMN_BILL + ") REFERENCES " +
                BillEntry.TABLE_NAME + " (" + BillEntry.COLUMN_KEY + "))";

        sqLiteDatabase.execSQL(SQL_CREATE_ELECTRICITY_TABLE);

        final String SQL_CREATE_CANALIZATION_TABLE = "CREATE TABLE " + CanalizationEntry.TABLE_NAME + " (" +
                CanalizationEntry.COLUMN_BILL + " INTEGER NOT NULL, " +
                CanalizationEntry.COLUMN_TAX + " REAL  DEFAULT (0), " +
                CanalizationEntry.COLUMN_VALUE + " REAL  DEFAULT (0), " +
                CanalizationEntry.COLUMN_SUM + " REAL  DEFAULT (0), " +
                " FOREIGN KEY (" + CanalizationEntry.COLUMN_BILL + ") REFERENCES " +
                BillEntry.TABLE_NAME + " (" + BillEntry.COLUMN_KEY + "))";

        sqLiteDatabase.execSQL(SQL_CREATE_CANALIZATION_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
