package com.epipasha.kvartplata.data;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "payment", indices = {@Index(value = {"month", "year"}, unique = true)})
public class PaymentEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "month")
    private int month;

    @ColumnInfo(name = "year")
    private int year;

    @ColumnInfo(name = "sum")
    private int sum;

    @Embedded(prefix = "cold_water")
    private ColdWaterEntity coldWater;

    public PaymentEntity(int id, int month, int year, int sum) {
        this.id = id;
        this.month = month;
        this.year = year;
        this.sum = sum;
    }

    @Ignore
    public PaymentEntity(int id, int month, int year){
        this.id = id;
        this.month = month;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getSum() {
        return sum;
    }

    public Date getDate() {
        return new Date(year, month, 1);
    }

    public ColdWaterEntity getColdWater() {
        return coldWater;
    }

    public void setColdWater(ColdWaterEntity coldWater) {
        this.coldWater = coldWater;
        calcSum();
    }

    private void calcSum(){
        sum = coldWater == null ? 0 : coldWater.getSum();
    }
}