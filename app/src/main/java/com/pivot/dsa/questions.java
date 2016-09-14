package com.pivot.dsa;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;

import java.util.Arrays;

public class questions extends AppCompatActivity implements fragmentQuestions.OnFragmentInteractionListener, gotoQuesDialog.Communicator {

    ViewPager viewPager;
    TabLayout mTabLayout;
    private static RecyclerView recyclerView;
    private static questionsAdapter qAdapter;
    private static QuesAdapter quesAdapter;
    private int subjectID;
    private int chapterID;
    private String chapter_name=null;
    //TODO - initialize the array with no of questions after retrieving from database.
    int optionSelected[];
    final int noOfChoices = commonDefines.noOfOptions;
    //TODO: should be initialized to total questions from database
    int totalQuestions = 0;
    DBHelper dbHelper;
    Cursor cursor;
    DBAnswers dbAnswers;
    private int tabQuestionNo=-1;
    private int correctAnsOption=-1;

    QuestionsPagerAdapter mQuestionsPagerAdapter;
    private BottomSheetDialog dialog ;

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

        this.setTitle(chapter_name);

        dbHelper = new DBHelper(this);
        cursor = dbHelper.getAllQuestionsForChapter(chapterID);

        dbAnswers = new DBAnswers(this);

        //Message.message(this, "total questions -- " + cursor.getCount());
        totalQuestions = cursor.getCount();

        viewPager = (ViewPager) findViewById(R.id.pager);
        mQuestionsPagerAdapter = new QuestionsPagerAdapter(getSupportFragmentManager(), this, subjectID, chapterID, cursor );
        viewPager.setAdapter(mQuestionsPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.setTabsFromPagerAdapter(mQuestionsPagerAdapter);

        mTabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));


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
                ListView lv = (ListView) findViewById(R.id.quesOptions);
                int tabPosition = tab.getPosition();

                if ( optionSelected[tabPosition] != -1 ) {
                    for (int count=0; count < noOfChoices; count++ ) {
                        if ( count == optionSelected[tabPosition] )
                            lv.getChildAt(count).setSelected(true);
                        else
                            lv.getChildAt(count).setSelected(false);
                    }
                }
                else {
                    for (int count=0; count < noOfChoices; count++ ) {
                        lv.getChildAt(count).setSelected(false);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public void optionSelected(int selectedOptionNo) {
        int tabNo = mTabLayout.getSelectedTabPosition();
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

            if ( totalQuestions == 0 ) {
                Toast.makeText(this, getResources().getString(R.string.ques_zeo_question) , Toast.LENGTH_LONG).show();
            } else if ( totalQuestions < 3 ) {
                Toast.makeText(this, getResources().getString(R.string.ques_total_questions), Toast.LENGTH_LONG).show();
            } else {
                gotoQuesDialog quesDialog = new gotoQuesDialog();
                Bundle args = new Bundle();
                args.putInt(commonDefines.totalQues, totalQuestions);
                quesDialog.setArguments(args);
                quesDialog.show(getFragmentManager(), "Select Question Alert");
            }

        } else if (id == R.id.action_pin ) {
            //dbHelper.updatePin(tabQuestionNo,commonDefines.PIN_QUES);
            Message.message(this, "action pin clicked " + tabQuestionNo);
        }
        else if ( id == R.id.home ) {
            /*Intent intent = new Intent();
            intent.putExtra(this.getResources().getStringArray(R.array.SubjectsTB)[0],subjectID);
            setResult(RESULT_OK, intent);*/
            //NavUtils.navigateUpFromSameTask(this);
            //super.onBackPressed();
            //Message.message(this, "testing home button");
        }
        else if ( id == R.id.action_ans ) {
            String ans_desc=null;
            Cursor cursorAns = null;

            if ( correctAnsOption == -1 ) {
                Toast.makeText(this, getResources().getString(R.string.ques_invalid), Toast.LENGTH_LONG).show();
            } else {

                ans_desc = "(" + correctAnsOption + ") " + getResources().getString(R.string.ques_correct_option) + "\n \n";
                try {
                    cursorAns = dbHelper.getAnswerForQuestion(tabQuestionNo);
                    String[] col = cursorAns.getColumnNames();
                    cursorAns.moveToFirst();
                    ans_desc = ans_desc + cursorAns.getString(cursorAns.getColumnIndex(DBAnswers.getAnsDesc()));
                } catch (Exception e) {
                    //cursorAns.close();
                    //ans_desc = ans_desc + "Could not find answer description";
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
        int selectedTab;
        questions ques = (questions) context;
        selectedTab = ques.getTabSelected();
        Fragment fragment = new fragmentQuestions();
        Bundle args = new Bundle();
        args.putInt(fragmentQuestions.QUES_NO, selectedTab); // Our object is just an integer :-P
        args.putInt(fragmentQuestions.CHAP_NO, chapterID); // Our object is just an integer :-P
        args.putInt(fragmentQuestions.SUB_NO, subjectID); // Our object is just an integer :-P
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