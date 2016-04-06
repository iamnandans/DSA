package com.pivot.dsa;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class questions extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout mTabLayout;
    private static RecyclerView recyclerView;
    private static questionsAdapter qAdapter;
    private static QuesAdapter quesAdapter;
    private int subjectID;
    private int chapterID;

    QuestionsPagerAdapter mQuestionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        subjectID = getIntent().getExtras().getInt(this.getResources().getStringArray(R.array.SubjectsTB)[0]);
        chapterID = getIntent().getExtras().getInt(this.getResources().getStringArray(R.array.ChaptersTB)[0]);

        viewPager = (ViewPager) findViewById(R.id.pager);
        mQuestionsPagerAdapter = new QuestionsPagerAdapter(getSupportFragmentManager(), this, subjectID, chapterID );
        viewPager.setAdapter(mQuestionsPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.setTabsFromPagerAdapter(mQuestionsPagerAdapter);

        mTabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));


        Message.message(this, "chapter id " + chapterID);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void getQuestions(Cursor cursor) {

    }

    /*
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //String data = mEditText.getText();
        Intent intent = new Intent();
        intent.putExtra(this.getResources().getStringArray(R.array.SubjectsTB)[0],subjectID);
        setResult(RESULT_OK, intent);
    }
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if ( id == R.id.home ) {
            /*Intent intent = new Intent();
            intent.putExtra(this.getResources().getStringArray(R.array.SubjectsTB)[0],subjectID);
            setResult(RESULT_OK, intent);*/
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }



}
class QuestionsPagerAdapter extends FragmentStatePagerAdapter {
    Context context;
    int subjectID;
    int chapterID;

    public QuestionsPagerAdapter(FragmentManager fm, Context context, int subjectID, int chapterID) {
        super(fm);
        this.context = context;
        this.subjectID = subjectID;
        this.chapterID = chapterID;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new fragmentQuestions();
        Bundle args = new Bundle();
        args.putInt(fragmentQuestions.QUES_NO, i + 1); // Our object is just an integer :-P
        args.putInt(fragmentQuestions.CHAP_NO, chapterID); // Our object is just an integer :-P
        args.putInt(fragmentQuestions.SUB_NO, subjectID); // Our object is just an integer :-P
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        // For this contrived example, we have a 100-object collection.
        return 100;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Ques " + (position + 1);
    }
}