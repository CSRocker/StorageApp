package com.example.clarachen.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ClaraChen on 3/7/16.
 */
public class SqliteDBController {

    public static final String MESSAGE = "Message";
    public static final String TABLE_NAME = "Msg_Table";
    public static final String DATABASE_NAME = "StorageHW.db";
    public static final int DATABASE_VERSION =4;
    public static final String TABLE_CREATE ="create table Msg_Table(Message text not null);";


    DataBaseHelper dataBaseHelper;
    Context context;
    SQLiteDatabase sqDB;

    public SqliteDBController(Context context){
        this.context = context;
        dataBaseHelper = new DataBaseHelper(context);

    }

    public SqliteDBController open(){
        sqDB = dataBaseHelper.getWritableDatabase();
        return this;

    }

    public void close() {
        dataBaseHelper.close();
    }

    public long insert(String message){
        ContentValues content = new ContentValues();
        content.put(MESSAGE, message);
        return sqDB.insertOrThrow(TABLE_NAME, null, content);
    }


    private static class DataBaseHelper extends SQLiteOpenHelper {
        public DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase){
            try
            {
                sqLiteDatabase.execSQL(TABLE_CREATE);
            }
            catch (SQLiteException e){
                e.printStackTrace();
            }
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("DROP TABLE IF EXISTS Msg_Table");
            onCreate(db);
        }

    }
}
