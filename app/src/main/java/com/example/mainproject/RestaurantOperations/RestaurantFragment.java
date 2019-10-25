package com.example.mainproject.RestaurantOperations;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.mainproject.R;
import com.example.mainproject.RestaurantOperations.RestaurantValuesClasses.RestaurantJSONItems;


public class RestaurantFragment extends Fragment implements View.OnClickListener {

    public RestaurantFragment() {
        // Required empty public constructor
    }


    private  ImageView imageView;
    private TextView name,cuisine,
            address, phone_numbers, ratings, orderNow, reviewTextView;
    private  final String TAG="Main";
    private  Context context;
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
        orderNow.setOnClickListener(this);
        reviewTextView.setOnClickListener(this);
        return v;
    }

    private void initialize(View v) {
        imageView=v.findViewById(R.id.fragmentRestaurantPhoto);
        name=v.findViewById(R.id.fragmentRestaurantName);
        cuisine=v.findViewById(R.id.fragmentCuisine);
        address = v.findViewById(R.id.fragmentRestaurantAddress);
        ratings=v.findViewById(R.id.ratings);
        phone_numbers=v.findViewById(R.id.phoneNumbers);
        orderNow = v.findViewById(R.id.orderNow);
        reviewTextView = v.findViewById(R.id.reviewsLoad);
        context=getActivity();

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
    public void onClick(View v) {
        switch (v.getId())
        {
            /*START MENUFRAGMENT*/
            case R.id.orderNow:
                Log.d(TAG, "onItemClick: Display Menu");
                restaurantFragmentMethods.loadMenuList(restaurantJSONItems.getCuisines());
                break;
            /*START REVIEW FRAGMENT*/
            case R.id.reviewsLoad:
                Log.d(TAG, "onItemClick: Display Reviews");
                //Log.d(TAG, "onItemClick: "+reviews);
                restaurantFragmentMethods.startReviewFragment(reviews);
                break;

        }
    }


    interface RestaurantFragmentMethods{
        void loadMenuList(String cuisines);
        void startReviewFragment(String reviews);
    }
}
