package com.epipasha.kvartplata.data;

import android.provider.BaseColumns;


public class KvartplataContract {

    public static final String COLUMN_BILL_ID = "bill_id";

    public static final class BillEntry implements BaseColumns {
        public static final String TABLE_NAME = "bill";

        public static final String COLUMN_MONTH = "bill_month";
        public static final String COLUMN_YEAR = "bill_year";
        public static final String COLUMN_SUM = "bill_sum";
    }

    public static final class HotWaterEntry {
        public static final String TABLE_NAME = "hotwater";

        public static final String COLUMN_BILL = COLUMN_BILL_ID;
        public static final String COLUMN_TAX = "hotwater_tax";
        public static final String COLUMN_VALUE = "hotwater_value";
        public static final String COLUMN_SUM = "hotwater_sum";
    }

    public static final class ColdWaterEntry  {
        public static final String TABLE_NAME = "coldwater";

        public static final String COLUMN_BILL = COLUMN_BILL_ID;
        public static final String COLUMN_TAX = "coldwater_tax";
        public static final String COLUMN_VALUE = "coldwater_value";
        public static final String COLUMN_SUM = "coldwater_sum";
    }

    public static final class ElectricityEntry  {
        public static final String TABLE_NAME = "electricity";

        public static final String COLUMN_BILL = COLUMN_BILL_ID;
        public static final String COLUMN_TAX = "electricity_tax";
        public static final String COLUMN_VALUE = "electricity_value";
        public static final String COLUMN_SUM = "electricity_sum";
    }

    public static final class CanalizationEntry  {
        public static final String TABLE_NAME = "canalization";

        public static final String COLUMN_BILL = COLUMN_BILL_ID;
        public static final String COLUMN_TAX = "canalization_tax";
        public static final String COLUMN_SUM = "canalization_sum";
    }
}