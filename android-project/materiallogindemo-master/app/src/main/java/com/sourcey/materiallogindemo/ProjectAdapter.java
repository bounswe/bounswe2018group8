/**
 * This class helps adaptating the projects to the ListView.
 *
 * @author  Berkay Kozan github.com/leblebi1
 * @version 1.0
 * @since   2018 October
 */

package com.sourcey.materiallogindemo;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProjectAdapter extends ArrayAdapter<Project>  implements View.OnClickListener {
    private static ArrayList<Project> projects;
    private Context mContext;
    private LayoutInflater mInflater;
    // View lookup cache
    private static class ViewHolder {
        TextView txtTitle;
        TextView txtDescription;
        TextView txtPriceRange;
        TextView txtDeadline;
        TextView txtStatus;
        ImageView info;
    }


    public ProjectAdapter(@NonNull Context context, ArrayList<Project> projects) {
        super(context, 0, projects);
        this.projects = projects;
        mContext = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return projects.size();
    }


    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    private int lastPosition = -1;

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Project project = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            viewHolder.txtDescription = (TextView) convertView.findViewById(R.id.description);
            viewHolder.txtDeadline = (TextView) convertView.findViewById(R.id.deadline);
            viewHolder.txtPriceRange = (TextView) convertView.findViewById(R.id.price_range);
            viewHolder.txtStatus = (TextView) convertView.findViewById(R.id.status);

            //TODO:viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;
        String descriptionToSend=project.getDescription();
        if(project.getDescription().length()>160){
            descriptionToSend=project.getDescription().substring(0,159)+"...";
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strdate= dateFormat.format(project.getDeadline());

        viewHolder.txtTitle.setText(project.getTitle());
        viewHolder.txtDescription.setText(descriptionToSend);
        viewHolder.txtDeadline.setText("Deadline: "+strdate);
        viewHolder.txtPriceRange.setText("Price: "+project.getMinprice()+"-"+project.getMaxprice());
        viewHolder.txtPriceRange.setTextColor(Color.YELLOW);
        if(project.getStatus().equals("active")){
            viewHolder.txtStatus.setTextColor(Color.GREEN);
        }
        else{
            viewHolder.txtStatus.setTextColor(Color.RED);
        }
        viewHolder.txtStatus.setText("Status: "+project.getStatus());

        viewHolder.txtStatus.setOnClickListener(this);
        viewHolder.txtStatus.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public void onClick(View v) {
        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Project project=(Project)object;

        switch (v.getId())
        {
            case R.id.status:
                Snackbar.make(v, project.getDescription(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }
}
