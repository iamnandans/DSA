package com.pivot.dsa;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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

public class MainOptionsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    DBHelper dbHelper;
    int MAX_SUBJECTS = 32;
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
        //getMenuInflater().inflate(R.menu.action_bar_ques, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

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
        //TextView textView = (TextView) view;
        Intent intent = new Intent("com.pivot.dsa.chapters");
        intent.putExtra(this.getResources().getStringArray(R.array.SubjectsTB)[0],position + 1);
        startActivity(intent);
        //Message.message(this, textView.getText().toString());
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
        cursorAdapter = new SimpleCursorAdapter(this, R.layout.single_row,cursor,fromFieldNames,viewIDs,0);

        listView.setAdapter(cursorAdapter);

        listView.setOnItemClickListener(this);
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