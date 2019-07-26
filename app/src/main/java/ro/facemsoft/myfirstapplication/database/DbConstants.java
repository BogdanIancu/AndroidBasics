package ro.facemsoft.myfirstapplication.database;

import android.provider.BaseColumns;

public class DbConstants {
    public static final String DB_NAME = "database.db";
    public static final int DB_VERSION = 1;

    public class Items implements BaseColumns {
        public static final String TABLE_NAME = "items";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESC = "description";
        public static final String COLUMN_DUE_DATE = "due_date";
        public static final String COLUMN_PRIORITY = "priority";

        public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME +" (" +_ID + " INT PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT, " + COLUMN_DESC + " TEXT, " +
                    COLUMN_DUE_DATE + " DATE, " + COLUMN_PRIORITY + " TEXT);";

        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
