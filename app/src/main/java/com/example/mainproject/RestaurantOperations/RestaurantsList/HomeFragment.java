package com.example.mainproject.RestaurantOperations.RestaurantsList;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.MainActivity;
import com.example.mainproject.R;
import com.example.mainproject.RestaurantOperations.RestaurantValuesClasses.RestaurantJSONItems;
import com.example.mainproject.RestaurantOperations.RestaurantValuesClasses.RestaurantLocation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static android.view.View.GONE;

public class HomeFragment extends Fragment  {
    private RecyclerView recyclerView;
    private List<RestaurantJSONItems> restaurantJSONItemsList;
    private String TAG="Main";
    private TextView location;
    private ProgressBar progressBar;
    private String latitude, longitude, address;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_home,container,false);
        initialize(v);
        progressBar.setVisibility(View.VISIBLE);
        //Add longitude and latitude

        loadJSONDataFromZomato();
        location.setText(address);
        location.setMovementMethod(new ScrollingMovementMethod());
        progressBar.bringToFront();
        return v;

    }
    private void initialize(View v) {
        initRecyclerView(v);

        location=v.findViewById(R.id.location);
        progressBar=v.findViewById(R.id.restaurantViewProgressBar);
        // search=findViewById(R.id.search);
        // button=findViewById(R.id.btn);
        //   show=v.findViewById(R.id.showAll);
        try {
            address = getAddress();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void initRecyclerView(View v){
        recyclerView=v.findViewById(R.id.recyclerView);
        Log.d(TAG, "initRecyclerView: Recycler View is initialized");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


    }



    private void showView(String jsonData)
    {
        restaurantJSONItemsList=new ArrayList<>();      //We re-initialize the arraylist everytime the search button is clicked
        //as it should reload the list when new item is searched.
        RecyclerView.Adapter adapter = new JSONAdapter(getActivity(), restaurantJSONItemsList);



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


    private void loadJSONDataFromZomato() {
        final String basicURL = "https://developers.zomato.com/api/v2.1"; //Basic URL add all attahcments
        String URL = basicURL + "/search?lat=" + latitude + "&lon=" + longitude + "&radius=5000&sort=real_distance";
        GetZomatoData getZomatoData=new GetZomatoData(getActivity());
        getZomatoData.execute(URL);
        String jsonData=null;

        try {
            jsonData = getZomatoData.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        if(jsonData!=null){
            progressBar.setVisibility(GONE);
            showView(jsonData);
        }
        else{
            MainActivity.getInstance().getProgressBar(progressBar);
        }
    }

    public void getLocationDetails(double latitude, double longitude, String address) {
        this.latitude = latitude + "";
        this.longitude = longitude + "";
        // this.address = address;
        Log.d("kun", "getLocationDetails: lat:" + this.latitude + " long:" + this.longitude);

    }

    private String getAddress() throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        addresses = geocoder.getFromLocation(Double.parseDouble(latitude), Double.parseDouble(longitude), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        addresses.get(0).getSubLocality();
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
        Log.d(TAG, "getAddress: " + addresses.get(0).getAddressLine(0));
        return address;
    }


}
