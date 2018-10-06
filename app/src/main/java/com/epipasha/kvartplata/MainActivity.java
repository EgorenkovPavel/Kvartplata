package com.epipasha.kvartplata;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.epipasha.kvartplata.data.PaymentEntity;
import com.epipasha.kvartplata.fragments.MainActivityViewModel;
import com.epipasha.kvartplata.fragments.ViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PaymentAdapter.PaymentClickListener {

    private MainActivityViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, PaymentDetailActivity.class);
                startActivity(i);
            }
        });

        RecyclerView rvPaymentsList = findViewById(R.id.rvPaymentList);

        rvPaymentsList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvPaymentsList.setLayoutManager(layoutManager);

        DividerItemDecoration decor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvPaymentsList.addItemDecoration(decor);

        final PaymentAdapter adapter = new PaymentAdapter();
        adapter.setPaymentClickListener(this);
        rvPaymentsList.setAdapter(adapter);

        model = ViewModelProviders.of(this, ViewModelFactory.getInstance(getApplication())).get(MainActivityViewModel.class);
        model.getPayments().observe(this, new Observer<List<PaymentEntity>>() {
            @Override
            public void onChanged(List<PaymentEntity> payments) {
                adapter.setItems(payments);
            }
        });

    }

    @Override
    public void OnPaymentClick(PaymentEntity payment) {
        Intent i = new Intent(this, PaymentDetailActivity.class);
        i.putExtra(PaymentDetailActivity.PAYMENT_ID, payment.getId());
        startActivity(i);
    }
}
