package com.epipasha.kvartplata.data;

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
}
