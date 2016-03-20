package com.pivot.dsa;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class chapters extends AppCompatActivity implements AdapterView.OnItemClickListener {

    DBHelper dbHelper;
    int MAX_CHAPTERS = 128;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        dbHelper = new DBHelper(this);
        //DBChapters dbChapters = new DBChapters(this);
        Cursor cursor = dbHelper.getAllChapters();
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

        listView.setOnItemClickListener(this);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView textView = (TextView) view;
        Intent intent = new Intent("com.pivot.dsa.questions");
        startActivity(intent);
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
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }
}
