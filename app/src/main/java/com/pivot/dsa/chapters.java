package com.pivot.dsa;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class chapters extends AppCompatActivity implements AdapterView.OnItemClickListener {

    DBHelper dbHelper;
    int MAX_CHAPTERS = 128;
    DBChapters chapters;
    Cursor cursor;
    private int subjectID;
    private static int subjectIDStatic = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        chapters = new DBChapters(this);
        if ( subjectIDStatic == 0 ) {
            subjectID = getIntent().getExtras().getInt(this.getResources().getStringArray(R.array.SubjectsTB)[0]);
        }
        else {
            subjectID = subjectIDStatic;
            subjectIDStatic = 0;
        }

        dbHelper = new DBHelper(this);
        cursor = dbHelper.getAllChaptersForSubject(subjectID);

        getAllChapters(cursor, subjectID);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onBackPressed() {
        subjectIDStatic=0;
        super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                subjectID = data.getIntExtra(this.getResources().getStringArray(R.array.SubjectsTB)[0],1);
           }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //TextView textView = (TextView) view;
        cursor.moveToPosition(position);
        int chapID = cursor.getInt(cursor.getColumnIndex(DBChapters.getUID()));
        subjectIDStatic = subjectID;
        Intent intent = new Intent("com.pivot.dsa.questions");
        intent.putExtra(this.getResources().getStringArray(R.array.SubjectsTB)[0],subjectID);
        intent.putExtra(this.getResources().getStringArray(R.array.ChaptersTB)[0],chapID);
        startActivityForResult(intent, RESULT_OK);
        //Message.message(this,textView.getText().toString());
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
            subjectIDStatic = 0;
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    private void getAllChapters(Cursor cursor, int subjectID) {
        /*
        int count=0;
        int numOfChapters=0;
        String [] chapterNameFromDB = new String[MAX_CHAPTERS];

        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            int chapID = cursor.getInt(cursor.getColumnIndex(DBChapters.getUID()));
            String chapName = cursor.getString(cursor.getColumnIndex(DBChapters.getCHAP_NAME()));
            //buffer.append(subID + "," + subName + ",--");
            chapterNameFromDB[count++] = chapName;
            numOfChapters++;
        }

        String [] chapterNameArray = new String[numOfChapters];

        System.arraycopy(chapterNameFromDB, 0, chapterNameArray, 0, numOfChapters);

        ListView listView;
        listView = (ListView) findViewById (R.id.listView2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, chapterNameArray);
        listView.setAdapter(adapter);
        */

        String [] fromFieldNames = new String [] { chapters.getCHAP_NAME()};
        int [] viewIDs = new int [] {R.id.name};
        ListView listView;
        listView = (ListView) findViewById(R.id.chapListView);

        SimpleCursorAdapter cursorAdapter;
        cursorAdapter = new SimpleCursorAdapter(this, R.layout.single_row,cursor,fromFieldNames,viewIDs,0);

        listView.setAdapter(cursorAdapter);

        listView.setOnItemClickListener(this);
    }
}
