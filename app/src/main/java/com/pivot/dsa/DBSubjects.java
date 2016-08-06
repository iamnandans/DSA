package com.pivot.dsa;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by shanthan on 3/2/2016.
 */
public class DBSubjects {
    Context context;
    private String SUBJECTS_TB = "subjects";
    private String UID = "_id";
    private String ID = "id";

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
        CREATE_SUB_TABLE = "create table " + SUBJECTS_TB + " (" + ID +
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

    public String getID() {
        return ID;
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
        String [] columns = {getID() + " _id", getSUB_NAME() };
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

    public int updateData(SQLiteDatabase db, String subjData) {
        String [] columns = {getID(),getSUB_NAME() };
        int i=0;
        int id;
        String subj_name=null;
        Cursor cursor = db.query(SUBJECTS_TB,columns,null,null,null,null,null);

        cursor.moveToLast();
        int sub_id = cursor.getInt(cursor.getColumnIndex(ID));

        try {
            Log.d("json:", subjData);
            JSONArray subjects = new JSONArray(subjData);
            ContentValues subj_values = new ContentValues();

            for (i = 0; i < subjects.length(); i++) {
                JSONObject temp = subjects.getJSONObject(i);
                id = temp.getInt(ID);
                subj_name = temp.getString(SUB_NAME);
                //Log.d("subjectid", "id:" + id);
                subj_values.put(SUB_NAME, subj_name);
                if ( id > sub_id ) {
                    /* add new data */
                    //Log.d("subjects:", "insert - id=" + id + "--sub_name:" + subj_name );
                    subj_values.put(ID, ++id);
                    db.insert(SUBJECTS_TB,null, subj_values);

                } else {
                    /* update existing data */
                    //Log.d("subjects:", "update - id=" + id + "--sub_name:" + subj_name );
                    db.update(SUBJECTS_TB,subj_values,ID + "=" + id,null);
                }
            }

        } catch (Exception e) {
            Log.d("JSON", "Exception: converting to json array " + e.toString());
            return 0;
        }

        cursor.close();
        return i;
    }
}
