package com.epipasha.kvartplata.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PaymentDao {

    @Query("SELECT * FROM payment")
    LiveData<List<PaymentEntity>> getAll();

    @Query("SELECT * FROM payment WHERE id =:paymentId")
    PaymentEntity getPayment(int paymentId);

    @Insert
    void insertPayment(PaymentEntity payment);

    @Update
    void updatePayment(PaymentEntity payment);

}
