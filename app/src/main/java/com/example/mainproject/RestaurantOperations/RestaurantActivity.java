package com.example.mainproject.RestaurantOperations;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mainproject.R;
import com.example.mainproject.RestaurantOperations.RestaurantMenu.MenuFragment;
import com.example.mainproject.RestaurantOperations.RestaurantMenu.MenuItem;
import com.example.mainproject.RestaurantOperations.RestaurantValuesClasses.RestaurantJSONItems;

import java.util.List;

public class RestaurantActivity extends AppCompatActivity implements RestaurantFragment.RestaurantFragmentMethods , MenuFragment.MenuFragmentMethods {

    private RestaurantJSONItems restaurantJSONItems;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private static final String TAG="kun";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        initialize();


        if(savedInstanceState!=null)
            return;
        RestaurantFragment restaurantFragment=new RestaurantFragment();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.restaurantLAyout,restaurantFragment);
        fragmentTransaction.commit();
        restaurantFragment.setRestaurantObject(restaurantJSONItems);


    }
    private void initialize() {
        restaurantJSONItems= (RestaurantJSONItems) getIntent().getSerializableExtra("RestaurantData");
        fragmentManager=getSupportFragmentManager();
    }




    @Override
    public void loadMenuList() {
        MenuFragment menuFragment=new MenuFragment();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.restaurantLAyout,menuFragment);
        fragmentTransaction.addToBackStack("MenuFragment").commit();
    }

    @Override
    public void sendMenuItems(List<MenuItem> cart) {
        fragmentManager=getSupportFragmentManager();
        ConfirmFragment confirmFragment = new ConfirmFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.restaurantLAyout, confirmFragment);
        fragmentTransaction.addToBackStack("BillFragment").commit();
        confirmFragment.getMenuItems(cart);


    }
}
