package com.pivot.dsa;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shanthan on 2/28/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DB_NAME = "DSA";
    private static final int DB_VERSION = 23;
    private SQLiteDatabase gDB;
    DBSubjects subjects ;
    DBChapters chapters ;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        Message.message(context, "db constructor called");

        subjects = new DBSubjects();
        chapters = new DBChapters();
        gDB = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Message.message(context,"on create called");
        try {
            subjects.createTBnData(db);
            chapters.createTBnData(db);
        } catch (SQLException e) {
            Message.message(context, "" + e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

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

    public Cursor getAllSubjects() {
        String [] columns = {subjects.getUID(),subjects.getSUB_NAME(),subjects.getLEVEL() };
        Cursor cursor = gDB.query(subjects.getSubjectsTb(),columns,null,null,null,null,null);
        StringBuffer buffer = new StringBuffer();

        return cursor;
    }
}
