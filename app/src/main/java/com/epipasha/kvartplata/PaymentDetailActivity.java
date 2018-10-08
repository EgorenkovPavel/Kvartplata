package com.epipasha.kvartplata;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.epipasha.kvartplata.databinding.ActivityPaymentDetailBinding;
import com.epipasha.kvartplata.databinding.FragmentColdWaterBinding;
import com.epipasha.kvartplata.fragments.ColdWaterFragment;
import com.epipasha.kvartplata.fragments.DrainFragment;
import com.epipasha.kvartplata.fragments.ElectricityFragment;
import com.epipasha.kvartplata.fragments.HotWaterFragment;
import com.epipasha.kvartplata.fragments.InternetFragment;
import com.epipasha.kvartplata.viewmodels.PaymentViewModel;
import com.epipasha.kvartplata.viewmodels.ViewModelFactory;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

public class PaymentDetailActivity extends AppCompatActivity {

    public static final String PAYMENT_ID = "payment_id";

    private PaymentViewModel model;

    private ColdWaterFragment mColdWaterFragment;
    private HotWaterFragment mHotWaterFragment;
    private DrainFragment mDrainFragment;
    private ElectricityFragment mElectricityFragment;
    private InternetFragment mInternetFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = ViewModelProviders.of(this, ViewModelFactory.getInstance(getApplication())).get(PaymentViewModel.class);
        ActivityPaymentDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_detail);
        //here data must be an instance of the class MarsDataProvider
        binding.setModel(model);
        binding.setActivity(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        if (i.hasExtra(PAYMENT_ID)){
            model.start(i.getIntExtra(PAYMENT_ID,0));
        }else{
            model.start();
        }

        mColdWaterFragment = new ColdWaterFragment();
        mHotWaterFragment = new HotWaterFragment();
        mDrainFragment = new DrainFragment();
        mElectricityFragment = new ElectricityFragment();
        mInternetFragment = new InternetFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tr = fm.beginTransaction();
        tr.add(R.id.container, mColdWaterFragment);
        tr.add(R.id.container, mHotWaterFragment);
        tr.add(R.id.container, mDrainFragment);
        tr.add(R.id.container, mElectricityFragment);
        tr.add(R.id.container, mInternetFragment);
        tr.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.payment_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save){
            model.save();
            finish();
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void onDateClick(View view){
        Calendar cal = Calendar.getInstance();
        cal.setTime(model.getDate().get());

        DatePickerDialog dialog = new DatePickerDialog(this,
                android.R.style.Theme_Holo_Dialog,
                new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                model.setDate(new Date(year - 1900, month, day));
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        //TODO fix
        dialog.getDatePicker().findViewById(16908818).setVisibility(View.GONE);

        dialog.show();
    }
}
