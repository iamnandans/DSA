package com.pivot.dsa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by shanthan on 3/6/2016.
 */
public class DBQuestions {
    private String QUESTIONS_TB = "questions";
    private static String UID = "_id";
    private static String ID = "id";
    private static String CHAPTER_ID = "chapterID";
    private static String YEAR = "year";
    private static String LEVEL = "level";
    private static String QUESTION = "question";
    private static String OPTION1 = "option1";
    private static String OPTION2 = "option2";
    private static String OPTION3 = "option3";
    private static String OPTION4 = "option4";
    private static String ANSWER = "answer";
    private static String PINNED = "pinned";
    private Context context;
    private DBChapters chapters;
    private String CREATE_QUESTIONS_TABLE;

    DBQuestions(Context context) {
        this.context = context;
        chapters = new DBChapters(this.context);

        CREATE_QUESTIONS_TABLE = "create table " + QUESTIONS_TB + " (" +
                ID + " integer primary key, " +
                CHAPTER_ID + " integer, " +
                YEAR + " integer, " +
                LEVEL + " integer, " +
                QUESTION + " varchar(1024), " +
                OPTION1 + " varchar(1024), " +
                OPTION2 + " varchar(1024), " +
                OPTION3 + " varchar(1024), " +
                OPTION4 + " varchar(1024), " +
                ANSWER + " integer, " +
                PINNED + " integer, " +
                "FOREIGN KEY (" + CHAPTER_ID + ") REFERENCES " + chapters.getChaptersTb() + "(" + chapters.getID() + "));";
    }

    private String DROP_QUESTIONS_TABLE = "drop table if exists " + QUESTIONS_TB;

    public String getQUESTIONS_TB() {
        return QUESTIONS_TB;
    }

    public static String getID() {
        return ID;
    }

    public static String getUID() {
        return UID;
    }

    public static String getChapterID() {
        return CHAPTER_ID;
    }

    public static String getQuestion() {
        return QUESTION;
    }

    public static String getOption1() {
        return OPTION1;
    }

    public static String getOption2() {
        return OPTION2;
    }

    public static String getOption3() {
        return OPTION3;
    }

    public static String getOption4() {
        return OPTION4;
    }

    public static String getAnswer() {
        return ANSWER;
    }

    public static String getPinValue() { return PINNED; }

    public boolean createTBnData(SQLiteDatabase db) {
        db.execSQL(CREATE_QUESTIONS_TABLE);

        StringBuffer question = new StringBuffer();
        question.append("insert into " + QUESTIONS_TB + " values(1, 1, 2014, 1, 'this is question1 in year 2014 with level 1', " +
                " 'option1','option2','option3','option4',1,0 );");
        db.execSQL(question.toString());

        StringBuffer question1 = new StringBuffer();
        question1.append("insert into " + QUESTIONS_TB + " values(2, 2, 2014, 1, 'this is question2 in year 2014 with level 2', " +
                " 'option1','option2','option3','option4',2,0 );");
        db.execSQL(question1.toString());

        //db.execSQL(getAllChaptersForSubject());
        return true;
    }

    public String getDropQuesionsTable() {
        return DROP_QUESTIONS_TABLE;
    }

    public Cursor getAllQuestionsForChapter (SQLiteDatabase db, int chapterIDV) {
        String[] columns = {ID + " _id", CHAPTER_ID, YEAR, LEVEL, QUESTION, OPTION1, OPTION2, OPTION3, OPTION4, ANSWER, PINNED };
        String[] columnValues = {String.valueOf(chapterIDV)};
        Cursor cursor = db.query(QUESTIONS_TB, columns, CHAPTER_ID + "=?", columnValues, null, null, null);

        return cursor;
    }

    public int updateData(SQLiteDatabase db, String quesData) {
        String [] columns = {getID(),QUESTION};
        int i=0;
        int id;
        int chap_id;
        int year;
        int level;
        String question=null;
        String option1=null;
        String option2 = null;
        String option3 = null;
        String option4 = null;
        int answer;
        int pinned;

        Cursor cursor = db.query(QUESTIONS_TB, columns, null, null, null, null, null);

        cursor.moveToLast();
        int ques_id = cursor.getInt(cursor.getColumnIndex(ID));

        try {
            Log.d("json:", quesData);
            JSONArray questions = new JSONArray(quesData);
            ContentValues ques_values = new ContentValues();

            for (i = 0; i < questions.length(); i++) {
                JSONObject temp = questions.getJSONObject(i);
                id = temp.getInt(ID);
                chap_id = temp.getInt(CHAPTER_ID);
                year = temp.getInt(YEAR);
                level = temp.getInt(LEVEL);
                question = temp.getString(QUESTION);
                option1 = temp.getString(OPTION1);
                option2 = temp.getString(OPTION2);
                option3 = temp.getString(OPTION3);
                option4 = temp.getString(OPTION4);
                answer = temp.getInt(ANSWER);
                pinned = temp.getInt(PINNED);

                ques_values.put(CHAPTER_ID,chap_id);
                ques_values.put(YEAR, year);
                ques_values.put(LEVEL, level);
                ques_values.put(QUESTION, question);
                ques_values.put(OPTION1, option1);
                ques_values.put(OPTION2, option2);
                ques_values.put(OPTION3, option3);
                ques_values.put(OPTION4, option4);
                ques_values.put(ANSWER, answer);
                ques_values.put(PINNED, pinned);

                if ( id > ques_id ) {
                    /* add new data */
                    //Log.d("subjects:", "insert - id=" + id + "--sub_name:" + subj_name );
                    ques_values.put(ID, ++id);
                    db.insert(QUESTIONS_TB,null, ques_values);

                } else {
                    /* update existing data */
                    //Log.d("subjects:", "update - id=" + id + "--sub_name:" + subj_name );
                    db.update(QUESTIONS_TB,ques_values,ID + "=" + id,null);
                }
            }

        } catch (Exception e) {
            Log.d("JSON", "Exception: converting to json array " + e.toString());
            return 0;
        }

        cursor.close();
        return i;
    }

    public int updatePin(SQLiteDatabase db,int quesID, int pinValue ) {
        ContentValues ques_values = new ContentValues();
        ques_values.put(PINNED,pinValue);
        try {
            db.update(QUESTIONS_TB, ques_values, ID + "=" + quesID, null);
        } catch (Exception e ) {
            Log.d("DB update ERROR", "Error updating pin value for question. " + e.toString());
        }
        return(1);
    }
}
