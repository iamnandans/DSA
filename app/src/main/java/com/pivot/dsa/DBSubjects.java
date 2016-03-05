package com.pivot.dsa;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by shanthan on 3/2/2016.
 */
public class DBSubjects {
    private String SUBJECTS_TB = "subjects";
    private String UID = "_id";
    private String SUB_NAME = "sub_name";
    private String LEVEL = "sub_level";
    private String CREATE_SUB_TABLE = "create table " + SUBJECTS_TB + " (" + UID +
            " integer primary key," + SUB_NAME +
            " varchar(128), " + LEVEL + " integer);";

    private String DROP_SUB_TABLE = "drop table if exists " + SUBJECTS_TB;

    public String getCreateSubTable() {
        return CREATE_SUB_TABLE;
    }

    public String getDropSubTable() {
        return DROP_SUB_TABLE;
    }

    public String getSubjectsTb() {
        return SUBJECTS_TB;
    }

    public String getUID() {
        return UID;
    }

    public String getAllSubjects() {
        String subjects = "insert into " + SUBJECTS_TB + " values(1, 'subject1',1);" ;
        subjects += subjects + "insert into " + SUBJECTS_TB + " values(2, 'subject2',1);";
        subjects += subjects + "insert into " + SUBJECTS_TB + " values(3, 'subject3',1);";
        subjects += subjects + "insert into " + SUBJECTS_TB + " values(4, 'subject4',1);";
        subjects += subjects + "insert into " + SUBJECTS_TB + " values(5, 'subject5',2);";
        subjects += subjects + "insert into " + SUBJECTS_TB + " values(6, 'subject6',2);";
        subjects += subjects + "insert into " + SUBJECTS_TB + " values(7, 'subject7',2);";
        subjects += subjects + "insert into " + SUBJECTS_TB + " values(8, 'subject8',3);";
        subjects += subjects + "insert into " + SUBJECTS_TB + " values(9, 'subject9',3);";
        subjects += subjects + "insert into " + SUBJECTS_TB + " values(10, 'subject10',3);";

        return subjects;
    }

    public boolean createTBnData(SQLiteDatabase db) {
        db.execSQL(CREATE_SUB_TABLE);
        db.execSQL(getAllSubjects());
        return true;
    }
}
