package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import static com.example.android.quakereport.QueryUtils.LOG_TAG;

/**
 * Created by Abhishek on 10/20/2016.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    String mUrl;
    public EarthquakeLoader(Context context, String url) {
        super(context);
        // TODO: Finish implementing this constructor
        mUrl = url;
    }

    @Override
    public List<Earthquake> loadInBackground() {
        Log.e(LOG_TAG,"Inside loadInBackground:\n");

        if (mUrl == null)
        {
            return null;
        }
        return QueryUtils.fetchEarthquakeData(mUrl);
    }

    @Override
    protected void onStartLoading() {
        Log.e(LOG_TAG,"Inside onStartLoading:\n");

        forceLoad();
    }

}
