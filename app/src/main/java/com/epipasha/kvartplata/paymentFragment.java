package com.epipasha.kvartplata;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Pavel on 21.08.2017.
 */

public abstract class paymentFragment extends Fragment {

    private int bilId;

    public void setBillId(int billId){
        this.bilId = billId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_detail, container, false);

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        save();
    }

    abstract void save();

}
