package com.epipasha.kvartplata.viewmodels;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import com.epipasha.kvartplata.BR;
import com.epipasha.kvartplata.R;
import com.epipasha.kvartplata.data.DataSource;
import com.epipasha.kvartplata.data.Repository;
import com.epipasha.kvartplata.data.entities.ColdWaterEntity;
import com.epipasha.kvartplata.data.entities.DrainEntity;
import com.epipasha.kvartplata.data.entities.ElectricityEntity;
import com.epipasha.kvartplata.data.entities.HotWaterEntity;
import com.epipasha.kvartplata.data.entities.InternetEntity;
import com.epipasha.kvartplata.data.entities.PaymentEntity;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
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

    private ObservableBoolean showColdWater = new ObservableBoolean();
    private ObservableBoolean showHotWater = new ObservableBoolean();
    private ObservableBoolean showDrain = new ObservableBoolean();
    private ObservableBoolean showElectricity = new ObservableBoolean();
    private ObservableBoolean showInternet = new ObservableBoolean();

    private SingleLiveEvent<Action> mAction = new SingleLiveEvent<>();

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

        getPrefs();

    }

    private void getPrefs() {

        String keyColdWater = getApplication().getString(R.string.pref_enable_cold_water_key);
        String keyHotWater = getApplication().getString(R.string.pref_enable_hot_water_key);
        String keyDrain = getApplication().getString(R.string.pref_enable_drain_key);
        String keyElectricity = getApplication().getString(R.string.pref_enable_electricity_key);
        String keyInternet = getApplication().getString(R.string.pref_enable_internet_key);

        Resources res = getApplication().getResources();
        boolean defColdWater = res.getBoolean(R.bool.pref_enable_cold_water_default);
        boolean defHotWater = res.getBoolean(R.bool.pref_enable_hot_water_default);
        boolean defDrain = res.getBoolean(R.bool.pref_enable_drain_default);
        boolean defElectricity = res.getBoolean(R.bool.pref_enable_electricity_default);
        boolean defInternet = res.getBoolean(R.bool.pref_enable_internet_default);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplication());
        showColdWater.set(sharedPref.getBoolean(keyColdWater, defColdWater));
        showHotWater.set(sharedPref.getBoolean(keyHotWater, defHotWater));
        showDrain.set(sharedPref.getBoolean(keyDrain, defDrain));
        showElectricity.set(sharedPref.getBoolean(keyElectricity, defElectricity));
        showInternet.set(sharedPref.getBoolean(keyInternet, defInternet));

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

            mRepository.getPreviousPayment(payment.getMonth(), payment.getYear(), new DataSource.GetPaymentCallback() {
                @Override
                public void onSuccess(PaymentEntity entity) {

                }

                @Override
                public void onFailed() {

                }
            });
        }else{
            coldWater = new ColdWaterEntity();
            hotWater = new HotWaterEntity();
            drain = new DrainEntity();
            electricity = new ElectricityEntity();
            internet = new InternetEntity();

            mDate.set(new Date());
            mSum.set(0);

            mRepository.getLastPayment(new DataSource.GetPaymentCallback() {
                @Override
                public void onSuccess(PaymentEntity entity) {
                    if(entity == null) return;

                    mDate.set(nextDate(entity.getDate()));

                    coldWater.setPastValue(entity.getColdWater().getPresentValue());
                    coldWater.setTax(entity.getColdWater().getTax());

                    hotWater.setPastValue(entity.getHotWater().getPresentValue());
                    hotWater.setTax(entity.getHotWater().getTax());

                    drain.setTax(entity.getDrain().getTax());

                    electricity.setPastValue(entity.getElectricity().getPresentValue());
                    electricity.setTax(entity.getElectricity().getTax());

                    internet.setSum(entity.getInternet().getSum());
                }

                @Override
                public void onFailed() {

                }
            });
        }

        coldWater.addOnPropertyChangedCallback(mWaterDeltaCallback);
        hotWater.addOnPropertyChangedCallback(mWaterDeltaCallback);

        coldWater.addOnPropertyChangedCallback(mSumCallback);
        hotWater.addOnPropertyChangedCallback(mSumCallback);
        drain.addOnPropertyChangedCallback(mSumCallback);
        electricity.addOnPropertyChangedCallback(mSumCallback);
        internet.addOnPropertyChangedCallback(mSumCallback);
    }

    private Date nextDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        return cal.getTime();
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

    public void start() {
        init(null);
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

    public SingleLiveEvent<Action> getAction() {
        return mAction;
    }

    public ObservableBoolean getShowColdWater() {
        return showColdWater;
    }

    public ObservableBoolean getShowHotWater() {
        return showHotWater;
    }

    public ObservableBoolean getShowDrain() {
        return showDrain;
    }

    public ObservableBoolean getShowElectricity() {
        return showElectricity;
    }

    public ObservableBoolean getShowInternet() {
        return showInternet;
    }

    public void save() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(mDate.get());
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR) - 1900;

        PaymentEntity payment = new PaymentEntity(mPaymentId, month, year, mSum.get());
        payment.setColdWater(coldWater);
        payment.setHotWater(hotWater);
        payment.setDrain(drain);
        payment.setElectricity(electricity);
        payment.setInternet(internet);
        mRepository.savePayment(payment);
    }

    public void setDate(final Date date) {

        mRepository.getPaymentByDate(date, new DataSource.GetPaymentCallback() {
            @Override
            public void onSuccess(PaymentEntity entity) {
                if(entity != null && entity.getId() != mPaymentId){
                    mAction.postValue(Action.SHOW_MES_PAYMENT_DATE_EXISTS);
                }else{
                    mDate.set(date);
                }
            }

            @Override
            public void onFailed() {

            }
        });
    }

    public enum Action {
        SHOW_MES_PAYMENT_DATE_EXISTS
    }

}
