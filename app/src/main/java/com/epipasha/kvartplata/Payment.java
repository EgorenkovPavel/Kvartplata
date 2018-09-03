package com.epipasha.kvartplata;

import java.util.Date;

public class Payment {

    private Date mDate;
    private long mSum;

    public Payment(Date date, long sum) {
        mDate = date;
        mSum = sum;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public long getSum() {
        return mSum;
    }

    public void setSum(long sum) {
        mSum = sum;
    }
}
