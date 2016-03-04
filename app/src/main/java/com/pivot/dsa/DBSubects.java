package com.pivot.dsa;

/**
 * Created by shanthan on 3/2/2016.
 */
public class DBSubects {
    private static final String SUBJECTS_TB = "subjects";
    private static final String UID = "_id";
    private static final String SUB_NAME = "sub_name";
    private static final String LEVEL = "sub_level";
    private static final String CREATE_SUB_TABLE = "create table " + SUBJECTS_TB + " (" + UID +
            " integer primary key autoincrement," + SUB_NAME +
            " varchar(128), " + LEVEL + " integer);";

    private static  final String DROP_SUB_TABLE = "drop table if exists " + SUBJECTS_TB;

    public static String getCreateSubTable() {
        return CREATE_SUB_TABLE;
    }

    public static String getDropSubTable() {
        return DROP_SUB_TABLE;
    }

    public static String getSubjectsTb() {
        return SUBJECTS_TB;
    }

    public static String getUID() {
        return UID;
    }
}
