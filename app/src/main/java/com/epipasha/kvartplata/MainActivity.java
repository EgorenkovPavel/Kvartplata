package com.epipasha.kvartplata;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static List<Payment> mPayments;
    static{
        mPayments = new ArrayList<>();
        mPayments.add(new Payment(new Date(), 1000));
        mPayments.add(new Payment(new Date(), 2000));
        mPayments.add(new Payment(new Date(), 3000));
        mPayments.add(new Payment(new Date(), 4000));
        mPayments.add(new Payment(new Date(), 5000));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView rvPaymentsList = findViewById(R.id.rvPaymentList);

        rvPaymentsList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvPaymentsList.setLayoutManager(layoutManager);

        DividerItemDecoration decor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvPaymentsList.addItemDecoration(decor);

        PaymentAdapter adapter = new PaymentAdapter();
        adapter.setItems(mPayments);
        rvPaymentsList.setAdapter(adapter);

    }

 }
