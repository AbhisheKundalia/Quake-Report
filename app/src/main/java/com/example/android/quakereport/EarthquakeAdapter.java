package com.example.android.quakereport;

/**
 * Created by Abhishek on 10/11/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is created to serve the purpose of custom Arrayadapter
 * Purpose: is to read the respective variable from Earthquake class and pass it to view thus
 * ensuring correct display of data with respect to ids in xml
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake>{


    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *(we initialize the value of context and Arraylist in super class from the arguments )
     * @param context        The current context. Used to inflate the layout file.
     * @param earthquakes A List of Earthquake objects to display in a list
     */
    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for three TextViews , the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate/create the view
        View listItemView = convertView;
        if(listItemView == null) {

            //layoutInflater Instantiates a layout XML file into its corresponding View objects.
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list3_item, parent, false);
        }

        //Get the current Earthquake object details
        Earthquake currentEarthquake = getItem(position);

        /**Read the values for each text holder from the current Earthquake object and assign
         * them to the respective View from xml
         */

        //Find the first TextView from list3_item.xml layout with ID 'magnitudeView'
        TextView magnitudeView = (TextView)listItemView.findViewById(R.id.magnitudeView);

        //Get the value of Magnitude from currentEarthquake object and set this text on magnitude Textview
        magnitudeView.setText(currentEarthquake.getMagnitude());

        //Find the Second TextView from list3_item.xml layout with ID 'locationView'
        TextView locationView = (TextView)listItemView.findViewById(R.id.locationView);

        //Get the value of Location from currentEarthquake object and set this text on Location Textview
        locationView.setText(currentEarthquake.getLocation());

        //Find the third TextView from list3_item.xml layout with ID 'dateView'
        TextView dateView = (TextView)listItemView.findViewById(R.id.dateView);

        //Get the value of date from currentEarthquake object and set this text on Date Textview
        dateView.setText(currentEarthquake.getDate());

        return listItemView;
    }
}
