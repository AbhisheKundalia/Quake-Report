package com.example.android.quakereport;

/**
 * Created by Abhishek on 10/11/2016.
 */

/**
 * Class to store all the data of one earthquake instance including score, place and time
 */
public class Earthquake {

    //To store the Magnitude of Earthquake on richter scale
    private double mMagnitude;

    //To store the Location where Earthquake took place
    private String mLocation;

    //To store the Date when Earthquake took place
    private long mTimeInMilliseconds;

    //To store the URL of the Earthquake details for current place
    private String mUrl;

    /**
     * Constructs a new {@link Earthquake} object.
     *
     * @param magnitude          is the magnitude (size) of the earthquake
     * @param location           is the city location of the earthquake
     * @param timeInMilliseconds is the time in milliseconds (from the Epoch) when the
     *                           earthquake happened
     */
    public Earthquake(double magnitude, String location, long timeInMilliseconds, String url) {
        mMagnitude = magnitude;
        mLocation = location;
        mTimeInMilliseconds = timeInMilliseconds;
        mUrl = url;
    }

    /**
     * To get the Magnitude of Earthquake
     *
     * @return Magnitude of Earthquake
     */
    public double getMagnitude() {
        return mMagnitude;
    }

    /**
     * To get the name of Location where Earthquake took place
     *
     * @return Location where Earthquake took place
     */
    public String getLocation() {
        return mLocation;
    }

    /**
     * To get the Date on which Earthquake occurred for the Location
     *
     * @return Date when Earthquake occurred
     */
    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    /**
     * To get the URL to redirect user to fetch more details of the Earthquake from the webpage
     *
     * @return
     */
    public String getUrl() {
        return mUrl;
    }
}
