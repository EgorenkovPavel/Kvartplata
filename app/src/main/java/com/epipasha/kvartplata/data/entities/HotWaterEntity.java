package com.epipasha.kvartplata.data.entities;

import com.epipasha.kvartplata.BR;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;

public class HotWaterEntity extends BaseObservable{

    @ColumnInfo(name = "past_value")
    private int pastValue;

    @ColumnInfo(name = "present_value")
    private int presentValue;

    @ColumnInfo(name = "tax")
    private int tax;

    @ColumnInfo(name = "sum")
    private int sum;

    @Ignore
    private int delta;

    public void setPastValue(int pastValue) {
        this.pastValue = pastValue;
        calculate();
    }

    public void setPresentValue(int presentValue) {
        this.presentValue = presentValue;
        calculate();
    }

    public void setTax(int tax) {
        this.tax = tax;
        calculate();
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    @Bindable
    public int getDelta() {
        return delta;
    }

    @Bindable
    public int getPastValue() {
        return pastValue;
    }

    @Bindable
    public int getPresentValue() {
        return presentValue;
    }

    @Bindable
    public int getTax() {
        return tax;
    }

    @Bindable
    public int getSum() {
        return sum;
    }

    private void calculate(){
        delta = presentValue - pastValue;
        sum = delta > 0 ? tax * delta : 0;
        notifyPropertyChanged(BR.delta);
        notifyPropertyChanged(BR.sum);
    }
}
