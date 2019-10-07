package com.example.mainproject.RestaurantOperations;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mainproject.R;
import com.example.mainproject.RestaurantOperations.RestaurantMenu.MenuItem;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmFragment extends Fragment {

    private List<MenuItem> cart;
    private TextView confirm,total,totalPriceValue;
    private ListView cartItemsListView;
    public ConfirmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_confirm, container, false);
        initialize(v);
        cartItemsListView.setAdapter(new ConfirmFragmentListAdapter(getActivity(),cart));
        return v;
    }

    private void initialize(View v) {
        confirm=v.findViewById(R.id.confirmYourOrder);
        total=v.findViewById(R.id.total);
        totalPriceValue=v.findViewById(R.id.totalValue);
        cartItemsListView=v.findViewById(R.id.carItemsList);
    }


    public void getMenuItems(List<com.example.mainproject.RestaurantOperations.RestaurantMenu.MenuItem> cart) {
        this.cart=cart;

    }
}
