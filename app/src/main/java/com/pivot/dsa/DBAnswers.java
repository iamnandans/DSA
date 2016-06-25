package com.pivot.dsa;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

/**
 * Created by shanthan on 6/25/2016.
 */
public class DBAnswers {
    private String ANSWERS_TB = "answers";
    private static String UID = "_id";

    private static String questionID = "questionID";
    private static String answer_desc = "answer_desc";
    private static String answer_image1 = "answer_image1";
    private DBQuestions questions;

    private String CREATE_ANSWERS_TABLE;
    private String DROP_ANSWERS_TABLE = "drop table if exists " + ANSWERS_TB;

    private Context context;
    private Cursor cursor;

    DBAnswers(Context context) {
        this.context = context;
        questions = new DBQuestions(this.context);

        CREATE_ANSWERS_TABLE = "create table " + ANSWERS_TB + " (" +
                UID + " integer primary key, " +
                questionID + " integer, " +
                answer_desc + " varchar(1024), " +
                answer_image1 + " varchar(1024), " +
                "FOREIGN KEY (" + questionID + ") REFERENCES " + questions.getQUESTIONS_TB() + "(" + questions.getUID() + "));";
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
        String[] columns = {UID, questionID, answer_desc, answer_image1};
        String[] columnValues = {String.valueOf(question)};
        cursor = db.query(ANSWERS_TB, columns, questionID + "=?", columnValues, null, null, null);
        return cursor;
    }

    public static String getAnsDesc() {
        return answer_desc;
    }

    public static String getAnsImage1() {
        return answer_desc;
    }
}
