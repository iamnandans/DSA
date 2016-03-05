package com.pivot.dsa;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by shanthan on 3/2/2016.
 */
public class DBChapters {
    private String CHAPTERS_TB = "chapters";
    private String UID = "_id";
    private String CHAP_NAME = "chap_name";
    private String LEVEL = "chap_level";
    private String SUBJECT_ID = "subj_id";
    private DBSubjects subjects = new DBSubjects();

    private String CREATE_CHAPTERS_TABLE = "create table " + CHAPTERS_TB + " (" +
            UID + " integer primary key," +
            SUBJECT_ID + " integer," +
            CHAP_NAME + " varchar(128), " +
            LEVEL + " integer, " +
            "FOREIGN KEY (" + SUBJECT_ID + ") REFERENCES " + subjects.getSubjectsTb() + "(" + subjects.getUID() + "));";

    private String DROP_CHAP_TABLE = "drop table if exists " + CHAPTERS_TB;

    public String getCreateChapersTable() {
        return CREATE_CHAPTERS_TABLE;
    }

    public String getDropChaptersTable() {
        return DROP_CHAP_TABLE;
    }

    public String getChaptersTb() {
        return CHAPTERS_TB;
    }

    public String getUID() {
        return UID;
    }

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

    public boolean createDTBData(SQLiteDatabase db) {
        db.execSQL(CREATE_CHAPTERS_TABLE);
        db.execSQL(getAllChapters());
        return true;
    }
}
