package com.pivot.dsa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by shanthan on 3/6/2016.
 */
public class DBQuestions {
    private String QUESTIONS_TB = "questions";
    private String UID = "_id";
    private String chapterID = "chapterID";
    private String year = "year";
    private String level = "level";
    private String question = "question";
    private String option1 = "option1";
    private String option2 = "option2";
    private String option3 = "option3";
    private String option4 = "option4";
    private String answer = "answer";
    private Context context;
    private DBChapters chapters;
    private String CREATE_QUESTIONS_TABLE;

    DBQuestions (Context context) {
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
                answer + " varchar(1024), " +
                "FOREIGN KEY (" + chapterID + ") REFERENCES " + chapters.getChaptersTb() + "(" + chapters.getUID() + "));";

    }

    private String DROP_QUESTIONS_TABLE = "drop table if exists " + QUESTIONS_TB;

    public String getQUESTIONS_TB() {
        return QUESTIONS_TB;
    }

    public String getUID() {
        return UID;
    }

    public boolean createTBnData(SQLiteDatabase db) {
        db.execSQL(CREATE_QUESTIONS_TABLE);

        StringBuffer question = new StringBuffer();
        question.append("insert into " + QUESTIONS_TB + " values(1, 1, 2014, 1, 'this is question1 in year 2014 with level 1', " +
                        " 'option1','option2','option3','option4','this is the explanation for the question' );") ;
        db.execSQL(question.toString());

        StringBuffer question1 = new StringBuffer();
        question1.append("insert into " + QUESTIONS_TB + " values(2, 2, 2014, 1, 'this is question2 in year 2014 with level 2', " +
                " 'option1','option2','option3','option4','this is the explanation for the question' );") ;
        db.execSQL(question1.toString());

        //db.execSQL(getAllChaptersForSubject());
        return true;
    }

    public String getDropQuesionsTable() {
        return DROP_QUESTIONS_TABLE;
    }
}
