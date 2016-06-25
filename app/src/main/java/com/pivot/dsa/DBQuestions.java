package com.pivot.dsa;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by shanthan on 3/6/2016.
 */
public class DBQuestions {
    private String QUESTIONS_TB = "questions";
    private static String UID = "_id";
    private static String chapterID = "chapterID";
    private static String year = "year";
    private static String level = "level";
    private static String question = "question";
    private static String option1 = "option1";
    private static String option2 = "option2";
    private static String option3 = "option3";
    private static String option4 = "option4";
    private static String answer = "answer";
    private Context context;
    private DBChapters chapters;
    private String CREATE_QUESTIONS_TABLE;

    DBQuestions(Context context) {
        this.context = context;
        chapters = new DBChapters(this.context);

        CREATE_QUESTIONS_TABLE = "create table " + QUESTIONS_TB + " (" +
                UID + " integer primary key, " +
                chapterID + " integer, " +
                year + " integer, " +
                level + " integer, " +
                question + " varchar(1024), " +
                option1 + " varchar(1024), " +
                option2 + " varchar(1024), " +
                option3 + " varchar(1024), " +
                option4 + " varchar(1024), " +
                answer + " integer, " +
                "FOREIGN KEY (" + chapterID + ") REFERENCES " + chapters.getChaptersTb() + "(" + chapters.getUID() + "));";


    }

    private String DROP_QUESTIONS_TABLE = "drop table if exists " + QUESTIONS_TB;

    public String getQUESTIONS_TB() {
        return QUESTIONS_TB;
    }

    public static String getUID() {
        return UID;
    }

    public static String getChapterID() {
        return chapterID;
    }

    public static String getQuestion() {
        return question;
    }

    public static String getOption1() {
        return option1;
    }

    public static String getOption2() {
        return option2;
    }

    public static String getOption3() {
        return option3;
    }

    public static String getOption4() {
        return option4;
    }

    public static String getAnswer() {
        return answer;
    }

    public boolean createTBnData(SQLiteDatabase db) {
        db.execSQL(CREATE_QUESTIONS_TABLE);

        StringBuffer question = new StringBuffer();
        question.append("insert into " + QUESTIONS_TB + " values(1, 1, 2014, 1, 'this is question1 in year 2014 with level 1', " +
                " 'option1','option2','option3','option4',1 );");
        db.execSQL(question.toString());

        StringBuffer question1 = new StringBuffer();
        question1.append("insert into " + QUESTIONS_TB + " values(2, 2, 2014, 1, 'this is question2 in year 2014 with level 2', " +
                " 'option1','option2','option3','option4',2 );");
        db.execSQL(question1.toString());

        //db.execSQL(getAllChaptersForSubject());
        return true;
    }

    public String getDropQuesionsTable() {
        return DROP_QUESTIONS_TABLE;
    }

    public Cursor getAllQuestionsForChapter (SQLiteDatabase db, int chapterIDV) {
        String[] columns = {UID, chapterID, year, level, question, option1, option2, option3, option4, answer};
        String[] columnValues = {String.valueOf(chapterIDV)};
        Cursor cursor = db.query(QUESTIONS_TB, columns, chapterID + "=?", columnValues, null, null, null);

        return cursor;
    }
}
