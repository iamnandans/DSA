package com.pivot.dsa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import static java.lang.Long.getLong;

public class MainOptionsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    DBHelper dbHelper;
    int MAX_SUBJECTS = 32;
    private ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarbHandler = new Handler();
    private long fileSize = 0;
    private String last_update_date = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_options);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getAllSubjects();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar_subject, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sync) {
            startSyncProcess();
            Message.message(this, "Sync buttoDMEPS7235Cn clicked.....action yet to be implemented");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

/*        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent("com.pivot.dsa.chapters");
        intent.putExtra(this.getResources().getStringArray(R.array.SubjectsTB)[0],position + 1);
        intent.putExtra(this.getResources().getStringArray(R.array.SubjectsTB)[1], ((TextView) view.findViewById(R.id.name)).getText().toString());

        startActivity(intent);
    }

    private void getAllSubjects () {
        dbHelper = new DBHelper(this);
        DBSubjects subjects = new DBSubjects(this);
        Cursor cursor = dbHelper.getAllSubjects();

        /*int count=0;
        int numOfSubjects=0;
        //String [] subjectName;
        String [] subjectNameFromDB = new String[MAX_SUBJECTS];

        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            int subID = cursor.getInt(cursor.getColumnIndex(subjects.getUID()));
            String subName = cursor.getString(cursor.getColumnIndex(subjects.getSUB_NAME()));
            //int subLevel = cursor.getInt(cursor.getColumnIndex(subjects.getLEVEL()));
            buffer.append(subID + "," + subName + ",--");
            subjectNameFromDB[count++] = subName;
            numOfSubjects++;
        }

        String [] subjectName = new String[numOfSubjects];

        System.arraycopy(subjectNameFromDB, 0, subjectName, 0, numOfSubjects);

        //Message.message(this, subjectName[0]);
        //Message.message(this,buffer.toString());

        ListView listView;
        listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, subjectName);
        listView.setAdapter(adapter);
        */

        String [] fromFieldNames = new String [] { subjects.getSUB_NAME()};
        int [] viewIDs = new int [] {R.id.name};
        ListView listView;
        listView = (ListView) findViewById(R.id.subListView);

        SimpleCursorAdapter cursorAdapter;
        cursorAdapter = new SimpleCursorAdapter(this, R.layout.single_row, cursor,fromFieldNames,viewIDs,0);
        listView.setAdapter(cursorAdapter);

        listView.setOnItemClickListener(this);
    }

    private void startSyncProcess () {
        /* http://www.tutorialspoint.com/android/android_progress_circle.htm

         */
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);
        progressBar.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        /* move all constant messages to strings.xml file */
        progressBar.setTitle("Syncing Data with Server....");
        progressBar.setMessage("File downloading ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
        progressBarStatus = 0;

        fileSize = 0;
        new Thread(new Runnable() {
            int count=0;
            public void run() {
                try {
                    synchronized (this) {
                        setStatus("Syncing Data....");
                        wait(1000);
                        last_update_date = getLastUpdateDateFromSharedPreference();
                        Log.d("httprequest", "last_update_date=" + last_update_date);
                        /* move all constant messages to strings.xml file */

                        //Log.d("nandan", "testing log.d messaegs");
                        syncData();
                        //wait(10000);
                        //wait(10000);
                        writeLastUpdateDateToSharedPreference();
                        progressBar.dismiss();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void syncData() {
        dbHelper = new DBHelper(this);
        CloudData cloudData = new CloudData();
        int typeOfData;
        int retVal = 0;


        /* sync subjects */
        setStatus("Syncing subjects....");
        typeOfData = R.integer.subj_id;
        String subjectsData = cloudData.getData(typeOfData, last_update_date, this);

        if ( subjectsData == null ) {
            Log.d("error:", getString(R.string.get_subj_failed));
        } else if ( subjectsData.equals("0") ) {
            Log.d("error:", getString(R.string.subj_upto_date));
        } else {
            retVal = dbHelper.updateSubjectsData(subjectsData);
            Log.d("Result:", retVal + " " + getString(R.string.subj_updated));
        }

        /* sync chapters */
        setStatus("Syncing chapters....");
        typeOfData = R.integer.chap_id;
        String chaptersData = cloudData.getData(typeOfData, last_update_date, this);

        if ( chaptersData == null ) {
            Log.d("Error:", getString(R.string.get_chap_failed));
        } else if ( chaptersData.equals("0") ) {
            Log.d("error:", getString(R.string.chap_upto_date));
        } else {
            retVal = dbHelper.updateChaptersData(chaptersData);
            Log.d("Result:", retVal + " " + getString(R.string.chap_updated));
        }

        /* sync questions */
        setStatus("Syncing questions....");
        typeOfData = R.integer.ques_id;
        String questionsData = cloudData.getData(typeOfData, last_update_date, this);

        if ( questionsData == null ) {
            Log.d("error:", getString(R.string.get_ques_failed));
        } else if ( questionsData.equals("0") ) {
            Log.d("error:", getString(R.string.ques_upto_date));
        } else {
            retVal = dbHelper.updateQuestionsData(questionsData);
            Log.d("error:", retVal + " " + getString(R.string.ques_updated));
        }

        /* sync answers */
        setStatus("Syncing answers....");
        typeOfData = R.integer.ans_id;
        String answersData = cloudData.getData(typeOfData, last_update_date, this);

        if ( questionsData == null ) {
            Log.d("error:", getString(R.string.get_ans_failed));
        } else if ( answersData.equals("0") ) {
            Log.d("error:", getString(R.string.ans_upto_date));
        } else {
            retVal = dbHelper.updateAnswersData(answersData);
            Log.d("error:", retVal + " " + getString(R.string.ans_updated));
        }
    }

    public String getLastUpdateDateFromSharedPreference() {
        String last_update_date;
        String defaultDate = getString(R.string.default_last_update_date);

        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.app_resource_file), Context.MODE_PRIVATE);
        last_update_date = sharedPref.getString(getString(R.string.last_update_date), defaultDate);

        //return last_update_date;
        return defaultDate;
    }

    public void writeLastUpdateDateToSharedPreference() {
        Date now = new Date();
        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.app_resource_file), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.last_update_date), Long.toString(now.getTime()/1000));
        editor.commit();
        //Log.d("dateformat", now.toString() + "--" + (now.getTime()/1000));
    }

    public void setStatus(final String statusMessage ) {
        /* runOnUiThread method is required to change the progressbar mesasage */
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                    progressBar.setMessage(statusMessage);
            }
        });
    }

    class SubAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}