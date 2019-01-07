/**
 * This class is used for project creation.
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

public class ProjectCreationActivity extends AppCompatActivity {
    private static final String TAG = "ProjectCreationActivity";
    static String response;
    static int statusCode;
    static String token;
    @BindView(R.id.btn_cancel) Button _cancel;
    @BindView(R.id.minPrice) EditText _minPrice;
    @BindView(R.id.maxPrice) EditText _maxPrice;
    @BindView(R.id.project_description) EditText _description;
    @BindView(R.id.project_title) EditText _title;
    @BindView(R.id.deadline) EditText _deadline;
    @BindView(R.id.btn_project) Button _projectCreator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_creation);
        ButterKnife.bind(this);
        if(getIntent().getStringExtra("token")!=null) {
            token = getIntent().getStringExtra("token");
        }
        _projectCreator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    projectCreate();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        _cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HomepageActivity.class);
                intent.putExtra("token", token);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }
    public void projectCreate() throws JSONException, ExecutionException, InterruptedException {
        Log.d(TAG, "ProjectCreate");

        if (!validate()) {
            Toast.makeText(getBaseContext(), "Project creation failed", Toast.LENGTH_LONG).show();
            _projectCreator.setEnabled(true);
            return;
        }
        _projectCreator.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(ProjectCreationActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Project...");
        progressDialog.show();

        final String title = _title.getText().toString();
        final String description = _description.getText().toString();
        final String maxPrice = _maxPrice.getText().toString();
        final String minPrice = _minPrice.getText().toString();
        final String deadline = _deadline.getText().toString();


        // TODO: Implement your own signup logic here.

        JSONObject jsonToPost = new JSONObject();
        jsonToPost.put("title", title);
        jsonToPost.put("description", description);
        jsonToPost.put("max_price", maxPrice);
        jsonToPost.put("min_price", minPrice);
        jsonToPost.put("deadline", deadline);

        HttpPostAsyncTask myTask= new HttpPostAsyncTask(jsonToPost,this,token);
        String theResponse = myTask.execute("http://52.59.230.90/projects/").get(); //TODO:change the link!
        statusCode = Integer.parseInt(theResponse.substring(0, 3));
        response = theResponse.substring(3);
        progressDialog.dismiss();
        _projectCreator.setEnabled(true);

        if (statusCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
            onProjectCreationFailure();
        }
        else if(statusCode==HttpURLConnection.HTTP_CREATED){
            onProjectCreationSuccess();

        }

    }

    public void onProjectCreationSuccess() {
        setResult(RESULT_OK, null);
        Toast.makeText(getApplicationContext(), "New project created.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
    public void onProjectCreationFailure() {
        String str="";
        String field=response.substring(2,response.indexOf("\":"));

        try {
            str = JSONObjectToString(response, field); //Getting error field: Non-field-errors etc...
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), field+"\n"+str, Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;
        final String title = _title.getText().toString();
        final String description = _description.getText().toString();
        final String maxPrice = _maxPrice.getText().toString();
        final String minPrice = _minPrice.getText().toString();
        final String deadline = _deadline.getText().toString();

        if (title.isEmpty()) { // || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() can be used for Email checks.
            _title.setError("enter a title");
            valid = false;
        } else {
            _title.setError(null);
        }
        if (description.isEmpty()) { // || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() can be used for Email checks.
            _description.setError("enter a description");
            valid = false;
        } else {
            _description.setError(null);
        }

        if (maxPrice.isEmpty()) {
            _maxPrice.setError("maximum price cannot be empty.");
            valid = false;
        }

        else if (minPrice.isEmpty()) {
            _minPrice.setError("minimum price cannot be empty.");
            valid = false;
        }
        else if(Integer.parseInt(maxPrice)<Integer.parseInt(minPrice)){
            valid = false;
            _maxPrice.setError("Maximum price cannot be less than minimum price.");
            _minPrice.setError("Minimum price cannot be greater than maximum price.");
        }
        else {
            _maxPrice.setError(null);
            _minPrice.setError(null);

        }

        if (deadline.isEmpty()) {
            _deadline.setError("enter a deadline.");
            valid = false;
        } else {
            _deadline.setError(null);
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
