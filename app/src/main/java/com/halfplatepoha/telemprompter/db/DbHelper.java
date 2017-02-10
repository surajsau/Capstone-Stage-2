package com.halfplatepoha.telemprompter.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by surajkumarsau on 10/02/17.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static DbHelper dbHelper;

    public static synchronized DbHelper getInstance(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version) {
        if (dbHelper == null)
            dbHelper = new DbHelper(context, name, cursorFactory, version);
        return dbHelper;
    }

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table " + DataProvider.Contract.FileInfoColumns.TABLE_NAME + " (" +
                DataProvider.Contract.FileInfoColumns.FILE_NAME + " TEXT NOT NULL," +
                DataProvider.Contract.FileInfoColumns.FILE_PATH + " TEXT NOT NULL," +
                DataProvider.Contract.FileInfoColumns._ID + " INT PRIMARY KEY" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table *;");
        onCreate(db);
    }
}
