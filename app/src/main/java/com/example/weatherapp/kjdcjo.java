package com.example.weatherapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class kjdcjo {
    public static void main(String[] args) throws JSONException {
        JSONArray jsonObject=new JSONArray("/city_list.json");
        for(int i =0; i<10;i++){
            System.out.println(String.valueOf(jsonObject.get(i)));
        }
//        String weather_info=jsonObject.getString("weather");
//        System.out.println(weather_info);
//        String temp_info="["+jsonObject.getString("main")+"]";
//        System.out.println(temp_info);
//        JSONArray arr=new JSONArray(weather_info);
//        JSONArray arr_temp=new JSONArray(temp_info);
    }
}
