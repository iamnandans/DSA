package com.pivot.dsa;

/**
 * Created by shanthan on 3/2/2016.
 */
public class DBChapters {
    private static final String CHAPTERS_TB = "chapters";
    private static final String UID = "_id";
    private static final String CHAP_NAME = "chap_name";
    private static final String LEVEL = "chap_level";
    private static final String SUBJECT_ID = "subj_id";

    private static final String CREATE_SUB_TABLE = "create table " + CHAPTERS_TB + " (" +
            UID + " integer primary key autoincrement," +
            SUBJECT_ID + " integer," +
            CHAP_NAME + " varchar(128), " +
            LEVEL + " integer, " +
            "FOREIGN KEY (" + SUBJECT_ID + ") REFERENCES " + DBSubects.getSubjectsTb() + "(" + DBSubects.getUID() + "));";

    private static final String DROP_CHAP_TABLE = "drop table if exists " + CHAPTERS_TB;

    public static String getCreateSubTable() {
        return CREATE_SUB_TABLE;
    }

    public static String getDropSubTable() {
        return DROP_CHAP_TABLE;
    }

    public static String getChaptersTb() {
        return CHAPTERS_TB;
    }

    public static String getUID() {
        return UID;
    }
}
