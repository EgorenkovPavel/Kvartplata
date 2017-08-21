package com.epipasha.kvartplata.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by Pavel on 29.07.2017.
 */

public class BillListLoader extends AsyncTaskLoader<Cursor> {

    Context context;

    public BillListLoader(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public Cursor loadInBackground() {
        return KvartplataDbManager.getBillData(context, null);
    }

}
