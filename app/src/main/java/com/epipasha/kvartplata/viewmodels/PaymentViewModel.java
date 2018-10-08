package com.epipasha.kvartplata.viewmodels;

import android.app.Application;

import com.epipasha.kvartplata.BR;
import com.epipasha.kvartplata.data.DataSource;
import com.epipasha.kvartplata.data.Repository;
import com.epipasha.kvartplata.data.entities.ColdWaterEntity;
import com.epipasha.kvartplata.data.entities.DrainEntity;
import com.epipasha.kvartplata.data.entities.ElectricityEntity;
import com.epipasha.kvartplata.data.entities.HotWaterEntity;
import com.epipasha.kvartplata.data.entities.InternetEntity;
import com.epipasha.kvartplata.data.entities.PaymentEntity;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;

public class PaymentViewModel extends AndroidViewModel {

    private Repository mRepository;
    private int mPaymentId;

    private ObservableInt mSum = new ObservableInt();
    private ObservableField<Date> mDate = new ObservableField<>();

    private ColdWaterEntity coldWater;
    private HotWaterEntity hotWater;
    private DrainEntity drain;
    private ElectricityEntity electricity;
    private InternetEntity internet;

    private Observable.OnPropertyChangedCallback mWaterDeltaCallback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            if (propertyId == BR.delta){
                setDrainValue();
            }
        }
    };

    private Observable.OnPropertyChangedCallback mSumCallback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            if (propertyId == BR.sum){
                setTotalSum();
            }
        }
    };

    public PaymentViewModel(@NonNull Application application, Repository repository) {
        super(application);

        mRepository = repository;
        init(null);
    }

    private void init(PaymentEntity payment){
        if (payment != null){
            coldWater = payment.getColdWater();
            hotWater = payment.getHotWater();
            drain = payment.getDrain();
            electricity = payment.getElectricity();
            internet = payment.getInternet();

            mDate.set(payment.getDate());
            mSum.set(payment.getSum());
        }else{
            coldWater = new ColdWaterEntity();
            hotWater = new HotWaterEntity();
            drain = new DrainEntity();
            electricity = new ElectricityEntity();
            internet = new InternetEntity();

            mDate.set(new Date());
            mSum.set(0);
        }

        coldWater.addOnPropertyChangedCallback(mWaterDeltaCallback);
        hotWater.addOnPropertyChangedCallback(mWaterDeltaCallback);

        coldWater.addOnPropertyChangedCallback(mSumCallback);
        hotWater.addOnPropertyChangedCallback(mSumCallback);
        drain.addOnPropertyChangedCallback(mSumCallback);
        electricity.addOnPropertyChangedCallback(mSumCallback);
        internet.addOnPropertyChangedCallback(mSumCallback);
    }

    private void setTotalSum() {
        int coldWaterSum = coldWater == null ? 0 : coldWater.getSum();
        int hotWaterSum = hotWater == null ? 0 : hotWater.getSum();
        int drainSum = drain == null ? 0 : drain.getSum();
        int electricitySum = electricity == null ? 0 : electricity.getSum();
        int internetSum = internet == null ? 0 : internet.getSum();

        int sum = coldWaterSum + hotWaterSum + drainSum + electricitySum + internetSum;
        mSum.set(sum);
    }

    private void setDrainValue(){
        drain.setValue(coldWater.getDelta() + hotWater.getDelta());
    }

    public void start(int paymentId) {

        mPaymentId = paymentId;
        mRepository.getPayment(paymentId, new DataSource.GetPaymentCallback() {
            @Override
            public void onSuccess(PaymentEntity entity) {
                init(entity);
            }

            @Override
            public void onFailed() {

            }
        });
    }

    public ObservableField<Date> getDate() {
        return mDate;
    }

    public ObservableInt getSum() {
        return mSum;
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

    public ElectricityEntity getElectricity() {
        return electricity;
    }

    public InternetEntity getInternet() {
        return internet;
    }

    public void save() {
        PaymentEntity payment = new PaymentEntity(mPaymentId, 1, 1, mSum.get());
        payment.setColdWater(coldWater);
        payment.setHotWater(hotWater);
        payment.setDrain(drain);
        payment.setElectricity(electricity);
        payment.setInternet(internet);
        mRepository.savePayment(payment);
    }
}
