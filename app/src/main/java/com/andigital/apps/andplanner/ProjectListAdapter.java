package com.andigital.apps.andplanner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by hdong on 14/07/2016.
 */
public class ProjectListAdapter extends ArrayAdapter<Project> {

    private static class ViewHolder {
        private ImageView thumbnail;
        private TextView name;
        private TextView timePeriod;
    }

    public ProjectListAdapter(Context context, ArrayList<Project> projects) {
        super(context, R.layout.project_list_view, projects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Project project = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.project_list_view, parent, false);
            viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.projectThumbnail);
            viewHolder.name = (TextView) convertView.findViewById(R.id.projectName);
            viewHolder.timePeriod = (TextView) convertView.findViewById(R.id.projectPeriod);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.name.setText(project.getName());
        String periodString = "(" + project.getStartDate() + " to " + project.getEndDate() + ")";
        viewHolder.timePeriod.setText(periodString);
        Picasso.with(this.getContext())
                .load(project.getThumbnail())
                .placeholder(R.drawable.ic_placeholder_img)
                .resize(200, 200)
                .centerCrop()
                .into(viewHolder.thumbnail);
        // Return the completed view to render on screen
        return convertView;
    }

}
