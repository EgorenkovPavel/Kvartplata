package com.epipasha.kvartplata;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PaymentAdapter.PaymentClickListener {

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
        adapter.setPaymentClickListener(this);
        rvPaymentsList.setAdapter(adapter);

    }

    @Override
    public void OnPaymentClick(Payment payment) {
        Intent i = new Intent(this, PaymentDetailActivity.class);
        startActivity(i);
    }
}
