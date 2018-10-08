package com.epipasha.kvartplata.data.entities;

import com.epipasha.kvartplata.BR;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;

public class InternetEntity extends BaseObservable{

    @ColumnInfo(name = "sum")
    private int sum;

    public void setSum(int sum) {
        this.sum = sum;
        notifyPropertyChanged(BR.sum);
    }

    @Bindable
    public int getSum() {
        return sum;
    }

}
