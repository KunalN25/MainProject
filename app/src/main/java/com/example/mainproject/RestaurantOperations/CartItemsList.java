package com.example.mainproject.RestaurantOperations;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.mainproject.R;
import com.example.mainproject.RestaurantOperations.RestaurantMenu.MenuItem;
import com.example.mainproject.RestaurantOperations.RestaurantMenu.MenuListAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartItemsList extends Fragment {

    private ListView listView;
    private List<MenuItem> cart;
    private MenuListAdapter menuListAdapter;
    public CartItemsList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_cart_items_list, container, false);
        initialize(v);
        listView.setAdapter(menuListAdapter);
        // Inflate the layout for this fragment
        return v;
    }

    private void initialize(View v) {
        listView=v.findViewById(R.id.carItemsList);
       // menuListAdapter=new MenuListAdapter(getActivity(),cart);

    }

    void getMenuItems(List<MenuItem> cart) {
        this.cart=cart;
    }
}
