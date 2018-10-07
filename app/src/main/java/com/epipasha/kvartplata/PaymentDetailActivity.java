package com.epipasha.kvartplata;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.epipasha.kvartplata.fragments.ColdWaterFragment;
import com.epipasha.kvartplata.fragments.DrainFragment;
import com.epipasha.kvartplata.fragments.HotWaterFragment;
import com.epipasha.kvartplata.viewmodels.PaymentViewModel;
import com.epipasha.kvartplata.viewmodels.ViewModelFactory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

public class PaymentDetailActivity extends AppCompatActivity {

    public static final String PAYMENT_ID = "payment_id";

    private PaymentViewModel model;

    private ColdWaterFragment mColdWaterFragment;
    private HotWaterFragment mHotWaterFragment;
    private DrainFragment mDrainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        model = ViewModelProviders.of(this, ViewModelFactory.getInstance(getApplication())).get(PaymentViewModel.class);

        Intent i = getIntent();
        if (i.hasExtra(PAYMENT_ID)){
            model.start(i.getIntExtra(PAYMENT_ID,0));
        }

        mColdWaterFragment = new ColdWaterFragment();
        mHotWaterFragment = new HotWaterFragment();
        mDrainFragment = new DrainFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tr = fm.beginTransaction();
        tr.add(R.id.container, mColdWaterFragment);
        tr.add(R.id.container, mHotWaterFragment);
        tr.add(R.id.container, mDrainFragment);
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
}
