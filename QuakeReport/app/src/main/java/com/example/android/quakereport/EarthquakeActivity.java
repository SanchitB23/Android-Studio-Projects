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

import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


//https://github.com/udacity/ud843-QuakeReport The Go-To for final code

public class EarthquakeActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<List<Detail>>{

    //    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String JSON_RESPONSE_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query";
    private DetailsAdapter mDetailsAdapter;

    private static final int EARTHQUAKE_LOADER_ID = 1;
    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);




        /*
Create a fake list of earthquake locations.
String placeHolder = "date";
final ArrayList<Detail> earthquakes = JsonQuery.extractEarthquakes();
earthquakes.add(new Detail("7.2", "San Francisco", placeHolder));
earthquakes.add(new Detail("7.2", "San Francisco", placeHolder));
earthquakes.add(new Detail("7.2", "San Francisco", placeHolder));
earthquakes.add(new Detail("7.2", "San Francisco", placeHolder));
earthquakes.add(new Detail("7.2", "San Francisco", placeHolder));
earthquakes.add("London");
earthquakes.add("Tokyo");
earthquakes.add("Mexico City");
earthquakes.add("Moscow");
earthquakes.add("Rio de Janeiro");
earthquakes.add("Paris");
Find a reference to the {@link ListView} in the layout
*/


        final ListView earthquakeListView = (ListView) findViewById(R.id.list);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(mEmptyStateTextView);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_spinner);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }


        // Set the adapter on the {@link ListView}
        mDetailsAdapter = new DetailsAdapter(this, new ArrayList<Detail>());
        // so the list can be populated in the user interface
        if (earthquakeListView != null) {
            earthquakeListView.setAdapter(mDetailsAdapter);
        }

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Find the current earthquake that was clicked on
                Detail currentEarthquake = mDetailsAdapter.getItem(i);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getmUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

       /* EarthquakeAsyncTask task = new EarthquakeAsyncTask();   //ASyncTask
        task.execute(JSON_RESPONSE_URL);*/



    }

    @Override
    public Loader<List<Detail>> onCreateLoader(int i, Bundle bundle) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));

        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        Uri baseUri = Uri.parse(JSON_RESPONSE_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "10");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", orderBy);

        return new EarthquakeLoader(this, uriBuilder.toString());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onLoadFinished(Loader<List<Detail>> loader, List<Detail> details) {
    // Clear the adapter of previous earthquake data
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.loading_spinner);
        progressBar.setVisibility(View.GONE);

        // Set empty state text to display "No earthquakes found."
        mEmptyStateTextView.setText(R.string.No_Earthquake);

        mDetailsAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (details != null && !details.isEmpty()) {
            mDetailsAdapter.addAll(details);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Detail>> loader) {
        mDetailsAdapter.clear();
    }

    // TODO DISPLAY ICON ON TOP OF THE APP BAR

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Detail>> {

        @Override
        protected List<Detail> doInBackground(String... url) {
            if (url.length < 1 || url[0] == null) {
                return null;
            }
            List<Detail> EarthquakesList = JsonQuery.fetchEarthquakeData(url[0]);
            return EarthquakesList;
        }

        @Override
        protected void onPostExecute(List<Detail> details) {
            // Clear the adapter of previous earthquake data
            mDetailsAdapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (details != null && !details.isEmpty()) {
                mDetailsAdapter.addAll(details);
            }
        }
    }
}
