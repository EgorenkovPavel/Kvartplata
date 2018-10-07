package com.epipasha.kvartplata.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.epipasha.kvartplata.R;
import com.epipasha.kvartplata.databinding.FragmentHotWaterBinding;
import com.epipasha.kvartplata.viewmodels.PaymentViewModel;
import com.epipasha.kvartplata.viewmodels.ViewModelFactory;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class HotWaterFragment extends Fragment{

    private PaymentViewModel model;

    public HotWaterFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        FragmentHotWaterBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_hot_water, container, false);
        View view = binding.getRoot();
        //here data must be an instance of the class MarsDataProvider
        binding.setModel(model);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(getActivity(), ViewModelFactory.getInstance(getActivity().getApplication())).get(PaymentViewModel.class);
    }

}
