package com.epipasha.kvartplata.data;

import java.util.List;

import androidx.lifecycle.LiveData;

public class Repository {

    private volatile static Repository INSTANCE = null;

    private LocalDataSource mLocalDataSource;

    private Repository(LocalDataSource localDataSource){
        mLocalDataSource = localDataSource;
    }

    public static Repository getInstance(LocalDataSource localDataSource) {
        if (INSTANCE == null) {
            synchronized (Repository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Repository(localDataSource);
                }
            }
        }
        return INSTANCE;
    }

    public LiveData<List<PaymentEntity>> getPayments() {
        return mLocalDataSource.getPayments();
    }

    public void getPayment(int paymentId, DataSource.GetPaymentCallback callback) {
        mLocalDataSource.getPayment(paymentId, callback);
    }

    public void savePayment(PaymentEntity payment) {
        mLocalDataSource.savePayment(payment);
    }
}
