package com.example.mainproject.RestaurantOperations;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.mainproject.R;
import com.example.mainproject.RestaurantOperations.RestaurantMenu.MenuItem;
import com.example.mainproject.RestaurantOperations.RestaurantValuesClasses.RestaurantJSONItems;

import org.json.JSONArray;

import java.util.List;


public class RestaurantFragment extends Fragment implements AdapterView.OnItemClickListener {

    public RestaurantFragment() {
        // Required empty public constructor
    }


    private  ImageView imageView;
    private TextView name,cuisine,
            address, phone_numbers, ratings;
    private ListView listView;
    private  final String TAG="Main";
    private  Context context;
    private ArrayAdapter<String> arrayAdapter;
    private String[] listItems={"Order Now","Reviews"};
    private RestaurantJSONItems restaurantJSONItems;
    private RestaurantFragmentMethods restaurantFragmentMethods;
    private String reviews;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_restaurant, container, false);
        initialize(v);
        loadRestaurantIntoFragment();
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this);

        return v;
    }

    private void initialize(View v) {
        imageView=v.findViewById(R.id.fragmentRestaurantPhoto);
        name=v.findViewById(R.id.fragmentRestaurantName);
        cuisine=v.findViewById(R.id.fragmentCuisine);
        address = v.findViewById(R.id.fragmentRestaurantAddress);
        ratings=v.findViewById(R.id.ratings);
        phone_numbers=v.findViewById(R.id.phoneNumbers);
        listView=v.findViewById(R.id.fragmentListViewForRestaurantProperties);
        context=getActivity();
        if (context != null) {
            arrayAdapter = new ArrayAdapter<>(context, R.layout.textview_for_lists, listItems);
        }
    }

    void setRestaurantObject(RestaurantJSONItems restaurantJSONItems)
    {
        this.restaurantJSONItems=restaurantJSONItems;
    }

    private   void loadRestaurantIntoFragment(){
        if(!restaurantJSONItems.getPhotos_url().isEmpty()) {
            Glide.with(context)
                    .asBitmap()
                    .fitCenter()
                    .load(restaurantJSONItems.getPhotos_url())
                    .into(imageView); //Download Image from the URL
        }
        else
            imageView.setImageResource(R.drawable.restaurant_default);

        name.setText(restaurantJSONItems.getName());
        address.setText("Address : "+restaurantJSONItems.getLocation().getAddress());
        ratings.setText(restaurantJSONItems.getRatings());
        phone_numbers.setText("Contacts : "+restaurantJSONItems.getPhone_numbers());
        reviews=restaurantJSONItems.getReviews();
       Log.d(TAG, "Restaurant Fragment loadRestaurantIntoFragment: "+restaurantJSONItems.getCuisines());
        cuisine.setText(restaurantJSONItems.getCuisines());
        Log.d(TAG, "loadRestaurantIntoFragment: Data loaded into restaurant fragment");
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        restaurantFragmentMethods=(RestaurantFragmentMethods)context;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(((TextView) view).getText().toString().equals("Order Now"))
        {
            Log.d(TAG, "onItemClick: Display Menu");
            restaurantFragmentMethods.loadMenuList(restaurantJSONItems.getCuisines());
        }
        else if(((TextView) view).getText().toString().equals("Reviews"))
        {
            Log.d(TAG, "onItemClick: Display Reviews");
            //Log.d(TAG, "onItemClick: "+reviews);
            restaurantFragmentMethods.startReviewFragment(reviews);

        }
    }


    interface RestaurantFragmentMethods{
        void loadMenuList(String cuisines);
        void startReviewFragment(String reviews);
    }
}
