package com.sourcey.materiallogindemo;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import java.io.*;
import java.net.*;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.Queue;

import java.net.HttpURLConnection;
import java.time.Clock;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.net.Proxy.Type.HTTP;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static final String urlAdress = "https://berkay.requestcatcher.com/";
    private static String token="";
    public static int statusCode;
    public static boolean isDialogOn=false;
    public static String response;
    ProgressDialog progressDialog;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;
    @BindView(R.id.link_homepage) TextView _homepageLink;
    @BindView(R.id.link_forgetpw) TextView _forgetpwLink;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    login();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        _homepageLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Homepage activity
                Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        _forgetpwLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Forget password activity
                Intent intent = new Intent(getApplicationContext(), ForgetPasswordActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

    }

    public void login() throws IOException, JSONException, InterruptedException, ExecutionException {
        Log.d(TAG, "Login");

        if (!validate()) {
            Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
            _loginButton.setEnabled(true);
            return;
        }

        _loginButton.setEnabled(false);

        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();
        JSONObject jsonToPost = new JSONObject();
        jsonToPost.put("username", email);
        jsonToPost.put("password", password);
        HttpPostAsyncTask myTask = new HttpPostAsyncTask(jsonToPost, this, "");
        String theResponse = myTask.execute("http://52.59.230.90/login/").get();
        statusCode = Integer.parseInt(theResponse.substring(0, 3));
        response = theResponse.substring(3);
        progressDialog.dismiss();
        _loginButton.setEnabled(true);
        Log.d("Burasiii", "my email is: " + email + " my pw is: " + password + " response is:" + response);
        if (statusCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
            onLoginFailed();
        }
        else if(statusCode==HttpURLConnection.HTTP_OK){
            onLoginSuccess();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

   /* @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }*/

    public void onLoginSuccess() throws IOException, JSONException {
        Toast.makeText(getApplicationContext(), "Login accepted.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
        String str = JSONObjectToString(response, "token"); //Getting error field: Non-field-errors etc...
        intent.putExtra("token", str);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void onLoginFailed() {
        String str="";
        String field=response.substring(2,response.indexOf("\":"));
        try {
            str = JSONArrayToString(response, field); //Getting error field: Non-field-errors etc...
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), field+"\n"+str, Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty()) { // || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() can be used for Email checks.
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 8) {
            _passwordText.setError("minimum 8 characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
    public String JSONArrayToString(String str,String field) throws JSONException, IOException {
        JsonElement jelement = new JsonParser().parse(str);
        JsonObject jobject = jelement.getAsJsonObject();
        JsonArray jarray = jobject.getAsJsonArray(field);
        return jarray.get(0).toString();
    }

    public String JSONObjectToString(String str,String field) throws JSONException, IOException {
        JSONObject json = new JSONObject(str);
        return json.getString(field);
    }
}
