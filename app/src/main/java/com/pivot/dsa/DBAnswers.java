package com.pivot.dsa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by shanthan on 6/25/2016.
 */
public class DBAnswers {
    private String ANSWERS_TB = "answers";
    private static String UID = "_id";
    private static String ID = "id";

    private static String QUESTION_ID = "questionID";
    private static String ANSWER_DESC = "answer_desc";
    private static String ANSWER_IMAGE1 = "answer_image1";
    private DBQuestions questions;

    private String CREATE_ANSWERS_TABLE;
    private String DROP_ANSWERS_TABLE = "drop table if exists " + ANSWERS_TB;

    private Context context;
    private Cursor cursor;

    DBAnswers(Context context) {
        this.context = context;
        questions = new DBQuestions(this.context);

        CREATE_ANSWERS_TABLE = "create table " + ANSWERS_TB + " (" +
                ID + " integer primary key, " +
                QUESTION_ID + " integer, " +
                ANSWER_DESC + " varchar(1024), " +
                ANSWER_IMAGE1 + " varchar(1024), " +
                "FOREIGN KEY (" + QUESTION_ID + ") REFERENCES " + questions.getQUESTIONS_TB() + "(" + questions.getID() + "));";
    }

    public String getDropAnswerTable() {
        return DROP_ANSWERS_TABLE;
    }

    public boolean createTBnData(SQLiteDatabase db) {
        db.execSQL(CREATE_ANSWERS_TABLE);

        StringBuffer answer = new StringBuffer();
        answer.append("insert into " + ANSWERS_TB + " values(1, 1, 'this is answer explationation for question#1', 'image is is null');");
        db.execSQL(answer.toString());

        StringBuffer answer1 = new StringBuffer();
        answer1.append("insert into " + ANSWERS_TB + " values(2, 2, 'this is answer explationation for question#2', 'image is is null');");
        db.execSQL(answer1.toString());

        return true;
    }

    public Cursor getAnswerForQuestion (SQLiteDatabase db, int question) {
        String[] columns = {ID, QUESTION_ID, ANSWER_DESC, ANSWER_IMAGE1};
        String[] columnValues = {String.valueOf(question)};
        cursor = db.query(ANSWERS_TB, columns, QUESTION_ID + "=?", columnValues, null, null, null);
        return cursor;
    }

    public String getID() {
        return ID;
    }

    public static String getAnsDesc() {
        return ANSWER_DESC;
    }

    public static String getAnsImage1() {
        return ANSWER_IMAGE1;
    }

    public int updateData(SQLiteDatabase db, String ansData) {
        String [] columns = {getID(),getAnsDesc() };
        int i=0;
        int id;
        int ques_id;
        String ans_desc=null;
        String ans_image1 = null;
        Cursor cursor = db.query(ANSWERS_TB,columns,null,null,null,null,null);

        cursor.moveToLast();
        int ans_id = cursor.getInt(cursor.getColumnIndex(ID));

        try {
            Log.d("json:", ansData);
            JSONArray answers = new JSONArray(ansData);
            ContentValues ans_values = new ContentValues();

            for (i = 0; i < answers.length(); i++) {
                JSONObject temp = answers.getJSONObject(i);
                id = temp.getInt(ID);
                ques_id = temp.getInt(QUESTION_ID);
                ans_desc = temp.getString(ANSWER_DESC);
                ans_image1 = temp.getString(ANSWER_IMAGE1);

                //Log.d("subjectid", "id:" + id);
                ans_values.put(QUESTION_ID, ques_id);
                ans_values.put(ANSWER_DESC, ans_desc);
                ans_values.put(ANSWER_IMAGE1, ans_image1);

                if ( id > ans_id ) {
                    /* add new data */
                    //Log.d("subjects:", "insert - id=" + id + "--sub_name:" + subj_name );
                    ans_values.put(ID, ++id);
                    db.insert(ANSWERS_TB,null, ans_values);

                } else {
                    /* update existing data */
                    //Log.d("subjects:", "update - id=" + id + "--sub_name:" + subj_name );
                    db.update(ANSWERS_TB,ans_values,ID + "=" + id,null);
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
