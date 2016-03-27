package com.pivot.dsa;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
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

    QuestionsPagerAdapter mQuestionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.pager);
        mQuestionsPagerAdapter = new QuestionsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mQuestionsPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.setTabsFromPagerAdapter(mQuestionsPagerAdapter);

        mTabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        subjectID = getIntent().getExtras().getInt(this.getResources().getStringArray(R.array.SubjectsTB)[0]);

        int chapterID = getIntent().getExtras().getInt(this.getResources().getStringArray(R.array.ChaptersTB)[0]);

        Message.message(this, "chapter id " + chapterID);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //String data = mEditText.getText();
        Intent intent = new Intent();
        intent.putExtra(this.getResources().getStringArray(R.array.SubjectsTB)[0],subjectID);
        setResult(RESULT_OK, intent);
    }

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
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    public class QuestionsPagerAdapter extends FragmentStatePagerAdapter {

        public QuestionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new QuestionsFragment();
            Bundle args = new Bundle();
            args.putInt(QuestionsFragment.ARG_OBJECT, i + 1); // Our object is just an integer :-P
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


    public static class QuestionsFragment extends Fragment implements AdapterView.OnItemClickListener {

        public static final String ARG_OBJECT = "Question No ";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int questionNo=0;
            View rootView = inflater.inflate(R.layout.fragment_fragment_questions, container, false);

            //String [] questionOptions = {"A. ", "B. ", "C. ", "D. "  };
            //String [] questionOptions1 = { "this is option A", "this is option B", "this is option C", "this is option D"};

            ListView listView;
            listView = (ListView) rootView.findViewById(R.id.quesOptions);
            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1, questionOptions);
            //listView.setAdapter(adapter);

            quesAdapter = new QuesAdapter(getActivity());
            listView.setAdapter(quesAdapter);
            //QuesAdapter singleRowAdappter  = new QuesAdapter(getActivity());

            listView.setOnItemClickListener(this);

            return rootView;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Message.message(getActivity(), "selected item at position " + position);
            view.setBackgroundResource(R.color.colorAppBar);
        }
    }

}

class QuesAdapter extends BaseAdapter {

    ArrayList<SingleRow> list;
    SingleRow item=null;
    static Context context;

    QuesAdapter (Context context) {
        String [] questionOptions = {"A. ", "B. ", "C. ", "D. "  };
        String [] questionOptions1 = { "this is option A", "this is option B", "this is option C", "this is option D"};
        list = new ArrayList<SingleRow>();

        for (int count = 0; count < 4; count++) {
            item = new SingleRow(questionOptions[count], questionOptions1[count], questionOptions1[count]);
            list.add(item);
        }

        this.context = context;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.question_custom_row,parent,false);
        TextView optNo = (TextView) row.findViewById(R.id.optionNo);
        TextView optValue = (TextView) row.findViewById(R.id.optionValue);
        ImageView optImage = (ImageView) row.findViewById(R.id.optionImage);

        SingleRow temp = list.get(position);
        optNo.setText(temp.optionNo);
        optValue.setText(temp.optionValue);
        //optImage.setImageResource(temp.optionImage);
        return row;
    }
}

class SingleRow {
    String optionNo;
    String  optionImage;
    String optionValue;

    SingleRow(String optNo,String optImg, String optValue ) {
        this.optionNo = optNo;
        this.optionImage = optImg;
        this.optionValue = optValue;
    }
}
