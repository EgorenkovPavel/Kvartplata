package com.epipasha.kvartplata.data.entities;

import com.epipasha.kvartplata.BR;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;

public class DrainEntity extends BaseObservable {

    @ColumnInfo(name = "value")
    private int value;

    @ColumnInfo(name = "tax")
    private int tax;

    @ColumnInfo(name = "sum")
    private int sum;

    public void setValue(int value) {
        this.value = value;
        notifyPropertyChanged(BR.value);
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
    public int getValue() {
        return value;
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
        sum = tax * value;
        notifyPropertyChanged(BR.sum);
    }
}
