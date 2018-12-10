package com.sourcey.materiallogindemo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONException;

import java.net.HttpURLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Workspage_fragment extends Fragment {
    public static String response;
    ArrayList<Project> ProjectList = new ArrayList<>();
    public static String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View workspageView;
        workspageView = inflater.inflate(R.layout.fragment_works_page, container, false);
        token = ((HomepageActivity) getActivity()).getToken();
        try {
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

    private void onGetProjectsSuccess() {
    }

    private void onGetProjectsFailure() {
    }
}