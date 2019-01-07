/**
 * This class sends post requests to the selected ip address.
 *
 * @author  Berkay Kozan github.com/leblebi1
 * @version 1.0
 * @since   2018 October
 */

package com.sourcey.materiallogindemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpPostAsyncTask extends AsyncTask<String, Void, String> {
    // This is the JSON body of the post
    JSONObject postData;
    String response;
    Context mContext;
    int statusCode;
    String token;
    String method;
    // This is a constructor that allows you to pass in the JSON body
    public HttpPostAsyncTask(JSONObject postData, Context mContext, String token) {
        if (postData != null) {
            this.postData = postData;
        }
        this.mContext= mContext;
        this.token= token;
    }
    public HttpPostAsyncTask(JSONObject postData, Context mContext, String token,String method) {
        if (postData != null) {
            this.postData = postData;
        }
        this.mContext= mContext;
        this.token= token;
        this.method=method;
    }
    // This is a function that we are overriding from AsyncTask. It takes Strings as parameters because that is what we defined for the parameters of our async task
    @Override
    protected String doInBackground(String... params) {
        try {
            // This is getting the url from the string we passed in
            URL url = new URL(params[0]);

            // Create the urlConnection
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();


            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod("POST");
            if(method!="" && method!=(null)){
                urlConnection.setRequestMethod("PATCH");
            }
            Log.d("WhatDoWeSend", token);
            if(token!=null && token.length()>0){
                urlConnection.setRequestProperty("Authorization", "JWT "+token);
                urlConnection.setDoOutput(false);
            }

            if (this.postData != null) {
                OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                writer.write(postData.toString());
                writer.flush();
            }

            statusCode = urlConnection.getResponseCode();
            Log.d("STATUSCODE",""+statusCode);
            if (statusCode< HttpURLConnection.HTTP_BAD_REQUEST) {

                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

                response = convertInputStreamToString(inputStream);
                Log.d("MERHABA",response);
                //JSONObject resp= convertStringToJSON(response1);

                // From here you can convert the string to JSON with whatever JSON parser you like to use
                // After converting the string to JSON, I call my custom callback. You can follow this process too, or you can implement the onPostExecute(Result) method
            } else {
                // Status code is not 200
                // Do something to handle the error
                InputStream inputStream = new BufferedInputStream(urlConnection.getErrorStream());
                response = convertInputStreamToString(inputStream);
                Log.d("HATALIYIM",response);

            }

        } catch (Exception e) {
            Log.d("hello", e.getLocalizedMessage());
        }
        return statusCode+response;
    }

/*    @Override
    protected void onPostExecute(String result) {
        result=result.substring(3);
        String str= null;
        if(statusCode>=HttpURLConnection.HTTP_BAD_REQUEST) {
            try {
                str = JSONArrayToString(result, "non_field_errors");
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(mContext.getApplicationContext(), str, Toast.LENGTH_LONG).show();
        }
        else if(statusCode==HttpURLConnection.HTTP_OK){
            Toast.makeText(mContext.getApplicationContext(), "Login accepted.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(mContext, HomepageActivity.class);
            mContext.startActivity(intent);
            ((Activity) mContext).finish();
        }
        else if(statusCode==HttpURLConnection.HTTP_CREATED){
            Toast.makeText(mContext.getApplicationContext(), "Account created.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(intent);
            ((Activity) mContext).finish();

        }

        // might want to change "executed" for the returned string passed
        // into onPostExecute() but that is upto you
    }*/

    public String convertInputStreamToString(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        String str=result.toString();
        return str;
    }
    public String JSONArrayToString(String str,String field) throws JSONException, IOException {
        JsonElement jelement = new JsonParser().parse(str);
        JsonObject  jobject = jelement.getAsJsonObject();
        JsonArray jarray = jobject.getAsJsonArray(field);
        return jarray.get(0).toString();
    }
}