/**
 * This class is here for one project. When you select a project to see details, this class is used.
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
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.BottomNavigationView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;



public class one_project_fragment extends Fragment {
    public static String response;
    Project project;
    public static String token;
    public static String id;
    private static String bid;
    private static String myUsername;
    private static String ownerStr;
    private static String the_status;
    ArrayList<String> bidList= new ArrayList<>();

    TextView title;
    TextView description;
    TextView status;
    TextView price_range;
    TextView deadline;
    TextView owner;
    TextView bidCount;
    TextView averageBid;
    Button bidButton;
    Button showBids;
    Button acceptBid;
    EditText bidId;
    TextView inProgress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View oneProjectView;
        oneProjectView = inflater.inflate(R.layout.fragment_one_project_page, container, false);
        Bundle args = getArguments();
        id = args.getString("pk",""); //Record the id of project
        token = args.getString("token",""); //Record token of person
        myUsername= args.getString("username", "");
        title=oneProjectView.findViewById(R.id.title);
        description=oneProjectView.findViewById(R.id.description);
        status=oneProjectView.findViewById(R.id.status);
        deadline=oneProjectView.findViewById(R.id.deadline);
        price_range=oneProjectView.findViewById(R.id.price_range);
        owner=oneProjectView.findViewById(R.id.owner);
        bidCount=oneProjectView.findViewById(R.id.bidCount);
        averageBid=oneProjectView.findViewById(R.id.averageBid);
        bidButton=oneProjectView.findViewById(R.id.bidButton);
        showBids= oneProjectView.findViewById(R.id.showBids);
        bidId= oneProjectView.findViewById(R.id.bid_id);
        acceptBid= oneProjectView.findViewById(R.id.acceptBid);
        inProgress=oneProjectView.findViewById(R.id.in_progress_flag);
        bidId.setVisibility(View.GONE);
        acceptBid.setVisibility(View.GONE);
        inProgress.setVisibility(View.GONE);
        final ListView lv = (ListView)oneProjectView.findViewById(R.id.bidList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                (HomepageActivity)getActivity(),
                android.R.layout.simple_list_item_1,
                bidList);

        lv.setAdapter(arrayAdapter);
        lv.setVisibility(View.GONE);

        if(token==(null)|| token==""){
            bidButton.setVisibility(View.GONE);
            showBids.setVisibility(View.GONE);
        }

        bidButton.setOnClickListener(
                new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(((HomepageActivity)getActivity()));
                builder.setTitle("How much do you want to bid?");

                    // Set up the input
                final EditText input = new EditText(((HomepageActivity)getActivity()));
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bid = input.getText().toString();
                        try {
                            bid(bid);
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
        showBids.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        lv.setVisibility(View.VISIBLE);
                        bidId.setVisibility(View.VISIBLE);
                        acceptBid.setVisibility(View.VISIBLE);
                    }

                }
    );
        acceptBid.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        try {
                            accept_the_bid();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
        );
        try {
            getProject();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(ownerStr.equals(myUsername)){
            bidButton.setVisibility(View.GONE);
            if(the_status.equals("in-progress")){
                showBids.setVisibility(View.GONE);
                inProgress.setVisibility(View.VISIBLE);
            }
        }
        else{
            showBids.setVisibility(View.GONE);

        }


        return oneProjectView;
    }

    private void accept_the_bid() throws JSONException, ExecutionException, InterruptedException {
        ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Accepting the bid...");
        progressDialog.show();
        String bid_id= bidId.getText().toString();
        JSONObject jsonToPost = new JSONObject();
        jsonToPost.put("id", bid_id);
        HttpPostAsyncTask myTask = new HttpPostAsyncTask(jsonToPost, getActivity().getApplicationContext(), token);
        String theResponse = myTask.execute("http://52.59.230.90/projects/"+id+"/bid/accept/").get();
        int statusCode = Integer.parseInt(theResponse.substring(0, 3));
        response = theResponse.substring(3);
        progressDialog.dismiss();
        if (statusCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
            onGetProjectFailure();
        }
        else if(statusCode==HttpURLConnection.HTTP_OK){
            Toast.makeText(getActivity().getApplicationContext(), "You accepted the bid!", Toast.LENGTH_LONG).show();
        }
    }


    public void bid(String bid) throws JSONException, ExecutionException, InterruptedException {
        ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Bidding...");
        progressDialog.show();
        JSONObject jsonToPost = new JSONObject();
        jsonToPost.put("amount", bid);
        HttpPostAsyncTask myTask = new HttpPostAsyncTask(jsonToPost, getActivity().getApplicationContext(), token);
        String theResponse = myTask.execute("http://52.59.230.90/projects/"+id+"/bid/").get();
        int statusCode = Integer.parseInt(theResponse.substring(0, 3));
        response = theResponse.substring(3);
        progressDialog.dismiss();
        if (statusCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
            onGetProjectFailure();
        }
        else if(statusCode==HttpURLConnection.HTTP_OK){
            Toast.makeText(getActivity().getApplicationContext(), "Your bid is accepted.", Toast.LENGTH_LONG).show();
        }
    }

    public void getProject() throws ExecutionException, InterruptedException, JSONException, ParseException {
        HttpGetAsyncTask myTask = new HttpGetAsyncTask(getActivity(),token);
        String theResponse = myTask.execute("http://52.59.230.90/projects/"+id+"/").get();
        int statusCode = Integer.parseInt(theResponse.substring(0, 3));
        response = theResponse.substring(3);
        if (statusCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
            onGetProjectFailure();
        }
        else if(statusCode==HttpURLConnection.HTTP_OK){
            onGetProjectSuccess();
        }

    }

    public void onGetProjectFailure() {
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
    public void onGetProjectSuccess() throws JSONException, ParseException {
        JSONObject obj = new JSONObject(response);
        String deadline_str = obj.getString("deadline");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date deadlinee = sdf.parse(deadline_str);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strdate= dateFormat.format(deadlinee);

        title.setText(obj.getString("title"));
        owner.setText("Owner:\n"+obj.getString("client_username"));
        ownerStr=obj.getString("client_username");
        description.setText(obj.getString("description"));
        deadline.setText("Deadline:\n"+strdate);
        status.setText("Status:\n"+obj.getString("status"));
        averageBid.setText("Average bid:\n"+ obj.getString("average_bid"));
        bidCount.setText("Bid count:\n"+ obj.getString("bid_count"));
        the_status=obj.getString("status");
        if(obj.getString("status").equals("active")){
            status.setTextColor(Color.GREEN);
        }
        else{
            status.setTextColor(Color.RED);
        }
        String pricerange="Price range:\n"+obj.getString("min_price")+"-"+obj.getString("max_price");
        price_range.setText(pricerange);
        price_range.setTextColor(Color.YELLOW);
        JSONArray jArray = obj.getJSONArray("bids");
        if (jArray != null) {
            for (int j=0;j<jArray.length();j++){
                JSONObject thisBid= jArray.getJSONObject(j);
                bidList.add("id:"+thisBid.getInt("id")+" freelancer_username:"+thisBid.getString("freelancer_username")+" amount:"+thisBid.getInt("amount"));
            }
        }
    }

}
