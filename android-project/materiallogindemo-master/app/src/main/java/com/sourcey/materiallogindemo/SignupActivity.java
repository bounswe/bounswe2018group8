/**
 * This class is used for signing up. If you sign up, then you'll be redirected to Login to use your credentials in your sign-in.
 *
 * @author  Berkay Kozan github.com/leblebi1
 * @version 1.0
 * @since   2018 October
 */
package com.sourcey.materiallogindemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    static String response;
    static int statusCode;
    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_first_name) EditText _firstnameText;
    @BindView(R.id.input_last_name) EditText _lastnameText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    signup();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }
    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        super.onBackPressed();  // optional depending on your needs
    }
    public void signup() throws JSONException, ExecutionException, InterruptedException {
        Log.d(TAG, "Signup");

        if (!validate()) {
            Toast.makeText(getBaseContext(), "Registration failed", Toast.LENGTH_LONG).show();
            _signupButton.setEnabled(true);
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        final String name = _nameText.getText().toString();
        //String address = _addressText.getText().toString();
        final String email = _emailText.getText().toString();
       // String mobile = _mobileText.getText().toString();
        final String password = _passwordText.getText().toString();
        final String reEnterPassword = _reEnterPasswordText.getText().toString();
        final String firstName= _firstnameText.getText().toString();
        final String lastName= _lastnameText.getText().toString();
        //final String isClient= _isClient.getSelectedItem().toString();
        //boolean is_client=false;
        /*if(isClient.equals("Client")){
            is_client=true;
        }*/

        // TODO: Implement your own signup logic here.

        JSONObject jsonToPost = new JSONObject();
        jsonToPost.put("username", name);
        jsonToPost.put("password1", password);
        jsonToPost.put("password2", reEnterPassword);
        jsonToPost.put("email", email);
        jsonToPost.put("first_name", firstName);
        jsonToPost.put("last_name", lastName);
        //jsonToPost.put("is_client", is_client);


        HttpPostAsyncTask myTask= new HttpPostAsyncTask(jsonToPost,this, "");
        String theResponse = myTask.execute("http://52.59.230.90/register/").get();
        statusCode = Integer.parseInt(theResponse.substring(0, 3));
        response = theResponse.substring(3);
        progressDialog.dismiss();
        _signupButton.setEnabled(true);
        Log.d("Burasiii", "my email is: " + email + " my pw is: " + password + " response is:" + response);
        if (statusCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
            onSignupFailed();
        }
        else if(statusCode==HttpURLConnection.HTTP_CREATED){
            onSignupSuccess();

        }

 /*       new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);*/
    }


    public void onSignupSuccess() {
        setResult(RESULT_OK, null);
        Toast.makeText(getApplicationContext(), "New account created.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void onSignupFailed() {
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

        String name = _nameText.getText().toString();
       // String address = _addressText.getText().toString();
        String email = _emailText.getText().toString();
       // String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

/*        if (address.isEmpty()) {
            _addressText.setError("Enter Valid Address");
            valid = false;
        } else {
            _addressText.setError(null);
        }
*/

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

/*        if (mobile.isEmpty() || mobile.length()!=10) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText.setError(null);
        }
*/
        if (password.isEmpty() || password.length() < 8) {
            _passwordText.setError("minimum 8 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 8  || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }
    public String JSONArrayToString(String str,String field) throws JSONException, IOException {
        JsonElement jelement = new JsonParser().parse(str);
        JsonObject jobject = jelement.getAsJsonObject();
        JsonArray jarray = jobject.getAsJsonArray(field);
        return jarray.get(0).toString();
    }
}