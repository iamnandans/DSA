package com.pivot.dsa;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by shanthan on 3/2/2016.
 */
public class DBSubjects {
    Context context;
    private String SUBJECTS_TB = "subjects";
    private String UID = "_id";

    private String SUB_NAME = "sub_name";
    private String LEVEL = "sub_level";
    /* private String CREATE_SUB_TABLE = "create table " + SUBJECTS_TB + " (" + UID +
            " integer primary key," + SUB_NAME +
            " varchar(128), " + LEVEL + " integer);";
    */
    private String CREATE_SUB_TABLE ;
    private String DROP_SUB_TABLE ;

    DBSubjects(Context context) {
        this.context = context;
        CREATE_SUB_TABLE = "create table " + SUBJECTS_TB + " (" + UID +
                " integer primary key," + SUB_NAME +
                " varchar(128));";
        DROP_SUB_TABLE = "drop table if exists " + SUBJECTS_TB;
    }

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

    public String getSUB_NAME() {
        return SUB_NAME;
    }

    /*
    public String getLEVEL() {
        return LEVEL;
    }
    */

    public Cursor getAllSubjects(SQLiteDatabase db) {
        String [] columns = {getUID(),getSUB_NAME() };
        Cursor cursor = db.query(SUBJECTS_TB,columns,null,null,null,null,null);

        return cursor;
    }

    /*
    public String getAllSubjects() {
        StringBuffer subjects = new StringBuffer();
                subjects.append("insert into " + SUBJECTS_TB + " values(1, 'subject1',1);") ;
        subjects.append("insert into " + SUBJECTS_TB + " values(2, 'subject2',1);");
         subjects += subjects + "insert into " + SUBJECTS_TB + " values(3, 'subject3',1);";
        subjects += subjects + "insert into " + SUBJECTS_TB + " values(4, 'subject4',1);";
        subjects += subjects + "insert into " + SUBJECTS_TB + " values(5, 'subject5',2);";
        subjects += subjects + "insert into " + SUBJECTS_TB + " values(6, 'subject6',2);";
        subjects += subjects + "insert into " + SUBJECTS_TB + " values(7, 'subject7',2);";
        subjects += subjects + "insert into " + SUBJECTS_TB + " values(8, 'subject8',3);";
        subjects += subjects + "insert into " + SUBJECTS_TB + " values(9, 'subject9',3);";
        subjects += subjects + "insert into " + SUBJECTS_TB + " values(10, 'subject10',3);";

        return subjects.toString();
    }
    */

    public boolean createTBnData(SQLiteDatabase db) {
        db.execSQL(CREATE_SUB_TABLE);

        String [] subjectsArray;
        Resources res = this.context.getResources();
        subjectsArray = res.getStringArray(R.array.SubjectName);

        int [] subjectNumber;
        subjectNumber = res.getIntArray(R.array.SubjectID);

        /* int [] subjectLevel;
        subjectLevel = res.getIntArray(R.array.SubjectLevel); */

        for (int count=0; count < subjectsArray.length; count++ ) {
            StringBuffer subjects = new StringBuffer();
            subjects.append("insert into " + SUBJECTS_TB + " values(" + subjectNumber[count] + ", '" + subjectsArray[count] + "');") ;
            db.execSQL(subjects.toString());
        }
        /*
        StringBuffer subjects = new StringBuffer();
        subjects.append("insert into " + SUBJECTS_TB + " values(1, 'subject1',1);") ;
        db.execSQL(subjects.toString());

        StringBuffer subjects1 = new StringBuffer();
        subjects1.append("insert into " + SUBJECTS_TB + " values(2, 'subject2',2)") ;
        db.execSQL(subjects1.toString());

        StringBuffer subjects2 = new StringBuffer();
        subjects2.append("insert into " + SUBJECTS_TB + " values(3, 'subject3',3)") ;
        db.execSQL(subjects2.toString());

        StringBuffer subjects3 = new StringBuffer();
        subjects3.append("insert into " + SUBJECTS_TB + " values(4, 'subject4',4)") ;
        db.execSQL(subjects3.toString());
        */
        //db.execSQL(getAllSubjects());
        return true;
    }
}
