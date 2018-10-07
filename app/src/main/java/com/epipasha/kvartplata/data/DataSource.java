package com.epipasha.kvartplata.data;

import com.epipasha.kvartplata.data.entities.PaymentEntity;

public interface DataSource {

    interface GetPaymentCallback {
        void onSuccess(PaymentEntity entity);
        void onFailed();
    }

}
