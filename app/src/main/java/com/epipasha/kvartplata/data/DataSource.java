package com.epipasha.kvartplata.data;

public interface DataSource {

    interface GetPaymentCallback {
        void onSuccess(PaymentEntity entity);
        void onFailed();
    }

}
