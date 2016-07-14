package com.andigital.apps.andplanner;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hdong on 14/07/2016.
 */
public class TeamListAdapter extends ArrayAdapter<Team> {

    private static class ViewHolder {
        private TextView name;
        private TextView timePeriod;
    }

    public TeamListAdapter(Context context, ArrayList<Team> teams) {
        super(context, R.layout.team_list_view, teams);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Team team = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.team_list_view, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.teamName);
            viewHolder.timePeriod = (TextView) convertView.findViewById(R.id.teamPeriod);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.name.setText(team.getName());
        if (team.getStartDate() != null && team.getEndDate() != null) {
            String periodString = "From " + team.getStartDate() + " to " + team.getEndDate();
            viewHolder.timePeriod.setText(periodString);
        } else {
            viewHolder.timePeriod.setText("N/A");
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
