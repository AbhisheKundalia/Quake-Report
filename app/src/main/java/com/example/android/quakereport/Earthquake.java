package com.example.android.quakereport;

import java.util.Date;

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
    private String mDate;

    /*
    * Create a new Earthquake object.
    *
    * @param vMagnitude is the score of Earthquake on richter scale (e.g. 0.1-9.9)
    * @param vLocation is the corresponding place where Earthquake took place (e.g. Bangalore)
    * @param vDate is the date when the respective Earthquake took place (e.g. Feb 2,2016
    * */
    public Earthquake(double vMagnitude, String vLocation, String vDate){
        mMagnitude = vMagnitude;
        mLocation = vLocation;
        mDate = vDate;
    }

    /**
     * To get the Magnitude of Earthquake
     * @return Magnitude of Earthquake
     */
    public String getMagnitude(){
        return String.valueOf(mMagnitude);
    }

    /**
     * To get the name of Location where Earthquake took place
     * @return Location where Earthquake took place
     */
    public String getLocation() {
        return mLocation;
    }

    /**
     * To get the Date on which Earthquake occurred for the Location
     * @return Date when Earthquake occurred
     */
    public String getDate() {
        return mDate;
    }
}
