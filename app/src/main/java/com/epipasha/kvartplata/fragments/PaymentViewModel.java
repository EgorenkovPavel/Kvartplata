package com.epipasha.kvartplata.fragments;

import android.app.Application;

import com.epipasha.kvartplata.data.ColdWaterEntity;
import com.epipasha.kvartplata.data.DataSource;
import com.epipasha.kvartplata.data.PaymentEntity;
import com.epipasha.kvartplata.data.Repository;

import androidx.annotation.NonNull;
import androidx.databinding.InverseMethod;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;

public class PaymentViewModel extends AndroidViewModel {

    private Repository mRepository;
    private int mPaymentId;

    private ColdWaterEntity coldWater;

    public PaymentViewModel(@NonNull Application application, Repository repository) {
        super(application);

        mRepository = repository;
        coldWater = new ColdWaterEntity();
    }

    public void start(int paymentId) {

        mPaymentId = paymentId;
        mRepository.getPayment(paymentId, new DataSource.GetPaymentCallback() {
            @Override
            public void onSuccess(PaymentEntity entity) {
                if (entity.getColdWater() != null){
                    coldWater = entity.getColdWater();
                }
            }

            @Override
            public void onFailed() {

            }
        });
    }

    public ColdWaterEntity getColdWater() {
        return coldWater;
    }

    public void save() {
        PaymentEntity payment = new PaymentEntity(mPaymentId, 1, 1);
        payment.setColdWater(coldWater);
        mRepository.savePayment(payment);
    }
}
