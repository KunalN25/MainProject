package com.example.mainproject.RestaurantOperations.RestaurantsList;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.R;
import com.example.mainproject.RestaurantOperations.RestaurantValuesClasses.RestaurantJSONItems;
import com.example.mainproject.RestaurantOperations.RestaurantValuesClasses.RestaurantLocation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class HomeFragment extends Fragment  {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<RestaurantJSONItems> restaurantJSONItemsList;

    private String TAG="Main";
    private TextView location;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_home,container,false);
        initialize(v);

        //Add longitude and latitude
        Log.d(TAG, "onCreateView: Home Fragment");
        loadJSONDataFromZomato();
        return v;
    }
    private void initialize(View v) {
        initRecyclerView(v);

        location=v.findViewById(R.id.location);
        // search=findViewById(R.id.search);
        // button=findViewById(R.id.btn);
        //   show=v.findViewById(R.id.showAll);
    }
    private void initRecyclerView(View v){
        recyclerView=v.findViewById(R.id.recyclerView);
        Log.d(TAG, "initRecyclerView: Recycler View is initialized");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


    }



    public  void showView(String jsonData)
    {
        restaurantJSONItemsList=new ArrayList<>();      //We re-initialize the arraylist everytime the search button is clicked
        //as it should reload the list when new item is searched.
        adapter=new JSONAdapter(getActivity(),restaurantJSONItemsList);



        loadFromMumbaiRestaurants(jsonData);
        if(recyclerView==null)
            Log.d(TAG, "showView: Recycler view is null");
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }
    private void loadFromMumbaiRestaurants(String s){

        try {
            JSONObject jsonObject=new JSONObject(s);

            JSONArray restaurants=jsonObject.getJSONArray("restaurants");
            for(int i=0;i<20;i++){
                jsonObject=restaurants.getJSONObject(i);
                JSONObject restaurantData=jsonObject.getJSONObject("restaurant");
                JSONObject location=restaurantData.getJSONObject("location");
                restaurantJSONItemsList.add(new RestaurantJSONItems(restaurantData.getString("name"),
                        restaurantData.getString("featured_image"),
                        restaurantData.getInt("average_cost_for_two"),
                        new RestaurantLocation(location.getString("city"),
                                location.getString("address"),
                                location.getString("locality_verbose")),
                        restaurantData.getString("cuisines"),
                        restaurantData.getString("phone_numbers"),
                        restaurantData.getJSONObject("user_rating").getString("aggregate_rating"),
                        restaurantData.getJSONObject("all_reviews").getJSONArray("reviews").toString()));



            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void loadJSONDataFromZomato() {
        final String basicURL="https://developers.zomato.com/api/v2.1";

        String latitude="19.1511";//lat.getText().toString();
        String longitude="72.9372";//longit.getText().toString();
        Log.d(TAG, "onClick: Submit clicked");
        String URL = basicURL+"/search?lat="+latitude+"&lon="+longitude;//+"&radius=5000&sort=real_distance";
        GetZomatoData getZomatoData=new GetZomatoData(getActivity());
        getZomatoData.execute(URL);
        String jsonData=null;

        try {
            jsonData = getZomatoData.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(jsonData!=null){
            showView(jsonData);
        }
    }

}
