package com.sourcey.materiallogindemo;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class edit_fragment extends Fragment {
    EditText email;
    EditText name;
    EditText surname;
    EditText bio;
    EditText username;
    String token;
    String response;
    Button change_button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle args = getArguments();
        token = args.getString("token",""); //Record token of person
        View editPageView;
        editPageView = inflater.inflate(R.layout.fragment_edit, container, false);
        email=editPageView.findViewById(R.id.email);
        name=editPageView.findViewById(R.id.name);
        bio=editPageView.findViewById(R.id.bio);
        surname=editPageView.findViewById(R.id.surname);
        username=editPageView.findViewById(R.id.username);
        change_button=editPageView.findViewById(R.id.change_button);
        token=((HomepageActivity)getActivity()).getToken();
        change_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    change();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        return editPageView;
    }

    public void change() throws JSONException, ExecutionException, InterruptedException {
        final String emailText = email.getText().toString();
        final String usernameText = username.getText().toString();
        final String bioText = bio.getText().toString();
        final String nameText = name.getText().toString();
        final String surnameText = surname.getText().toString();

        JSONObject jsonToPost = new JSONObject();
        jsonToPost.put("username", usernameText);
        jsonToPost.put("email", emailText);
        jsonToPost.put("bio", bioText);
        jsonToPost.put("first_name", nameText);
        jsonToPost.put("last_name", surnameText);

        HttpPostAsyncTask myTask = new HttpPostAsyncTask(jsonToPost, getActivity().getApplicationContext(), token,"PATCH");
        String theResponse = myTask.execute("http://52.59.230.90/users/self/").get();
        int statusCode = Integer.parseInt(theResponse.substring(0, 3));
        response = theResponse.substring(3);

        if (statusCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
            onChangeFailed();
        }
        else if(statusCode==HttpURLConnection.HTTP_OK){
            Toast.makeText(getActivity().getApplicationContext(), "You succesfully changed your profile.", Toast.LENGTH_LONG).show();
        }
    }

    public void onChangeFailed() {
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
}

