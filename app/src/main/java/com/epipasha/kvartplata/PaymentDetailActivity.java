package com.epipasha.kvartplata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.epipasha.kvartplata.fragments.ColdWaterFragment;
import com.epipasha.kvartplata.fragments.PaymentViewModel;
import com.epipasha.kvartplata.fragments.ViewModelFactory;

public class PaymentDetailActivity extends AppCompatActivity {

    public static final String PAYMENT_ID = "payment_id";

    private PaymentViewModel model;

    private ColdWaterFragment frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        model = ViewModelProviders.of(this, ViewModelFactory.getInstance(getApplication())).get(PaymentViewModel.class);

        Intent i = getIntent();
        Bundle args = new Bundle();

        if (i.hasExtra(PAYMENT_ID)){
            model.start(i.getIntExtra(PAYMENT_ID,0));
        }

        frag = new ColdWaterFragment();
        frag.setArguments(args);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tr = fm.beginTransaction();
        tr.add(R.id.container, frag);
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