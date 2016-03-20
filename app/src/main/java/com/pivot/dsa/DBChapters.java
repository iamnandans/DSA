package com.pivot.dsa;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by shanthan on 3/2/2016.
 */
public class DBChapters {
    private String CHAPTERS_TB = "chapters";
    private static String UID = "_id";
    private static String CHAP_NAME = "chap_name";
    private String LEVEL = "chap_level";
    private static String SUBJECT_ID = "subj_id";
    private DBSubjects subjects ; //= new DBSubjects();
    Context context;
    private String DROP_CHAP_TABLE;

    private String CREATE_CHAPTERS_TABLE;  /* = "create table " + CHAPTERS_TB + " (" +
            UID + " integer primary key," +
            SUBJECT_ID + " integer," +
            CHAP_NAME + " varchar(128), " +
            LEVEL + " integer, " +
            "FOREIGN KEY (" + SUBJECT_ID + ") REFERENCES " + subjects.getSubjectsTb() + "(" + subjects.getUID() + "));"; */

    DBChapters(Context context) {
        this.context = context;
        subjects = new DBSubjects(context);
        CREATE_CHAPTERS_TABLE  = "create table " + CHAPTERS_TB + " (" +
                UID + " integer primary key," +
                SUBJECT_ID + " integer," +
                CHAP_NAME + " varchar(128), " +
                "FOREIGN KEY (" + SUBJECT_ID + ") REFERENCES " + subjects.getSubjectsTb() + "(" + subjects.getUID() + "));";
        DROP_CHAP_TABLE = "drop table if exists " + CHAPTERS_TB;
    }

    public String getCreateChapersTable() {
        return CREATE_CHAPTERS_TABLE;
    }

    public String getDropChaptersTable() {
        return DROP_CHAP_TABLE;
    }

    public String getChaptersTb() {
        return CHAPTERS_TB;
    }

    public static String getUID() {
        return UID;
    }

    public static String getSUBJECT_ID() {
        return SUBJECT_ID;
    }

    public static String getCHAP_NAME() {
        return CHAP_NAME;
    }

    public Cursor getAllChapters(SQLiteDatabase db) {
        String [] columns = {getUID(),getSUBJECT_ID(), getCHAP_NAME() };
        Cursor cursor = db.query(CHAPTERS_TB,columns,null,null,null,null,null);

        return cursor;
    }
    /*
    public String getAllChapters() {
        String subjects = "insert into " + CHAPTERS_TB + " values(1, 1, 'chapter1',1);" ;
        subjects += subjects + "insert into " + CHAPTERS_TB + " values(2, 1 , 'chapter2',1);";
        subjects += subjects + "insert into " + CHAPTERS_TB + " values(3, 1, 'chapter3',2);";
        subjects += subjects + "insert into " + CHAPTERS_TB + " values(4, 2, 'chapter1',2);";
        subjects += subjects + "insert into " + CHAPTERS_TB + " values(5, 2, 'chapter2',3);";
        subjects += subjects + "insert into " + CHAPTERS_TB + " values(6, 2, 'chapter2',3);";
        subjects += subjects + "insert into " + CHAPTERS_TB + " values(7, 3, 'chapter1',1);";
        subjects += subjects + "insert into " + CHAPTERS_TB + " values(8, 4, 'chapter1',2);";
        subjects += subjects + "insert into " + CHAPTERS_TB + " values(9, 5, 'chapter1',3);";
        subjects += subjects + "insert into " + CHAPTERS_TB + " values(10, 6, 'chapter1',1);";

        return subjects;
    }
    */

    public boolean createTBnData(SQLiteDatabase db) {
        db.execSQL(CREATE_CHAPTERS_TABLE);

        String [] chaptersArray;
        Resources res = this.context.getResources();
        chaptersArray = res.getStringArray(R.array.chapterName);

        int [] chapterID;
        chapterID = res.getIntArray(R.array.chapterID);

        int [] chapSubID;
        chapSubID = res.getIntArray(R.array.chapSubjID);

        for (int count=0; count < chaptersArray.length; count++ ) {
            String SqlStatement = "insert into " + CHAPTERS_TB + " values(" + chapterID[count] + "," + chapSubID[count] + ",'" + chaptersArray[count] + "');" ;
            db.execSQL(SqlStatement);
        }

        //db.execSQL(getAllChapters());
        return true;
    }
}
