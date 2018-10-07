package com.epipasha.kvartplata.viewmodels;

import android.app.Application;

import com.epipasha.kvartplata.data.DataSource;
import com.epipasha.kvartplata.data.Repository;
import com.epipasha.kvartplata.data.entities.ColdWaterEntity;
import com.epipasha.kvartplata.data.entities.DrainEntity;
import com.epipasha.kvartplata.data.entities.HotWaterEntity;
import com.epipasha.kvartplata.data.entities.PaymentEntity;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class PaymentViewModel extends AndroidViewModel {

    private Repository mRepository;
    private int mPaymentId;

    private ColdWaterEntity coldWater;
    private HotWaterEntity hotWater;
    private DrainEntity drain;

    public PaymentViewModel(@NonNull Application application, Repository repository) {
        super(application);

        mRepository = repository;
        coldWater = new ColdWaterEntity();
        hotWater = new HotWaterEntity();
        drain = new DrainEntity();
    }

    public void start(int paymentId) {

        mPaymentId = paymentId;
        mRepository.getPayment(paymentId, new DataSource.GetPaymentCallback() {
            @Override
            public void onSuccess(PaymentEntity entity) {
                if (entity.getColdWater() != null){
                    coldWater = entity.getColdWater();
                    hotWater = entity.getHotWater();
                    drain = entity.getDrain();
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

    public HotWaterEntity getHotWater() {
        return hotWater;
    }

    public DrainEntity getDrain() {
        return drain;
    }

    public void save() {
        PaymentEntity payment = new PaymentEntity(mPaymentId, 1, 1);
        payment.setColdWater(coldWater);
        payment.setHotWater(hotWater);
        payment.setDrain(drain);
        mRepository.savePayment(payment);
    }
}
