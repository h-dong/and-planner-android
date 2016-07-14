package com.andigital.apps.andplanner;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class TeamActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView responseView;
    private ProgressBar progressBar;
    private ListView listView;
    private ArrayList<Team> teams;
    private TeamListAdapter adapter;
    private static String API_URL = "http://52.51.66.81:8080/phases";
    private static String projectID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

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
            projectID = extras.getString("PROJECT_ID");
        }

        setTeamListViewAdapter();

        // Get data from API
        new RetrieveDataFromAPI().execute();
    }

    private void setTeamListViewAdapter() {
        teams = new ArrayList<>();
        adapter = new TeamListAdapter(this, teams);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_projects) {
            this.finish();
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
                URL url = new URL(API_URL + "?id=" + projectID);
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
                        Team team = new Team();
                        JSONObject item = jsonArray.getJSONObject(i);

                        team.setId(item.getInt("id"));
                        team.setName(item.getString("phase_name"));

                        if (item.has("starts_at")) {
                            team.setStartDate(item.getString("starts_at"));
                        }

                        if (item.has("ends_at")) {
                            team.setEndDate(item.getString("ends_at"));
                        }

                        teams.add(team);
                    }

                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                            Intent intent = new Intent(getBaseContext(), ScheduleActivity.class);
                            intent.putExtra("TEAM_ID", Integer.toString(teams.get(position).getId()));
                            startActivity(intent);
                        }

                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
