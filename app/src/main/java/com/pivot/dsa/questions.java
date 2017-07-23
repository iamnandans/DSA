package com.pivot.dsa;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Arrays;

public class questions extends AppCompatActivity implements fragmentQuestions.OnFragmentInteractionListener, gotoQuesDialog.Communicator {

    ViewPager viewPager;
    TabLayout mTabLayout;
    //TODO - initialize the array with no of questions after retrieving from database.
    int optionSelected[];
    //TODO: should be initialized to total questions from database
    int totalQuestions = 0;
    DBHelper dbHelper;
    Cursor cursor;
    DBAnswers dbAnswers;
    QuestionsPagerAdapter mQuestionsPagerAdapter;
    private int subjectID;
    private int chapterID;
    private String chapter_name = null;
    private int tabQuestionNo = -1;
    private int correctAnsOption = -1;
    private BottomSheetDialog dialog;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dialog = new BottomSheetDialog(this);

        subjectID = getIntent().getExtras().getInt(this.getResources().getStringArray(R.array.SubjectsTB)[0]);
        chapterID = getIntent().getExtras().getInt(this.getResources().getStringArray(R.array.ChaptersTB)[0]);
        chapter_name = getIntent().getExtras().getString(this.getResources().getStringArray(R.array.ChaptersTB)[2]);

        //Log.d("nandan123 ", "chapter Id is " + chapterID);

        this.setTitle(chapter_name);

        dbHelper = new DBHelper(this);
        cursor = dbHelper.getAllQuestionsForChapter(chapterID);

        dbAnswers = new DBAnswers(this);

        //Message.message(this, "total questions -- " + cursor.getCount());
        totalQuestions = cursor.getCount();

        viewPager = (ViewPager) findViewById(R.id.pager);
        mQuestionsPagerAdapter = new QuestionsPagerAdapter(getSupportFragmentManager(), this, subjectID, chapterID, cursor);
        viewPager.setAdapter(mQuestionsPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.setTabsFromPagerAdapter(mQuestionsPagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        //viewPager.setOnPageChangeListener();
        //mTabLayout.setupWithViewPager(viewPager);

        //Message.message(this, "chapter id " + chapterID);
        /* home button is disabled as of now, as event handling could not be done. Handling the
           event is required to display subject name in app title bar
         */
        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //TODO: array should be initialized to number of rows in database.
        optionSelected = new int[totalQuestions];
        Arrays.fill(optionSelected, -1);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                Log.d("nandan", "on tab selected called - " + tab.getPosition() + " -- " + optionSelected[tab.getPosition()]);
                //mTabLayout.setupWithViewPager(viewPager);
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //Log.d("nandan", "on tab unselected called-" + tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //Log.d("nandan", "on tab reselected called-" + tab.getPosition());
            }
        });

        //TabLayout.Tab tab = mTabLayout.getTabAt(0);
        //tab.select();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void optionSelected(int selectedOptionNo) {
        int tabNo = mTabLayout.getSelectedTabPosition();
