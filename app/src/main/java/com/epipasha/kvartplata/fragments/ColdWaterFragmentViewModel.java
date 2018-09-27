package com.epipasha.kvartplata.fragments;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;

public class ColdWaterFragmentViewModel extends AndroidViewModel {

    private ObservableInt prevValueField = new ObservableInt();
    private ObservableInt presValueField = new ObservableInt();
    private ObservableInt taxField = new ObservableInt();
    private ObservableInt deltaField = new ObservableInt();
    private ObservableInt sumField = new ObservableInt();

    public ColdWaterFragmentViewModel(@NonNull Application application) {
        super(application);

        prevValueField.set(12);
        presValueField.set(16);
        taxField.set(4);

        calculate();
    }

    public ObservableInt getPrevValue() {
        return prevValueField;
    }

    public ObservableInt getPresValue() {
        return presValueField;
    }

    public ObservableInt getTax() {
        return taxField;
    }

    public ObservableInt getDelta() {
        return deltaField;
    }

    public ObservableInt getSum() {
        return sumField;
    }

    public void onPrevValueChanged(CharSequence s, int start, int before, int count){
        onTextChanged(s, prevValueField);
    }

    public void onPresValueChanged(CharSequence s, int start, int before, int count){
        onTextChanged(s, presValueField);
    }

    public void onTaxChanged(CharSequence s, int start, int before, int count){
        onTextChanged(s, taxField);
    }

    private void onTextChanged(CharSequence s, ObservableInt field){
        String text = s.toString();
        int value = text.isEmpty() ? 0 : Integer.valueOf(text);
        field.set(value);

        calculate();
    }

    private void calculate(){
        int prev = prevValueField.get();
        int pres = presValueField.get();
        int tax = taxField.get();

        int delta = pres - prev;
        int sum = tax * delta;

        deltaField.set(delta);
        sumField.set(sum);
    }
}
