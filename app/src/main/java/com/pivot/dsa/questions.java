package com.pivot.dsa;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class questions extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout mTabLayout;
    private static RecyclerView recyclerView;
    private static questionsAdapter qAdapter;

    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.pager);
        mDemoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mDemoCollectionPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.setTabsFromPagerAdapter(mDemoCollectionPagerAdapter);

        mTabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
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

    public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {

        public DemoCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new DemoObjectFragment();
            Bundle args = new Bundle();
            args.putInt(DemoObjectFragment.ARG_OBJECT, i + 1); // Our object is just an integer :-P
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


    public static class  DemoObjectFragment extends Fragment {

        public static final String ARG_OBJECT = "Question No ";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int questionNo=0;
            View rootView = inflater.inflate(R.layout.fragment_fragment_questions, container, false);
            recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);

            qAdapter = new questionsAdapter(getActivity(),getData());
            recyclerView.setAdapter(qAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            Bundle args = getArguments();
            questionNo = args.getInt(ARG_OBJECT);

/*            ((TextView) rootView.findViewById(android.R.id.text1)).setText("Question No " +
                    Integer.toString(args.getInt(ARG_OBJECT))); */
            return rootView;
        }

        public List<QuestionOption> getData() {
            List<QuestionOption> data = new ArrayList<>();

            String [] optionNos = {"A ) ", "B ) ", "C ) ", "D )"};
            String [] optionValue = {"This is option A", "This is option B", "This is option C", "This is option D"};

            for (int i=0; i < optionNos.length; i++ ) {
                QuestionOption qOpt = new QuestionOption();
                qOpt.optionName = optionNos[i];
                qOpt.optionValue = optionValue[i];
                data.add(qOpt);
            }
            return data;
        }
    }
}
