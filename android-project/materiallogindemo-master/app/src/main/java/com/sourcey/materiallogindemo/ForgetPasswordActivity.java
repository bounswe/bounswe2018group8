package com.sourcey.materiallogindemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgetPasswordActivity extends AppCompatActivity {
    private static final String TAG = "ForgetPasswordActivity";
    static String response="";
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.btn_sendpw) Button _sendpw;
    @BindView(R.id.link_login) TextView _loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);

        _sendpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                try {
                    sendForm();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
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
    /*@Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }*/

    public void sendForm() throws ExecutionException, InterruptedException, JSONException, IOException {
        Log.d(TAG, "send password");
        String email = _emailText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Write a valid email!");
            _sendpw.setEnabled(true);
            return;
        }
        _sendpw.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(ForgetPasswordActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Sending password...");
        progressDialog.show();

        // TODO: Implement your own signup logic here.

        JSONObject jsonToPost = new JSONObject();
        jsonToPost.put("email", email);
        HttpPostAsyncTask myTask= new HttpPostAsyncTask(jsonToPost,this,"");
        String theResponse = myTask.execute("http://52.59.230.90/password/reset/").get();
        int statusCode = Integer.parseInt(theResponse.substring(0, 3));
        response = theResponse.substring(3);
        progressDialog.dismiss();
        _sendpw.setEnabled(true);

        if (statusCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
            onForgetPasswordFailure();
        }
        else if(statusCode==HttpURLConnection.HTTP_OK){
            onForgetPasswordSuccess();

        }
    }
    public void onForgetPasswordFailure() {
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

    public void onForgetPasswordSuccess() throws IOException, JSONException {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        String str = JSONObjectToString(response, "detail"); //Getting error field: Non-field-errors etc...
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
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
