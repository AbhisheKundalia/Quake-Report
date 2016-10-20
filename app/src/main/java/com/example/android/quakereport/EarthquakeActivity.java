/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.widget.ProgressBar;
import android.widget.TextView;

import static android.R.attr.order;
import static android.R.id.empty;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String USGS_URL = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;
    private ProgressBar loadingBar;

    private EarthquakeAdapter mEarthquakeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        //Set empty view
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(mEmptyStateTextView);

        if (!isNetworkConnected()) {
            //stop loading bar and display message that no Internet connection
            loadingBar = (ProgressBar) findViewById(R.id.loading_spinners);
            try {
                loadingBar.setVisibility(View.GONE);
            } catch (NullPointerException e) {
                Log.e(LOG_TAG, "Loading bar throws Null Exception if Network is not connected!!", e);
            }
            mEmptyStateTextView.setText(R.string.no_internet);

        } else {

            // Create a new {@link ArrayAdapter} of earthquakes with empty list of earthquakes
            mEarthquakeAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            earthquakeListView.setAdapter(mEarthquakeAdapter);

            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            Log.e(LOG_TAG, "Initloader is being called");
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);

            //Set onclick listener for the views
            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    //To get the current Earthquake from the list of earthquakes
                    Earthquake currentEarthquake = mEarthquakeAdapter.getItem(position);

                    //Set Intent to open the URL in the web browser
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(currentEarthquake.getUrl()));

                    //To verify if the application is present to handle the Intent
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            });


        }

    }

    /**
     * Checks if the device is connected or is connecting
     *
     * @return true if device is connected to internet
     */

    private boolean isNetworkConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
        Log.e(LOG_TAG, "Inside OncreateLoader:\n");
        return new EarthquakeLoader(this, USGS_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        Log.e(LOG_TAG, "Inside onLoadFinished:\n");

        //Set loading bar to gone on load complete
        loadingBar = (ProgressBar) findViewById(R.id.loading_spinners);
        try {
            loadingBar.setVisibility(View.GONE);
        } catch (NullPointerException e) {
            Log.e(LOG_TAG, "Loading bar throws Null Exception On load finished", e);
        }

        //Clears the data of previous earthquake load
        mEarthquakeAdapter.clear();

        if (earthquakes != null || !earthquakes.isEmpty()) {
            // mEmptyStateTextView.setText(R.string.no_earthquakes);/*
            mEarthquakeAdapter.addAll(earthquakes);
        }
        mEmptyStateTextView.setText(R.string.no_earthquakes);

    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {

        Log.e(LOG_TAG, "Inside OnLoaderReset:\n");

        //Clears the data of previous earthquake load
        mEarthquakeAdapter.clear();

    }
}
