package com.example.android.quakereport;

/**
 * Created by Abhishek on 10/11/2016.
 */

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * This class is created to serve the purpose of custom Arrayadapter
 * Purpose: is to read the respective variable from Earthquake class and pass it to view thus
 * ensuring correct display of data with respect to ids in xml
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {


    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     * (we initialize the value of context and Arraylist in super class from the arguments )
     *
     * @param context     The current context. Used to inflate the layout file.
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
        if (listItemView == null) {

            //layoutInflater Instantiates a layout XML file into its corresponding View objects.
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list3_item, parent, false);
        }

        //Get the current Earthquake object details
        Earthquake currentEarthquake = getItem(position);

        /**Read the values for each text holder from the current Earthquake object and assign
         * them to the respective View from xml
         */

        //Find the first TextView from list3_item.xml layout with ID 'magnitudeView'
        TextView magnitudeView = (TextView) listItemView.findViewById(R.id.magnitudeView);

        //Get the value of Magnitude from currentEarthquake object and set this text on magnitude Textview
        magnitudeView.setText(formatMagnitude(currentEarthquake.getMagnitude()));

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        //Find the Second TextView from list3_item.xml layout with ID 'locationoffsetView'
        TextView locationOffSetView = (TextView) listItemView.findViewById(R.id.locationoffsetView);

        //Get the value of Location from currentEarthquake object and get its offset location
        // from method getOffSetLocation and set this text on Location Offset Textview
        locationOffSetView.setText(getOffSetLocation(currentEarthquake.getLocation()));

        //Find the third TextView from list3_item.xml layout with ID 'primaryLocationView'
        TextView primaryLocationView = (TextView) listItemView.findViewById(R.id.primarylocationView);

        //Get the value of Location from currentEarthquake object and get its Primary location
        // from method getPrimaryLocation and set this text on Primary Location Textview
        primaryLocationView.setText(getPrimaryLocation(currentEarthquake.getLocation()));

        // Create a new Date object from the time in milliseconds of the earthquake
        Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());

        // Find the TextView with view ID date
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        // Format the date string (i.e. "Mar 3, 1984")
        String formattedDate = formatDate(dateObject);
        // Display the date of the current earthquake in that TextView
        dateView.setText(formattedDate);

        // Find the TextView with view ID time
        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        // Format the time string (i.e. "4:30PM")
        String formattedTime = formatTime(dateObject);
        // Display the time of the current earthquake in that TextView
        timeView.setText(formattedTime);
        return listItemView;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    /**
     * Returns the off set location of Location e.g. "72Km W of" else sets it to "Near the"
     *
     * @param location is the actual location from which off set location is retrieved
     * @return Off set location as String as"72Km W of"
     */
    private String getOffSetLocation(String location) {
        //Check if the location contains Off set string if not return "Near the" as off set location
        if (location.contains("of")) {
            int positionOf = location.indexOf("of");
            if (positionOf > 0) {
                location = location.substring(0, positionOf + 2);
            }
        } else {
            location = getContext().getString(R.string.near_the);
        }
        return location;
    }

    /**
     * Returns the Primary location of Location e.g. "Rumoi, Japan"
     *
     * @param location is the actual location from which Primary location is retrieved
     * @return Off set location as String as"Rumoi, Japan"
     */
    private String getPrimaryLocation(String location) {
        //Check if the location contains Off set string if not return "Near the" as off set location
        if (location.contains("of")) {
            int positionOf = location.indexOf("of");
            if (positionOf > 0) {
                location = location.substring(positionOf + 2);
            }
        }
        return location;
    }

    /**
     * This method formats the magnitude to display in 1 decimal place.
     *
     * @param magnitude is in decimal with more than 1 decimal place e.g. 1.233
     * @return converted magnitude with only 1 decimal place e.g. 1.2
     */
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        // to get the value of the Resource ID of color
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
