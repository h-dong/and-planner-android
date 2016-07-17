package com.andigital.apps.andplanner;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ScheduleActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView responseView;
    private ProgressBar progressBar;
    private ListView listView;
    private ArrayList<Person> people;
    private PeopleListAdapter adapter;
    private static String teamID;
    private static String API_URL = "http://52.51.66.81:8080/phaseAssignees";
    // http://52.51.66.81:8080/leaveTypes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        responseView = (TextView) findViewById(R.id.responseView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            teamID = extras.getString("TEAM_ID");
        }

        setPeopleListViewAdapter();

        // Get data from API
        new RetrieveDataFromAPI().execute();
    }

    private void setPeopleListViewAdapter() {
        people = new ArrayList<>();
        adapter = new PeopleListAdapter(this, people);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.schedule, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_schedule) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_schedule) {
            // Handle the camera action
        } else if (id == R.id.nav_weekly) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_feedback) {

        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class RetrieveDataFromAPI extends AsyncTask<String, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            responseView.setText("");
        }

        protected String doInBackground(String... urls) {
            String response;

            try {
                URL url = new URL(API_URL + "?phaseId=" + teamID);
//                URL url = new URL(API_HOST + API_USERS + "?" + API_PER_PAGE + API_PER_PAGE_NUM + API_AUTH);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();

                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    bufferedReader.close();
                    response = stringBuilder.toString();

                    return response;
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {

            if (response == null) { // No response from sever
                progressBar.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);
                responseView.setVisibility(View.VISIBLE);
                responseView.setText("Unable to retrieve data from server");
            } else {
                progressBar.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                responseView.setVisibility(View.GONE);
                Log.i("INFO", response);

                try {
                    adapter.clear();

                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Person person = new Person();
                        JSONObject item = jsonArray.getJSONObject(i);

                        person.setId(item.getInt("id"));
                        person.setName(item.getString("display_name"));
                        person.setEmail(item.getString("email"));
                        person.setRole(item.getString("role"));
                        person.setThumbnail(item.getString("thumbnail"));

//                        JSONArray assignmentsArray = new JSONArray(item.getJSONArray("assignments"));
//                        person.setAssignments(assignmentsArray);

                        people.add(person);
                    }

                    listView.setAdapter(adapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
