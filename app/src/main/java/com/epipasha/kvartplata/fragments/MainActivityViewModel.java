package com.epipasha.kvartplata.fragments;

import android.app.Application;

import com.epipasha.kvartplata.data.PaymentEntity;
import com.epipasha.kvartplata.data.Repository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainActivityViewModel extends AndroidViewModel {

    private Repository mRepository;

    private LiveData<List<PaymentEntity>> mPayments;

    public MainActivityViewModel(Application application, Repository repository) {
        super(application);

        mRepository = repository;

        mPayments = mRepository.getPayments();
    }

    public LiveData<List<PaymentEntity>> getPayments() {
        return mPayments;
    }
}
