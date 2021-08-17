package com.example.country;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class CountryDetail extends AppCompatActivity {

    public static List<Country> CountryList_border ;
    RecyclerView recyclerView_border;
    CountryAdapter mAdapter_border;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_detail);

        TextView txt=(TextView)findViewById(R.id.textViewName);
        txt.setText(MainActivity.CountryList_Country.get(MainActivity.country_postion).getName());

         txt=(TextView)findViewById(R.id.textViewNative);
        txt.setText(MainActivity.CountryList_Country.get(MainActivity.country_postion).getNativeName());


        recyclerView_border = (RecyclerView) findViewById(R.id.RecyclerViewCountry);
        CountryList_border = new ArrayList<>();

        mAdapter_border = new CountryAdapter(CountryList_border);


        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);



        recyclerView_border.setLayoutManager(layoutManager1);
        recyclerView_border.setItemAnimator(new DefaultItemAnimator());
        recyclerView_border.setAdapter(mAdapter_border);



        for (int i=0;i<MainActivity.CountryList_Country.get(MainActivity.country_postion).getBorder().size();i++){

                String search =MainActivity.CountryList_Country.get(MainActivity.country_postion).getBorder().get(i);

                Country country;
                for (int j = 0; j < MainActivity.CountryList_Country.size(); j++) {
                        if (MainActivity.CountryList_Country.get(j).getalpha3Code().equals(search)) {

                                String name = MainActivity.CountryList_Country.get(j).getName();
                                String nativename = MainActivity.CountryList_Country.get(j).getNativeName();
                                String border = MainActivity.CountryList_Country.get(j).getBorder().toString();
                                String alpha3Code = MainActivity.CountryList_Country.get(j).getalpha3Code();
                                int area=MainActivity.CountryList_Country.get(j).getArea();

                                country = new Country(name,  nativename,  i , area , border , alpha3Code,false);
                                CountryList_border.add(country);
                            }
                      }

                mAdapter_border.notifyDataSetChanged();

            }


        }



}