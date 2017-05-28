package com.pivot.dsa;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import static com.pivot.dsa.QuesAdapter.context;

/**
 * Created by shanthan on 6/12/2016.
 */
public class commonDefines {
    public static final String totalQues="totalQues";
    public static final String SUBJECTSLIST = "subjectsList";
    public static final int NOOFYEARS = 5;
    public static final int noOfOptions=4;
    public static final int PIN_QUES=1;
    public static final int UNPIN_QUES=0;
    private boolean alertDialogResponse = false;

    public boolean getUserConfirmation(Context subContext) {
        AlertDialog.Builder builder = new AlertDialog.Builder(subContext);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
        return alertDialogResponse;
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    alertDialogResponse = true;
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    alertDialogResponse = false;
                    break;
            }
        }
    };
}