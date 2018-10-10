package com.epipasha.kvartplata.data;

import com.epipasha.kvartplata.data.entities.PaymentEntity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class LocalDataSource {

    private static volatile LocalDataSource INSTANCE;

    private AppDatabase mDb;
    private AppExecutors mAppExecutors;

    private LocalDataSource(@NonNull AppDatabase db) {
        mAppExecutors = AppExecutors.getInstance();
        mDb = db;
    }

    public static LocalDataSource getInstance(@NonNull AppDatabase db) {
        if (INSTANCE == null) {
            synchronized (LocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalDataSource(db);
                }
            }
        }
        return INSTANCE;
    }

    public LiveData<List<PaymentEntity>> getPayments() {
        return mDb.paymentDao().getAll();
    }

    public void getPayment(final int paymentId, final DataSource.GetPaymentCallback callback) {
        mAppExecutors.discIO().execute(new Runnable() {
            @Override
            public void run() {
                PaymentEntity payment = mDb.paymentDao().getPayment(paymentId);
                callback.onSuccess(payment);
            }
        });
    }

    public void getLastPayment(final DataSource.GetPaymentCallback callback) {
        mAppExecutors.discIO().execute(new Runnable() {
            @Override
            public void run() {
                PaymentEntity payment = mDb.paymentDao().getLastPayment();
                callback.onSuccess(payment);
            }
        });
    }

    public void getPreviousPayment(final int month, final int year, final DataSource.GetPaymentCallback callback) {
        mAppExecutors.discIO().execute(new Runnable() {
            @Override
            public void run() {
                PaymentEntity payment = mDb.paymentDao().getPreviousPayment(month, year);
                callback.onSuccess(payment);
            }
        });
    }

    public void savePayment(final PaymentEntity payment) {
        mAppExecutors.discIO().execute(new Runnable() {
            @Override
            public void run() {
                if(payment.getId() == 0) {
                    mDb.paymentDao().insertPayment(payment);
                }else{
                    mDb.paymentDao().updatePayment(payment);
                }
            }
        });
    }

    public void getPaymentByDate(final Date date, final DataSource.GetPaymentCallback callback) {
        mAppExecutors.discIO().execute(new Runnable() {
            @Override
            public void run() {
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR) - 1900;

                PaymentEntity payment = mDb.paymentDao().getPaymentByDate(month, year);
                callback.onSuccess(payment);
            }
        });

    }
}
