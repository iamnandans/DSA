package com.pivot.dsa;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shanthan on 2/28/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DB_NAME = "DSA";
    private static final int DB_VERSION = 18;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        Message.message(context, "db constructor called");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DBSubjects subjects = new DBSubjects();
        DBChapters chapters = new DBChapters();

        Message.message(context,"on create called");
        try {
            subjects.createTBnData(db);
            chapters.createDTBData(db);
        } catch (SQLException e) {
            Message.message(context, "" + e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        DBSubjects subjects = new DBSubjects();
        DBChapters chapters = new DBChapters();

        Message.message(context, "on upgrade called");
        try {


            db.execSQL(subjects.getDropSubTable());
            db.execSQL(chapters.getDropChaptersTable());
            onCreate(db);
        } catch (SQLException e) {
            Message.message(context, "" + e);
        }
        //onCreate(db);
    }
}
