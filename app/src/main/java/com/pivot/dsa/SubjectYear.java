package com.pivot.dsa;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

/**
 * Created by shanthan on 5/28/2017.
 */

public class SubjectYear extends DialogFragment {
    Communicator communicator;
    Spinner spinnerSubject;
    Spinner spinnerYear;

    public void onAttach (Activity activity) {
        super.onAttach(activity);
        communicator = (Communicator) getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //int totalQuestions = getArguments().getInt(commonDefines.SUBJECTSLIST);
        //String [] subList = getArguments().getStringArray(commonDefines.SUBJECTSLIST);
        String[] subList = new String[10];
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setTitle(R.string.subjectYear);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.subject_year, null);
        builder.setView(view);

        spinnerSubject = (Spinner) view.findViewById(R.id.spSub);
        spinnerYear = (Spinner) view.findViewById(R.id.spYear);

        DBHelper dbHelper = new DBHelper(getActivity());
        Cursor cursor = dbHelper.getAllSubjects();

        String[] fromFieldNames = new String[]{DBSubjects.SUB_NAME};
        int[] viewIDs = new int[]{R.id.name};

        SimpleCursorAdapter cursorAdapterSubj;
        cursorAdapterSubj = new SimpleCursorAdapter(getActivity(), R.layout.single_row, cursor, fromFieldNames, viewIDs, 0);
        spinnerSubject.setAdapter(cursorAdapterSubj);

        Cursor cursorYear = dbHelper.getLast5Years();

        String[] fromFieldNamesYear = new String[]{DBQuestions.getYEAR()};
        int[] viewIDsYear = new int[]{R.id.name};

        SimpleCursorAdapter cursorAdapterYear;
        cursorAdapterYear = new SimpleCursorAdapter(getActivity(), R.layout.single_row, cursorYear, fromFieldNamesYear, viewIDsYear, 0);
        spinnerYear.setAdapter(cursorAdapterYear);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String subSelected = ((Cursor) spinnerSubject.getSelectedItem()).getString(1).toString();
                String yearSelected = ((Cursor) spinnerYear.getSelectedItem()).getString(1).toString();
                //Log.d("nandan123", "Select subject is " + subSelected + ", year " + yearSelected);
                communicator.subjectYear(subSelected, yearSelected);
            }
        });
        Dialog dialog = builder.create();
        return dialog;
    }

    interface Communicator {
        public void subjectYear(String subject, String year);
    }
}

