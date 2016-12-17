package com.pivot.dsa;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fragmentQuestions.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragmentQuestions#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentQuestions extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public static final String QUES_NO="0";
    public static final String CHAP_NO="0";
    public static final String SUB_NO="0";

    static DBHelper dbHelper;
    static Cursor cursor;
    static int chapterNo;
    static int subjectNo;
    static ArrayList<SingleRow> list;
    SingleRow item=null;
    int maxQuesOpt=0;
    Bundle args = getArguments();
    static int questionNo = -1;
    QuesAdapter quesAdapter;
    ListView listView;
    View rootView;
    questions ques;


    public fragmentQuestions() {
        // Required empty public constructor
        //this.cursor = cursor;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentQuestions.
     */
    // TODO: Rename and change types and number of parameters

    /*
    public static fragmentQuestions newInstance(String param1, String param2) {
        fragmentQuestions fragment = new fragmentQuestions();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_fragment_questions, container, false);
        list = new ArrayList<SingleRow>();
        maxQuesOpt = this.getResources().getInteger(R.integer.MaxQuesOpt);

        questionNo = getArguments().getInt(QUES_NO);
        chapterNo = getArguments().getInt(CHAP_NO);
        subjectNo = getArguments().getInt(SUB_NO);

        ques = (questions) getActivity();
        int selectedQues = ques.getTabSelected();

        String [] questionOptions = {"A. ", "B. ", "C. ", "D. "  };
        //String [] questionOptions1 = { "this is option A", "this is option B", "this is option C", "this is option D"};
        String [] questionOptions1 = new String[maxQuesOpt];

        Message.message(getActivity(), "nandan - question no is " + questionNo);
        if ( questionNo != -1 ) {
            dbHelper = new DBHelper(getActivity());
            cursor = dbHelper.getAllQuestionsForChapter(chapterNo);
            //TODO : have to handle this condition
            //if ( cursor == null )
            //cursor.moveToPosition(questionNo-1);
            cursor.moveToFirst();
        } else {
            cursor.move(questionNo);
        }

        //String[] col = cursor.getColumnNames();
        final String question = cursor.getString(cursor.getColumnIndex(DBQuestions.getQuestion()));
        int answerOption = cursor.getInt(cursor.getColumnIndex(DBQuestions.getAnswer()));

        TextView tvQues = (TextView) rootView.findViewById(R.id.question);
        tvQues.setText(question);

        final int dbQuesID = cursor.getInt(cursor.getColumnIndex(DBQuestions.getUID()));
        ques.setQuestionNumber(dbQuesID);

        ques.setAnswerForQuestion(answerOption);
        //int count=0;

        listView = (ListView) rootView.findViewById(R.id.quesOptions);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1, questionOptions);
        //listView.setAdapter(adapter);
        //listView.setOnItemClickListener(onItemClick(rootView.findViewById(android.R.id.content)));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //questions ques = (questions) getActivity();
                ques.optionSelected(position);
                view.setSelected(true);
            }
        });
        quesAdapter = new QuesAdapter(getActivity(), cursor, selectedQues);
        listView.setAdapter(quesAdapter);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

class QuesAdapter extends BaseAdapter {

    ArrayList<SingleRow> list;
    SingleRow item=null;
    static Context context;
    Cursor cursor;
    int questionNo=0;

    QuesAdapter (Context context, Cursor cursor, int selectedQuestionNo) {
        this.context = context;
        this.cursor = cursor;
        this.questionNo = selectedQuestionNo;

        String [] questionOptions = {"A. ", "B. ", "C. ", "D. "  };
        String [] questionOptions1 = new String[4] ; //{ "this is option A", "this is option B", "this is option C", "this is option D"};
        //String [] questionOptions
        list = new ArrayList<SingleRow>();

        StringBuffer buffer = new StringBuffer();
        //cursor.moveToPosition(questionNo);

        int quesID = cursor.getInt(cursor.getColumnIndex(DBQuestions.getUID()));
        /*
        String question = cursor.getString(cursor.getColumnIndex(DBQuestions.getQuestion()));
        String option1 = cursor.getString(cursor.getColumnIndex(DBQuestions.getOption1()));
        String option2 = cursor.getString(cursor.getColumnIndex(DBQuestions.getOption2()));
        String option3 = cursor.getString(cursor.getColumnIndex(DBQuestions.getOption3()));
        String option4 = cursor.getString(cursor.getColumnIndex(DBQuestions.getOption4()));
        String answer = cursor.getString(cursor.getColumnIndex(DBQuestions.getAnswer()));
        */

        int count=0;
        //String question = cursor.getString(cursor.getColumnIndex(DBQuestions.getQuestion()));
        //String answer = cursor.getString(cursor.getColumnIndex(DBQuestions.getAnswer()));
        questionOptions1[count++] = cursor.getString(cursor.getColumnIndex(DBQuestions.getOption1()));
        questionOptions1[count++] = cursor.getString(cursor.getColumnIndex(DBQuestions.getOption2()));
        questionOptions1[count++] = cursor.getString(cursor.getColumnIndex(DBQuestions.getOption3()));
        questionOptions1[count++] = cursor.getString(cursor.getColumnIndex(DBQuestions.getOption4()));

        for (count = 0; count < 4; count++) {
            item = new SingleRow(questionOptions[count], questionOptions1[count], questionOptions1[count]);
            list.add(item);
        }
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