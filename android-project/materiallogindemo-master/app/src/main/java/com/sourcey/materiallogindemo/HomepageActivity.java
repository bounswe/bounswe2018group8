package com.sourcey.materiallogindemo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.SearchView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomepageActivity  extends AppCompatActivity{
    private static final String TAG = "HomepageActivity";
    private String token="";
    @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;
    private ArrayList<String> projects= new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        ButterKnife.bind(this);
        if(getIntent().getStringExtra("token")!=null) {
            token = getIntent().getStringExtra("token");
        }
        if(token.equals("")) {
            bottomNavigationView.setVisibility(View.GONE);
        }
        else {
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    item.setChecked(true);
                    switch (item.getItemId()) {

                        case R.id.menu_homepage:
                            goToHomePage();
                            break;
                        case R.id.menu_profile:
                            goToProfilePage();
                            break;
                        case R.id.menu_create:
                            Intent intent = new Intent(getApplicationContext(), ProjectCreationActivity.class);
                            intent.putExtra("token", token);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                            break;
                        case R.id.menu_works:
                            break;
                    }
                    return false;
                }
            });
        }
        goToHomePage();
    }

    private void goToProfilePage() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = (Fragment) new Profile_fragment();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.commit();
    }

    private void goToWorksPage() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = (Fragment) new Workspage_fragment();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.commit();
    }

    private void goToHomePage() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = (Fragment) new Homepage_fragment();
        fragmentTransaction.replace(android.R.id.content, fragment);
        fragmentTransaction.commit();
    }

    public JSONArray getProjects() throws ExecutionException, InterruptedException, IOException, JSONException {
        HttpGetAsyncTask myTask = new HttpGetAsyncTask(this,token);
        String theResponse = myTask.execute("http://52.59.230.90/projects/").get();
        int statusCode = Integer.parseInt(theResponse.substring(0, 3));
        String response = theResponse.substring(3);
        Log.d("getProjects", " response is:" + response);
        JSONArray jsonArray = new JSONArray(response);
        JSONObject elemeent= jsonArray.getJSONObject(0);
        Log.d("deniyorum",elemeent.getString("client_username"));
        return jsonArray;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case(R.id.login_option):
                Intent intent1 = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent1);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case(R.id.logout_option):
                try {
                    logout();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if(token.equals("")){
            menu.findItem(R.id.logout_option).setVisible(false);
            menu.findItem(R.id.login_option).setVisible(true);
        }else{
            menu.findItem(R.id.logout_option).setVisible(true);
            menu.findItem(R.id.login_option).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    public void logout() throws ExecutionException, InterruptedException, IOException, JSONException {
        JSONObject jsonToPost = new JSONObject();
        HttpPostAsyncTask myTask = new HttpPostAsyncTask(jsonToPost, this,token);
        String theResponse = myTask.execute("http://52.59.230.90/logout/").get();
        int statusCode = Integer.parseInt(theResponse.substring(0, 3));
        String response = theResponse.substring(3);
        Log.d("Burasiii", " response is:" + response);

        String str = JSONObjectToString(response, "detail"); //Getting error field: Non-field-errors etc...
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();

    }
    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }
    public String JSONObjectToString(String str,String field) throws JSONException, IOException {
        JSONObject json = new JSONObject(str);
        return json.getString(field);
    }
    public String JSONArrayToString(String str,String field) throws JSONException, IOException {
        JsonElement jelement = new JsonParser().parse(str);
        JsonObject jobject = jelement.getAsJsonObject();
        JsonArray jarray = jobject.getAsJsonArray(field);
        return jarray.get(0).toString();
    }
    public String getToken(){
        return token;
    }

}
