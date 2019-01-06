/**
 * This class shows "my works". You see projects that you created here.
 *
 * @author  Berkay Kozan github.com/leblebi1
 * @version 1.0
 * @since   2018 October
 */

package com.sourcey.materiallogindemo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class Workspage_fragment extends Fragment {
    public static String response;
    ArrayList<Project> ProjectList = new ArrayList<>();
    ArrayList<Project> biddedProjectList= new ArrayList<>();
    public static String token;
    static int project_number;
    String myUsername;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View workspageView;
        workspageView = inflater.inflate(R.layout.fragment_works_page, container, false);
        token = ((HomepageActivity) getActivity()).getToken();
        try {
            getSelf();
            getMyProjects();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ListView projectsList = (ListView) workspageView.findViewById(R.id.projectList);
        projectsList.setAdapter(new ProjectAdapter(getActivity(), ProjectList));
        //ListView biddedProjectsList = (ListView) workspageView.findViewById(R.id.biddedProjectList);
        //biddedProjectsList.setAdapter(new ProjectAdapter(getActivity(), biddedProjectList));
        projectsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                project_number=ProjectList.get(position).getId();
                Bundle bundle = new Bundle();
                bundle.putString("pk", project_number+"");
                bundle.putString("token", token);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = (Fragment) new one_project_fragment();
                fragment.setArguments(bundle);
                fragmentTransaction.replace(android.R.id.content, fragment);
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();
            }
        });
        return workspageView;
    }

    public void getMyProjects() throws ExecutionException, InterruptedException, JSONException, ParseException {
        HttpGetAsyncTask myTask = new HttpGetAsyncTask(getActivity(), token);
        String theResponse = myTask.execute("http://52.59.230.90/projects/").get();
        int statusCode = Integer.parseInt(theResponse.substring(0, 3));
        response = theResponse.substring(3);
        if (statusCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
            onGetProjectsFailure();
        } else if (statusCode == HttpURLConnection.HTTP_OK) {
            onGetProjectsSuccess();
        }

    }

    public void onGetProjectsSuccess() throws JSONException, ParseException {
        ProjectList= new ArrayList<>();
        JSONArray jsonArray = new JSONArray(response);
        for (int i = 0 ; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            String deadline_str = obj.getString("deadline");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date deadline = sdf.parse(deadline_str);
            int id = obj.getInt("id");
            int client_id = obj.getInt("client_id");
            String client_username = obj.getString("client_username");
            String freelancer_id = obj.getString("freelancer_id");
            String freelancer_username = obj.getString("freelancer_username");
            String status = obj.getString("status");
            String title = obj.getString("title");
            String description = obj.getString("description");
            int max_price = obj.getInt("max_price");
            int min_price = obj.getInt("min_price");
            int bid_count = obj.getInt("min_price");
            int average_bid = obj.getInt("min_price");
            boolean isBiddedProject=false;
            ArrayList<Bid> bidList= new ArrayList<>();
            JSONArray jArray = obj.getJSONArray("bids");
            if (jArray != null) {
                for (int j=0;j<jArray.length();j++){
                    JSONObject thisBid= jArray.getJSONObject(j);
                    Bid bid= new Bid(thisBid.getInt("id"), thisBid.getInt("freelancer_id"), thisBid.getString("freelancer_username"), thisBid.getInt("amount"));
                    if(obj.getString("freelancer_username").equals(myUsername)){
                        bidList.add(bid);
                        isBiddedProject=true;
                    }
                }
            }
            Log.d("TAG", ProjectList.size()+"");
            Project project= new Project(bid_count, average_bid, id,title,description,deadline,max_price,min_price,status,client_id,freelancer_id,freelancer_username,client_username,bidList);
            Log.d("Still",myUsername);
            Log.d("Want", client_username);

            if(client_username.equals(myUsername)){
                ProjectList.add(project);
                Log.d("Want", ProjectList.size()+"");
            }
            else if(isBiddedProject){
                biddedProjectList.add(project);
            }
        }
    }

    public void onGetProjectsFailure() {
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
        myUsername= obj.getString("username");
    }
}