//        Log.d("nandan", "tab-" + tabNo + ", optionNo-" + selectedOptionNo);
        optionSelected[tabNo] = selectedOptionNo;
    }

    /*
        tab number and question number in db are different. Setting the same here,
        which will be used which answer option is clicked
     */
    public void setQuestionNumber(int questionNo) {
        tabQuestionNo = questionNo;
    }

    /* set the answer for current question here and add in answer text at beginning.
       will be used when answer option is clicked an app bar
     */
    public void setAnswerForQuestion(int ansOption) {
        correctAnsOption = ansOption;
    }

    public int previousSelectedOption(int questionNo) {
        return optionSelected[mTabLayout.getSelectedTabPosition()];
    }

    public int getTabSelected() {
        return mTabLayout.getSelectedTabPosition();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar_ques, menu);
        return true;
    }

    public void getQuestions(Cursor cursor) {

    }

    /*
    @Override
    public void onBackPressed() {
        Message.message(this, "home button pressed");
        super.onBackPressed();
        //String data = mEditText.getText();
        Intent intent = new Intent();
        intent.putExtra(this.getResources().getStringArray(R.array.SubjectsTB)[0],subjectID);
        setResult(RESULT_OK, intent);
    }
    */
    /*
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    */
    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        Message.message(this, "keycode is " + keyCode);

        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Message.message(this, "Back button pressed....");
        }
        else if(keyCode == KeyEvent.KEYCODE_HOME)
        {
            Message.message(this, "home button presses!");
        }
        return super.onKeyDown(keyCode, event);
    }
    */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_goto_ques) {
            //Message.message(this, "goto question clicked");
            //TabLayout.Tab tab = mTabLayout.getTabAt(5);
            //tab.select();

            if (totalQuestions == 0) {
                Toast.makeText(this, getResources().getString(R.string.ques_zeo_question), Toast.LENGTH_LONG).show();
            } else if (totalQuestions < 3) {
                Toast.makeText(this, getResources().getString(R.string.ques_total_questions), Toast.LENGTH_LONG).show();
            } else {
                gotoQuesDialog quesDialog = new gotoQuesDialog();
                Bundle args = new Bundle();
                args.putInt(commonDefines.totalQues, totalQuestions);
                quesDialog.setArguments(args);
                quesDialog.show(getFragmentManager(), "Select Question Alert");
            }

        } else if (id == R.id.action_pin) {
            //dbHelper.updatePin(tabQuestionNo,commonDefines.PIN_QUES);
            //Message.message(this, "action pin clicked " + tabQuestionNo + " -- pin value is  " + cursor.getInt(2));
            if (cursor == null) {
                Message.message(this, "Could not pin the Question");
            } else if (cursor.getCount() < 1) {
                Message.message(this, "Incorrect Question");
            } else {
                int selectedTab=0;

                /* initially tab selected will have -1 value */
                if ( -1 == getTabSelected() ) {
                    selectedTab = 0;
                } else {
                    selectedTab = getTabSelected();
                }

                cursor.moveToPosition(selectedTab);
                //Log.d("nandan123", "question id is " + cursor.getInt(cursor.getColumnIndex(DBQuestions.getUID())));
                //Log.d("nandan123", "question id is " + cursor.getInt(cursor.getColumnIndex(DBQuestions.getUID())));
                if (cursor.getInt(cursor.getColumnIndex(DBQuestions.getPinValue())) == 1) {
                    dbHelper.updatePin(cursor.getInt(cursor.getColumnIndex(DBQuestions.getUID())), 0);
                    Message.message(this, getString(R.string.remove_ques_fav));
                } else {
                    dbHelper.updatePin(cursor.getInt(cursor.getColumnIndex(DBQuestions.getUID())), 1);
                    Message.message(this, getString(R.string.add_ques_fav));
                }

                /* pin value is changes as such data needs to be refreshed in cursor. So reloading the data */
                cursor = dbHelper.getAllQuestionsForChapter(chapterID);
                cursor.moveToPosition(getTabSelected());
            }

        } else if (id == R.id.home) {
            /*Intent intent = new Intent();
            intent.putExtra(this.getResources().getStringArray(R.array.SubjectsTB)[0],subjectID);
            setResult(RESULT_OK, intent);*/
            //NavUtils.navigateUpFromSameTask(this);
            //super.onBackPressed();
            //Message.message(this, "testing home button");
        } else if (id == R.id.action_ans) {
            String ans_desc = null;
            Cursor cursorAns = null;
            int selectedTab=0;

            /* initially tab selected will have -1 value */
            if ( -1 == getTabSelected() ) {
                selectedTab = 0;
            } else {
                selectedTab = getTabSelected();
            }

            if ( cursor == null ) {
                Message.message(this, "Could not find explanation for answer");
            } else if (cursor.getCount() < 1) {
                Message.message(this, "Incorrect Question");
            } else {
                cursor.moveToPosition(selectedTab);

                try {
                    int questionID=0;
                    questionID = cursor.getInt(cursor.getColumnIndex(DBQuestions.getUID()));
                    //Log.d("nandan123", "question id is " + questionID);
                    cursorAns = dbHelper.getAnswerForQuestion(questionID);
                    cursorAns.moveToFirst();
                    ans_desc = "(" + cursor.getInt(cursor.getColumnIndex(DBQuestions.getAnswer())) + ") " + getResources().getString(R.string.ques_correct_option) + "\n \n";
                    ans_desc = ans_desc + cursorAns.getString(cursorAns.getColumnIndex(DBAnswers.getAnsDesc()));

                } catch (Exception e) {
                    if ( cursorAns !=  null ) {
                        cursorAns.close();
                    }
                    ans_desc = ans_desc + "Could not find answer description";
                }
                View view = getLayoutInflater().inflate(R.layout.answer_bottomsheet, null);
                TextView tv = (TextView) view.findViewById(R.id.ans_text);
                tv.setText(ans_desc);
                // image for answer can be set here
                //ImageView ans_image = (ImageView) view.findViewById(R.id.ans_image);

                dialog.setContentView(view);
                dialog.show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void gotoQuestion(int quesNo) {
        //Toast.makeText(this, "finally ---- " + message, Toast.LENGTH_SHORT).show();
        TabLayout.Tab tab = mTabLayout.getTabAt(quesNo);
        tab.select();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "questions Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.pivot.dsa/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "questions Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.pivot.dsa/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}

class QuestionsPagerAdapter extends FragmentStatePagerAdapter {
    Context context;
    int subjectID;
    int chapterID;
    int totalQues;
    Cursor cursor;

    public QuestionsPagerAdapter(FragmentManager fm, Context context, int subjectID, int chapterID, Cursor cursor) {
        super(fm);
        this.context = context;
        this.subjectID = subjectID;
        this.chapterID = chapterID;
        this.totalQues = cursor.getCount();
        this.cursor = cursor;
    }

    @Override
    public Fragment getItem(int i) {
        Bundle args = new Bundle();

        args.putInt(fragmentQuestions.QUES_NO, (i) ); // Our object is just an integer :-P
        args.putInt(fragmentQuestions.CHAP_NO, this.chapterID); // Our object is just an integer :-P
        args.putInt(fragmentQuestions.SUB_NO, this.subjectID); // Our object is just an integer :-P

        //Log.d("nandan123" , "chapter id is -- " + this.chapterID);

        Fragment fragment = new fragmentQuestions();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return this.totalQues;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Q " + (position + 1);
    }

}