package com.pivot.dsa;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shanthan on 5/28/2017.
 */

public class subjectYear extends DialogFragment {
    Communicator communicator;
    Spinner spinner;

    public void onAttach (Activity activity) {
        super.onAttach(activity);
        communicator = (subjectYear.Communicator) getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //int totalQuestions = getArguments().getInt(commonDefines.SUBJECTSLIST);
        //String [] subList = getArguments().getStringArray(commonDefines.SUBJECTSLIST);
        String[] subList = new String[10];
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setTitle(R.string.gotoQuesDialogTitle);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.subject_year, null);
        builder.setView(view);

        spinner = (Spinner) view.findViewById(R.id.spSubYear);
        List<String> list_sub = new ArrayList<String>();

        for ( int count=0; count < subList.length ; count++ ) {
            list_sub.add(subList[count]);
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list_sub);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int position = spinner.getSelectedItemPosition();
                communicator.subjectYear(position);
            }
        });

        Dialog dialog = builder.create();
        return dialog;
    }

    interface Communicator {
        public void subjectYear(int quesNo);
    }
}

