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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shanthan on 6/11/2016.
 */
public class gotoQuesDialog  extends DialogFragment {
    Communicator communicator;
    Spinner spinner;

    public void onAttach (Activity activity) {
        super.onAttach(activity);
        communicator = (Communicator) getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int totalQuestions = getArguments().getInt(commonDefines.totalQues);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //builder.setTitle(R.string.gotoQuesDialogTitle);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.goto_question, null);
        builder.setView(view);

        spinner = (Spinner) view.findViewById(R.id.gotoQues);
        List<String> list = new ArrayList<String>();

        for ( int count=0; count < totalQuestions ; count++ ) {
            list.add("Q " + (count + 1) );
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int position = spinner.getSelectedItemPosition();
                communicator.gotoQuestion(position);
            }
        });

        Dialog dialog = builder.create();
        return dialog;
    }

    interface Communicator {
        public void gotoQuestion(int quesNo);
    }
}
