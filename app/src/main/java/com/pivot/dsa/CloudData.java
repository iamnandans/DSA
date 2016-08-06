package com.pivot.dsa;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * Created by shanthan on 8/3/2016.
 */
public class CloudData  {
    String getData(int dataType, String last_update_date, Context ctx) {
        HttpClient httpclient = new DefaultHttpClient();
        String url;

        //String url = "http://www.mysoredentalcare.com/test/data.php?mod_date=" + last_update_date;
        if ( dataType == R.integer.subj_id )
            url = ctx.getString(R.string.subjects_url) + last_update_date;
        else if ( dataType == R.integer.chap_id )
            url = ctx.getString(R.string.chapters_url) + last_update_date;
        else if (dataType == R.integer.ques_id )
            url = ctx.getString(R.string.questions_url) + last_update_date;
        else if (dataType == R.integer.ans_id )
            url = ctx.getString(R.string.answers_url) + last_update_date;
        else {
            return null;
        }

        // Prepare a request object
        HttpGet httpget = new HttpGet(url);

        // Execute the request
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            // Examine the response status
            //Message.message(this, "got data");
            Log.d("hhtpresponse", "got data kanri");
            //Message.message(this, response.getStatusLine().toString());
            Log.d("hhtpresponse", response.getStatusLine().toString());
            Log.d("hhtpresponse",response.toString());
            HttpEntity entity = response.getEntity();

            if (entity != null) {

                // A Simple JSON Response Read
                InputStream instream = entity.getContent();
                String result = convertStreamToString(instream);
                // now you have the string representation of the HTML request
                instream.close();
                Log.d("hhtpresponse", result);
                return result;
                /*
                //JSONObject jsonObj = new JSONObject(result);
                JSONArray subjects = new JSONArray(result);
                //subjects = jsonObj.getJSONArray();

                for (int i = 0; i < subjects.length(); i++) {
                    JSONObject c = subjects.getJSONObject(i);
                    String id = c.getString("id");
                    Log.d("subjectid","id:" + id);
                }
                */
            } else {
                Log.d("hhtpresponse","entity is null ");
            }

        } catch (Exception e) {
            //Message.message(this, "got exception: " + e );
            Log.d("nan", "got exception: " + e  );
        }

        return null;
    }

    private String convertStreamToString(InputStream is) {
    /*
     * To convert the InputStream to String we use the BufferedReader.readLine()
     * method. We iterate until the BufferedReader return null which means
     * there's no more data to read. Each line will appended to a StringBuilder
     * and returned as String.
     */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
