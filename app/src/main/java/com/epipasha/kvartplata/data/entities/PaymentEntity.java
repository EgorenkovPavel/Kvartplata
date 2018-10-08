package com.epipasha.kvartplata.data.entities;

import com.epipasha.kvartplata.BR;

import java.util.Date;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
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

    @Embedded(prefix = "hot_water")
    private HotWaterEntity hotWater;

    @Embedded(prefix = "drain")
    private DrainEntity drain;

    @Embedded(prefix = "electricity")
    private ElectricityEntity electricity;

    @Embedded(prefix = "internet")
    private InternetEntity internet;

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
    }

    public HotWaterEntity getHotWater() {
        return hotWater;
    }

    public void setHotWater(HotWaterEntity hotWater) {
        this.hotWater = hotWater;
    }

    public DrainEntity getDrain() {
        return drain;
    }

    public void setDrain(DrainEntity drain) {
        this.drain = drain;
    }

    public ElectricityEntity getElectricity() {
        return electricity;
    }

    public void setElectricity(ElectricityEntity electricity) {
        this.electricity = electricity;
    }

    public InternetEntity getInternet() {
        return internet;
    }

    public void setInternet(InternetEntity internet) {
        this.internet = internet;
    }

}
