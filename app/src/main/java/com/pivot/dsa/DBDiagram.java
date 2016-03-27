package com.pivot.dsa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by shanthan on 3/6/2016.
 */
public class DBDiagram {

    private String DIAGRAM_TB = "diagram";
    private String UID = "_id";
    private String questionID = "questionID";
    private String qDiagram = "ques_diagram";
    private String option1Diagram = "opt1Diagram";
    private String option2Diagram = "opt2Diagram";
    private String option3Diagram = "opt3Diagram";
    private String option4Diagram = "opt4Diagram";
    private String ansDiagram = "ansDiagram";
    private DBQuestions question;
    private Context context;

    private String CREATE_DIAGRAM_TABLE ;

    DBDiagram (Context context) {
        this.context = context;
        question = new DBQuestions(this.context);
        CREATE_DIAGRAM_TABLE = "create table " + DIAGRAM_TB + " (" +
                UID + " integer primary key, " +
                questionID + " integer, " +
                qDiagram + " varchar(256), " +
                option1Diagram + " varchar(256), " +
                option2Diagram + " varchar(256), " +
                option3Diagram + " varchar(256), " +
                option4Diagram + " varchar(256), " +
                ansDiagram + " varchar(256), " +
                "FOREIGN KEY (" + questionID + ") REFERENCES " + question.getQUESTIONS_TB() + "(" + question.getUID() + "));";
    }

    private String DROP_DIAGRAM_TABLE = "drop table if exists " + DIAGRAM_TB;

    public boolean createTBnData(SQLiteDatabase db) {
        db.execSQL(CREATE_DIAGRAM_TABLE);

        StringBuffer diagram = new StringBuffer();
        diagram.append("insert into " + DIAGRAM_TB + " values(1, 1, '/data/data/DSA/questionDia1','/data/data/DSA/image1'," +
                       "'/data/data/DSA/image2','/data/data/DSA/image3','/data/data/DSA/image4','/data/data/DSA/andDia');") ;
        db.execSQL(diagram.toString());

        StringBuffer diagram1 = new StringBuffer();
        diagram1.append("insert into " + DIAGRAM_TB + " values(2, 2, '/data/data/DSA/questionDia1','/data/data/DSA/image1'," +
                "'/data/data/DSA/image2','/data/data/DSA/image3','/data/data/DSA/image4','/data/data/DSA/andDia');") ;
        db.execSQL(diagram1.toString());

        //db.execSQL(getAllChaptersForSubject());
        return true;
    }

    public String getDropDiagramTable() {
        return DROP_DIAGRAM_TABLE;
    }
}
