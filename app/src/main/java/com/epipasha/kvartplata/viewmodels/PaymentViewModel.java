package com.epipasha.kvartplata.viewmodels;

import android.app.Application;

import com.epipasha.kvartplata.BR;
import com.epipasha.kvartplata.data.DataSource;
import com.epipasha.kvartplata.data.Repository;
import com.epipasha.kvartplata.data.entities.ColdWaterEntity;
import com.epipasha.kvartplata.data.entities.DrainEntity;
import com.epipasha.kvartplata.data.entities.HotWaterEntity;
import com.epipasha.kvartplata.data.entities.InternetEntity;
import com.epipasha.kvartplata.data.entities.PaymentEntity;
import com.epipasha.kvartplata.fragments.InternetFragment;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.lifecycle.AndroidViewModel;

public class PaymentViewModel extends AndroidViewModel {

    private Repository mRepository;
    private int mPaymentId;

    private ColdWaterEntity coldWater;
    private HotWaterEntity hotWater;
    private DrainEntity drain;
    private InternetEntity internet;

    public PaymentViewModel(@NonNull Application application, Repository repository) {
        super(application);

        mRepository = repository;
        coldWater = new ColdWaterEntity();
        hotWater = new HotWaterEntity();
        drain = new DrainEntity();
        internet = new InternetEntity();

        coldWater.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (propertyId == BR.delta){
                    setDrainValue();
                }
            }
        });
        hotWater.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (propertyId == BR.delta){
                    setDrainValue();
                }
            }
        });
    }

    private void setDrainValue(){
        drain.setValue(coldWater.getDelta() + hotWater.getDelta());
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
                    internet = entity.getInternet();

                    coldWater.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
                        @Override
                        public void onPropertyChanged(Observable sender, int propertyId) {
                            if (propertyId == BR.delta){
                                setDrainValue();
                            }
                        }
                    });
                    hotWater.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
                        @Override
                        public void onPropertyChanged(Observable sender, int propertyId) {
                            if (propertyId == BR.delta){
                                setDrainValue();
                            }
                        }
                    });
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

    public InternetEntity getInternet() {
        return internet;
    }

    public void save() {
        PaymentEntity payment = new PaymentEntity(mPaymentId, 1, 1);
        payment.setColdWater(coldWater);
        payment.setHotWater(hotWater);
        payment.setDrain(drain);
        payment.setInternet(internet);
        mRepository.savePayment(payment);
    }
}
