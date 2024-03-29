package com.example.mainproject.RestaurantOperations.RestaurantsList;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.example.mainproject.LoginAndRegistration.InternetConnection;
import com.example.mainproject.MainActivity;
import com.example.mainproject.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetZomatoData extends AsyncTask<String,Void,String> {
    private Context context;
    private String TAG="Main";
    private ProgressBar progressBar;


    public GetZomatoData(Context context) {
        this.context = context;
    }


    @Override
    protected void onPostExecute(String s) {
        if(s==null){
            Log.d(TAG, "onPostExecute: data could not be loaded");
            MainActivity.getInstance().startConnectionErrorFragment();
        }

    }

    @Override
    protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection connection=null;
            StringBuilder buf=null;
            try {
                url = new URL(strings[0]);

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-type","application/json");
                connection.setRequestProperty("user-key","3a8321b70f68f4d7c9072aaa947c87e3");
                BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;

                buf = new StringBuilder();
                while (( inputLine= reader.readLine()) != null) {


                    buf.append(inputLine);
                }

                reader.close();
                connection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
                if (connection != null) {
                    connection.disconnect();
                }

            }
            finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            Log.d(TAG, "doInBackground: Data is loaded");

            if (buf != null) {
                return buf.toString();
            }
            else
                return null;



    }
}
