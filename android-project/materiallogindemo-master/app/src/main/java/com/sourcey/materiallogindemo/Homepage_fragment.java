package com.sourcey.materiallogindemo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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

import butterknife.BindView;



public class Homepage_fragment extends Fragment {
    public static String response;
    ArrayList<Project> ProjectList = new ArrayList<>();
    public static String token;
    public static int project_number;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View homepageView;
        homepageView = inflater.inflate(R.layout.fragment_home_page, container, false);
        token=((HomepageActivity)getActivity()).getToken();
        try {
            getProjects();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final ListView projectsList = (ListView)homepageView.findViewById(R.id.projectList);
        projectsList.setAdapter(new ProjectAdapter(getActivity(),ProjectList));
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
        return homepageView;
    }
        public void getProjects() throws ExecutionException, InterruptedException, JSONException, ParseException {
            HttpGetAsyncTask myTask = new HttpGetAsyncTask(getActivity(),token);
            String theResponse = myTask.execute("http://52.59.230.90/projects/").get();
            int statusCode = Integer.parseInt(theResponse.substring(0, 3));
            response = theResponse.substring(3);
            if (statusCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
                onGetProjectsFailure();
            }
            else if(statusCode==HttpURLConnection.HTTP_OK){
                onGetProjectsSuccess();
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
            Log.d("TAG", ProjectList.size()+"");
            Project project= new Project(id,title,description,deadline,max_price,min_price,status,client_id,freelancer_id,freelancer_username,client_username);
            ProjectList.add(project);

        }
    }
    public int getPK(){
        return project_number;
    }
}
