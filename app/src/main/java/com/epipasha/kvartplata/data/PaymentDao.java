package com.epipasha.kvartplata.data;

import com.epipasha.kvartplata.data.entities.PaymentEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PaymentDao {

    @Query("SELECT * FROM payment ORDER BY year, month")
    LiveData<List<PaymentEntity>> getAll();

    @Query("SELECT * FROM payment WHERE id =:paymentId")
    PaymentEntity getPayment(int paymentId);

    @Query("SELECT * FROM payment ORDER BY year, month LIMIT 1")
    PaymentEntity getLastPayment();

    @Query("SELECT * FROM payment WHERE year <:year or (year =:year and month <:month) ORDER BY year, month LIMIT 1")
    PaymentEntity getPreviousPayment(int month, int year);

    @Query("SELECT * FROM payment WHERE year =:year AND month =:month")
    PaymentEntity getPaymentByDate(int month, int year);

    @Insert
    void insertPayment(PaymentEntity payment);

    @Update
    void updatePayment(PaymentEntity payment);

}
