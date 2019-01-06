/**
 * This fragment is here for profile page. It is used via Homepage Activity.
 *
 * @author  Berkay Kozan github.com/leblebi1
 * @version 1.0
 * @since   2018 October
 */



package com.sourcey.materiallogindemo;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class Profile_fragment extends Fragment{
    public static String response;
    public static String token;
    public static String depositAmount;
    TextView name;
    TextView surname;
    TextView is_client;
    TextView bio;
    TextView balance;
    Button deposit;
    TextView email;
    TextView username;
    TextView edit_button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View profilePageView;
        profilePageView = inflater.inflate(R.layout.fragment_profile_page, container, false);
        token=((HomepageActivity)getActivity()).getToken();
        name=profilePageView.findViewById(R.id.name);
        surname=profilePageView.findViewById(R.id.surname);
        bio=profilePageView.findViewById(R.id.bio);
        balance=profilePageView.findViewById(R.id.balance);
        email=profilePageView.findViewById(R.id.email);
        username=profilePageView.findViewById(R.id.username);
        deposit=profilePageView.findViewById(R.id.deposit);
        edit_button=profilePageView.findViewById(R.id.edit_profile);
        deposit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(((HomepageActivity)getActivity()));
                builder.setTitle("How much do you want to deposit to your account?");

                // Set up the input
                final EditText input = new EditText(((HomepageActivity)getActivity()));
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        depositAmount = input.getText().toString();
                        try {
                            depositToAccount(depositAmount);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    edit();
            }
        });
        try {
            getSelf();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return profilePageView;
    }

    public void edit() {
        FragmentManager fragmentManager = getFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString("token", token);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = (Fragment) new edit_fragment();
        fragment.setArguments(bundle);
        fragmentTransaction.replace(android.R.id.content, fragment);

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void depositToAccount(String depositAmount) throws JSONException, ExecutionException, InterruptedException {
        ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Bidding...");
        progressDialog.show();
        JSONObject jsonToPost = new JSONObject();
        jsonToPost.put("amount", depositAmount);
        HttpPostAsyncTask myTask = new HttpPostAsyncTask(jsonToPost, getActivity().getApplicationContext(), token);
        String theResponse = myTask.execute("http://52.59.230.90/users/self/deposit/").get();
        int statusCode = Integer.parseInt(theResponse.substring(0, 3));
        response = theResponse.substring(3);
        progressDialog.dismiss();
        if (statusCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
            onGetSelfFailure();
        }
        else if(statusCode==HttpURLConnection.HTTP_OK){
            Toast.makeText(getActivity().getApplicationContext(), "Your deposit is added to your account!", Toast.LENGTH_LONG).show();
        }
    }

    public void getSelf() throws ExecutionException, InterruptedException, JSONException, ParseException {
        HttpGetAsyncTask myTask = new HttpGetAsyncTask(getActivity(),token);
        String theResponse = myTask.execute("http://52.59.230.90/users/self").get();
        int statusCode = Integer.parseInt(theResponse.substring(0, 3));
        response = theResponse.substring(3);
        if (statusCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
            onGetSelfFailure();
        }
        else if(statusCode==HttpURLConnection.HTTP_OK){
            onGetSelfSuccess();
        }

    }

    public void onGetSelfFailure() {
        String str="";
        String field=response.substring(2,response.indexOf("\":"));
        try {
            str = ((HomepageActivity) getActivity()).JSONObjectToString(response, field); //Getting error field: Non-field-errors etc...
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getActivity().getApplicationContext(), field+"\n"+str, Toast.LENGTH_LONG).show();
    }
    public void onGetSelfSuccess() throws JSONException, ParseException {
        JSONObject obj = new JSONObject(response);
        name.setText("name: "+obj.getString("first_name"));
        surname.setText("surname: "+obj.getString("last_name"));
        bio.setText("bio: "+obj.getString("bio"));
        balance.setText("balance: "+obj.getString("balance"));
        email.setText("email: "+obj.getString("email"));
        username.setText("username: "+obj.getString("username"));

    }

}
