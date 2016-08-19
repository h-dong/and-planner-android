package com.andigital.apps.andplanner;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by hdong on 17/07/2016.
 */
public class PeopleListAdapter extends ArrayAdapter<Person> {

    private static class ViewHolder {
        private ImageView thumbnail;
        private TextView name;
        private TextView email;
        private TextView role;
    }

    public PeopleListAdapter(Context context, ArrayList<Person> person) {
        super(context, R.layout.people_list_view, person);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Person person = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.people_list_view, parent, false);

            viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.personThumbnail);
            viewHolder.name = (TextView) convertView.findViewById(R.id.personName);
            viewHolder.email = (TextView) convertView.findViewById(R.id.personEmail);
            viewHolder.role = (TextView) convertView.findViewById(R.id.personRole);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.name.setText(person.getName());
        viewHolder.email.setText(person.getEmail());
        viewHolder.role.setText(person.getRole());
        if (person.getThumbnail().isEmpty()) {
            viewHolder.thumbnail.setImageResource(R.drawable.ic_placeholder_img);
        } else{
            Picasso.with(this.getContext())
                    .load(person.getThumbnail())
                    .placeholder(R.drawable.ic_placeholder_img)
                    .resize(200, 200)
                    .centerCrop()
                    .into(viewHolder.thumbnail);
        }

        // Return the completed view to render on screen
        return convertView;
    }

}
