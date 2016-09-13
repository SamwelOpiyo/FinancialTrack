package com.samwelopiyo.financialtrack.financialtrack;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;


/**
 * Created by root on 4/26/16.
 */

public class DataBaseHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "financialTrack";

    // table names
    private static final String INCOME_TABLE = "income_reports";
    private static final String EXPENDITURE_TABLE = "expenditure_reports";
    private static final String SIGNUP_TABLE = "signup";

    // Table Column names
    private static final String INCOME_ID = "_id";
    private static final String INCOME_AMOUNT = "amount";
    private static final String INCOME_SOURCE = "source";
    private static final String INCOME_DATE = "date";
    private static final String INCOME_TIME = "time";
    private static final String INCOME_PLACE = "place";
    // --Commented out by Inspection (5/26/16 11:58 PM):private static final String TAG = "DataBaseHelper";

    private static final String EXPENDITURE_ID = "_id";
    private static final String EXPENDITURE_AMOUNT = "amount";
    private static final String EXPENDITURE_USAGE = "usage";
    private static final String EXPENDITURE_DATE = "date";
    private static final String EXPENDITURE_TIME = "time";
    private static final String EXPENDITURE_PLACE = "place";

    private static final String SIGNUP_ID = "_id";
    private static final String USERNAME = "username";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    private final Context context;
    private final DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DataBaseHelper(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        // Creating Tables
        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_TABLE_REPORT_INCOME = "CREATE TABLE IF NOT EXISTS " + INCOME_TABLE + " ("
                    + INCOME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + INCOME_AMOUNT + " INTEGER, "
                    + INCOME_SOURCE + " TEXT, " + INCOME_DATE + " TEXT, " + INCOME_TIME + " TEXT, " + INCOME_PLACE + " TEXT" + ");";

            String CREATE_TABLE_REPORT_EXPENDITURE = "CREATE TABLE IF NOT EXISTS " + EXPENDITURE_TABLE + " ("
                    + EXPENDITURE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + EXPENDITURE_AMOUNT + " INTEGER, "
                    + EXPENDITURE_USAGE + " TEXT, " + EXPENDITURE_DATE + " TEXT, " + EXPENDITURE_TIME + " TEXT, " + EXPENDITURE_PLACE + " TEXT" + ");";
            String CREATE_TABLE_SIGNUP = "CREATE TABLE IF NOT EXISTS " + SIGNUP_TABLE + " ("
                    + SIGNUP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USERNAME + " TEXT, " + EMAIL + " TEXT, "
                    + PASSWORD + " TEXT" + ");";
            try {
                db.execSQL(CREATE_TABLE_SIGNUP);
                db.execSQL(CREATE_TABLE_REPORT_INCOME);
                db.execSQL(CREATE_TABLE_REPORT_EXPENDITURE);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        // Upgrading database
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop older table if existed
            db.execSQL("DROP TABLE IF EXISTS " + INCOME_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + EXPENDITURE_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + SIGNUP_TABLE);

            // Create tables again
            onCreate(db);
        }
    }

    //---opens the database---
    public DataBaseHelper open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close() {
        DBHelper.close();
    }

    //---insert a title into the database---
    public long insertTitleIncome(String amountIncome, String source, String dateIncome, String timeIncome, String placeIncome) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(INCOME_AMOUNT, amountIncome);
        initialValues.put(INCOME_SOURCE, source);
        initialValues.put(INCOME_DATE, dateIncome);
        initialValues.put(INCOME_TIME, timeIncome);
        initialValues.put(INCOME_PLACE, placeIncome);
        return db.insert(INCOME_TABLE, null, initialValues);
    }

    public long insertTitleExpenditure(String amountExpenditure, String usage, String dateExpenditure, String timeExpenditure, String placeExpenditure) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(EXPENDITURE_AMOUNT, amountExpenditure);
        initialValues.put(EXPENDITURE_USAGE, usage);
        initialValues.put(EXPENDITURE_DATE, dateExpenditure);
        initialValues.put(EXPENDITURE_TIME, timeExpenditure);
        initialValues.put(EXPENDITURE_PLACE, placeExpenditure);
        return db.insert(EXPENDITURE_TABLE, null, initialValues);
    }

    public long insertTitleSignup(String usernameVar, String emailVar, String passwordVar) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(USERNAME, usernameVar);
        initialValues.put(EMAIL, emailVar);
        initialValues.put(PASSWORD, passwordVar);
        return db.insert(SIGNUP_TABLE, null, initialValues);
    }

    //---deletes a particular title---
    public boolean deleteTitleIncome(long rowId) {
        return db.delete(INCOME_TABLE, INCOME_ID +
                "=" + rowId, null) > 0;
    }

    public boolean deleteTitleExpenditure(long rowId) {
        return db.delete(EXPENDITURE_TABLE, EXPENDITURE_ID +
                "=" + rowId, null) > 0;
    }

    //---retrieves all the titles---
    public Cursor getAllTitlesIncome() {
        return db.query(INCOME_TABLE, new String[]{
                        INCOME_ID,
                        INCOME_AMOUNT,
                        INCOME_SOURCE,
                        INCOME_DATE,
                        INCOME_TIME,
                        INCOME_PLACE},
                null, null, null, null, null, null);
    }
    public Cursor getAllUser() {
        Cursor c = db.query(SIGNUP_TABLE, new String[]{
                        SIGNUP_ID,
                        USERNAME,
                        EMAIL,
                        PASSWORD},
                null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
    public Cursor getAllTitlesExpenditure() {
        return db.query(EXPENDITURE_TABLE, new String[]{
                        EXPENDITURE_ID,
                        EXPENDITURE_AMOUNT,
                        EXPENDITURE_USAGE,
                        EXPENDITURE_DATE,
                        EXPENDITURE_TIME,
                        EXPENDITURE_PLACE},
                null, null, null, null, null, null);
    }

// --Commented out by Inspection START (5/26/16 11:59 PM):
//    //---retrieves a particular title---
//    public Cursor getTitleIncome(long rowId) throws SQLException {
//        Cursor mCursor =
//                db.query(true, INCOME_TABLE, new String[]{
//                                INCOME_ID,
//                                INCOME_AMOUNT,
//                                INCOME_SOURCE,
//                                INCOME_DATE,
//                                INCOME_TIME,
//                                INCOME_PLACE
//                        },
//                        INCOME_ID + "=" + rowId,
//                        null, null, null, null, null, null);
//        if (mCursor != null) {
//            mCursor.moveToFirst();
//        }
//        return mCursor;
//    }
// --Commented out by Inspection STOP (5/26/16 11:59 PM)

// --Commented out by Inspection START (5/26/16 11:59 PM):
//    public Cursor getTitleExpenditure(long rowId) throws SQLException {
//        Cursor mCursor =
//                db.query(true, EXPENDITURE_TABLE, new String[]{
//                                EXPENDITURE_ID,
//                                EXPENDITURE_AMOUNT,
//                                EXPENDITURE_USAGE,
//                                EXPENDITURE_DATE,
//                                EXPENDITURE_TIME,
//                                EXPENDITURE_PLACE
//                        },
//                        EXPENDITURE_ID + "=" + rowId,
//                        null, null, null, null, null, null);
//        if (mCursor != null) {
//            mCursor.moveToFirst();
//        }
//        return mCursor;
//    }
// --Commented out by Inspection STOP (5/26/16 11:59 PM)

    //---updates a title---
    public boolean updateTitleIncome(long rowId, String amount, String source, String date, String time, String place) {
        ContentValues args = new ContentValues();
        args.put(INCOME_AMOUNT, amount);
        args.put(INCOME_SOURCE, source);
        args.put(INCOME_DATE, date);
        args.put(INCOME_TIME, time);
        args.put(INCOME_PLACE, place);
        return db.update(INCOME_TABLE, args,
                INCOME_ID + "=" + rowId, null) > 0;
    }

    public boolean updateTitleExpenditure(long rowId, String amount, String usage, String date, String time, String place) {
        ContentValues args = new ContentValues();
        args.put(EXPENDITURE_AMOUNT, amount);
        args.put(EXPENDITURE_USAGE, usage);
        args.put(EXPENDITURE_DATE, date);
        args.put(EXPENDITURE_TIME, time);
        args.put(EXPENDITURE_PLACE, place);
        return db.update(EXPENDITURE_TABLE, args,
                INCOME_ID + "=" + rowId, null) > 0;
    }


    public int numberOfRows() {
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, SIGNUP_TABLE);
        noUsers = numRows;
        return numRows;
    }

    Integer noUsers;

    /*public Cursor getAllIncom() {
        return db.query(INCOME_TABLE, new String[] {INCOME_ID, INCOME_ID, INCOME_AMOUNT,INCOME_DATE, INCOME_TIME, INCOME_PLACE},
                null, null, null, null, null, null);
    }*/


    /* Insert data to a Table
   myDB.execSQL("INSERT INTO "
           + TableName
   + " (Column_Name, Column_Name2, Column_Name3, Column_Name4)"
           + " VALUES ( "+EditTextString1+", 'Column_Value2','Column_Value3','Column_Value4');");*/


}

