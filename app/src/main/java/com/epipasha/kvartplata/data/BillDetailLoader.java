package com.epipasha.kvartplata.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by Pavel on 29.07.2017.
 */

public class BillDetailLoader extends AsyncTaskLoader<Cursor> {

    Context context;
    int billId;

    public BillDetailLoader(Context context, int billId) {
        super(context);
        this.context = context;
        this.billId = billId;
    }

    @Override
    public Cursor loadInBackground() {

        return KvartplataDbManager.getDetailData(context, billId);

    }

}
