package com.example.country;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public static int country_postion;
    public static List<Country> CountryList_Country ;
    RecyclerView recyclerView_Country;
    CountryAdapter mAdapter_Country;

    boolean name_sort_asc=false;
    boolean area_sort_asc=false;


    String myUrl = "https://restcountries.eu/rest/v2/all?fields=name;nativeName;borders;area;alpha3Code";

     ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

 ;

        recyclerView_Country = (RecyclerView) findViewById(R.id.RecyclerViewCountry);
        CountryList_Country = new ArrayList<>();

        mAdapter_Country = new CountryAdapter(CountryList_Country);


        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);



        recyclerView_Country.setLayoutManager(layoutManager1);
        recyclerView_Country.setItemAnimator(new DefaultItemAnimator());
        recyclerView_Country.setAdapter(mAdapter_Country);



            // create object of MyAsyncTasks class and execute it
            MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
            myAsyncTasks.execute();


        LocalBroadcastManager.getInstance(getApplication()).registerReceiver(mMessageReceiver,
                new IntentFilter("message_subject_intent"));



    }




    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            country_postion= Integer.parseInt(intent.getStringExtra("the_id"));

            Intent intent2Open = new Intent(getApplicationContext(), CountryDetail.class);
            startActivity(intent2Open);

            }

        };


        public void click_name_sortasc(View view) {
        Button btn=(Button)findViewById(R.id.button_Sort_Name);
        if (name_sort_asc){
            btn.setText("Name Asc");
            name_sort_asc=false;
            Collections.sort(CountryList_Country, new Comparator<Country>() {
                public int compare(Country result1, Country result2) {
                    return result1.getName().compareTo(result2.getName());
                }
            });
        }else{
            btn.setText("Name Dsc");
            name_sort_asc=true;
            Collections.sort(CountryList_Country, new Comparator<Country>() {
                public int compare(Country result1, Country result2) {
                    return result2.getName().compareTo(result1.getName());
                }
            });
        }

        mAdapter_Country.notifyDataSetChanged();
    }

    public void click_area_sortasc(View view) {
        Button btn=(Button)findViewById(R.id.button_sort_area);
        if (area_sort_asc){
            btn.setText("Area Dsc");

            area_sort_asc=false;
            Collections.sort(CountryList_Country);
        }else {
            btn.setText("Area Asc");

            area_sort_asc=true;
            Collections.sort(CountryList_Country, Collections.reverseOrder());
        }



        mAdapter_Country.notifyDataSetChanged();
    }

    public class MyAsyncTasks extends AsyncTask<String, String, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("processing results");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
            @Override
            protected String doInBackground(String... params) {


                String result = "";
                try {
                    URL url;
                    HttpURLConnection urlConnection = null;
                    try {
                        url = new URL(myUrl);

                        urlConnection = (HttpURLConnection) url.openConnection();

                        InputStream in = urlConnection.getInputStream();

                        InputStreamReader isw = new InputStreamReader(in);

                        int data = isw.read();

                        while (data != -1) {
                            result += (char) data;
                            data = isw.read();

                        }

                        return result;

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (urlConnection != null) {
                            urlConnection.disconnect();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return "Exception: " + e.getMessage();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String s) {

                progressDialog.dismiss();
                try {

                    JSONArray jsonArray = new JSONArray(s);

                    Country country;

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("name");
                        String nativename = jsonObject.getString("nativeName");
                        String border = jsonObject.getString("borders");
                        String alpha3Code = jsonObject.getString("alpha3Code");
                        int area=0;
                        try {
                              area = jsonObject.getInt("area");

                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                        country = new Country(name,  nativename,  i , area , border , alpha3Code,true);
                        CountryList_Country.add(country);

                    }

                mAdapter_Country.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }