package com.example.country;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;

public class Country implements Comparable<Country> {

    private List<String> Border;
    private String Name;
    private String NativeName;
    private String alpha3Code;
    private int Area;
    private int idNumber;
    private boolean can_click;

    public Country(String name, String nativename,int id,int area,String border,String alpha3Code,boolean can_click) {
        this.Name = name;
        this.NativeName = nativename;
        this.idNumber=id;
        this.Area=area;
        this.alpha3Code=alpha3Code;
        this.can_click=can_click;

        this.Border = new ArrayList<String>();

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(border);
            for (int i = 0; i < jsonArray.length(); i++) {
                String jsonObject = jsonArray.getString(i);
                Border.add(jsonObject);
                }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getName() {        return Name;    }

    public String getNativeName() {        return NativeName;    }

    public int getId() {        return idNumber;    }

    public int getArea() {        return Area;    }

    public String getalpha3Code() {        return alpha3Code;    }

    public List<String> getBorder() {        return Border;    }

    public boolean getCan_click() {        return can_click;    }



    @Override
    public int compareTo(Country comparestu) {
        int compareage=((Country)comparestu).getArea();
        /* For Ascending order*/
        return this.Area-compareage;

    }




}
