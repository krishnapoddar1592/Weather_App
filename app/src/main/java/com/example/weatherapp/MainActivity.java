package com.example.weatherapp;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    public class DownloadData extends AsyncTask<String,Void,String> {
        String result="";
        @Override
        protected String doInBackground(String... urls) {
            URL url = null;
            try {
                url=new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream in =connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data=reader.read();
                while(data!=-1){
                    char current = (char) data;
                    result+=current;
                    data=reader.read();
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        public String get_text(String s){
            try {
                JSONObject jsonObject=new JSONObject(s);
                String weather_info=jsonObject.getString("weather");
                System.out.println(weather_info);
                String temp_info="["+jsonObject.getString("main")+"]";
                System.out.println(temp_info);
                JSONArray arr=new JSONArray(weather_info);
                JSONArray arr_temp=new JSONArray(temp_info);
                for(int i=0;i<arr.length();i++){
                    JSONObject jsonPart =arr.getJSONObject(i);
                    JSONObject jsonPart2 =arr_temp.getJSONObject(i);
                    return "Weather: "+jsonPart.getString("main")+"\n"+"Description: "+jsonPart.getString("description")+"\n"+"Temperature: "+jsonPart2.getString("temp")+"°C"+"\n"+"Feels like: "+jsonPart2.getString("feels_like")+"°C"+"\n"+"Humidity: "+jsonPart2.getString("humidity")+"%";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject=new JSONObject(s);
                String weather_info=jsonObject.getString("weather");
                System.out.println(weather_info);
                String temp_info="["+jsonObject.getString("main")+"]";
                System.out.println(temp_info);
                JSONArray arr=new JSONArray(weather_info);
                JSONArray arr_temp=new JSONArray(temp_info);
                for(int i=0;i<arr.length();i++){
                    JSONObject jsonPart =arr.getJSONObject(i);
                    JSONObject jsonPart2 =arr_temp.getJSONObject(i);
                    Log.i("main",jsonPart.getString("main"));
                    Log.i("description",jsonPart.getString("description"));
                    Log.i("main",jsonPart2.getString("temp"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        EditText editText;
        AutoCompleteTextView editText;
        Button button;
        TextView textView;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#4A6572"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);


        editText=findViewById(R.id.editText);
        button=findViewById(R.id.button);
        textView=findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager mgr= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(editText.getWindowToken(),0);
                String text;
                String text_2="";
                String city_text="";
                DownloadData task =new DownloadData();
                try {
                    city_text= URLEncoder.encode(String.valueOf(editText.getText()),"UTF-8");
                    text=task.execute(String.valueOf(new URL("https://api.openweathermap.org/data/2.5/weather?q="+city_text+"%20&appid=756b069dd446ca1c68a0776d24592338&units=metric"))).get();
                    text_2=task.get_text(text);
                    if(text_2.equals(null)){
                        text_2="enter a valid city";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    text_2="enter a valid city";
                    System.out.println(text_2);
                }
                textView.setText(text_2);
            }
        });


    }
}