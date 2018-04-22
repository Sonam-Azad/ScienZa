package com.example.android.simplefeeder;

import android.provider.BaseColumns;

public class DatabaseContract {
    private DatabaseContract(){}
    public static class DataBaseEntries implements BaseColumns
    {
        public static final String TABLE_NAME="Bookmark";
        public static final String COLUMN_TITLE="title";
        public static final String COLUMN_DESCRIPTION="description";
        public static final String COLUMN_IMAGEURL="imageurl";
        public static final String COLUMN_URL="url";
        public static final String COLUMN_DATE="date";


    }

}
